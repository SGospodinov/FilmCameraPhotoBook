package com.example.filmcameraphotobook.ui.main;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filmcameraphotobook.R;
import com.example.filmcameraphotobook.photo.Photo;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotMetadata;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment {
    private static final int COLUMNS_COUNT = 2;
    private static final int PAGE_SIZE = 8;
    private static final int PREFETCH_DISTANCE = 10;
    private static final int TAKE_PHOTO_REQUEST_CODE = 123;

    private GalleryViewModel viewModel;
    private FirestorePagingAdapter<Photo, PhotoViewHolder> galleryAdapter;

    private RecyclerView galleryRecyclerView;
    private TextView errorTextView;
    private ProgressBar loadingSpinner;
    private CoordinatorLayout layoutParent;
    private FloatingActionButton newPhotoButton;

    private NavController navigationController;

    private  GalleryFragmentDirections.CreateNewPhoto newPhotoAction = null;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_fragment, container, false);
        navigationController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        galleryRecyclerView = view.findViewById(R.id.gallery_recycler_view);
        errorTextView = view.findViewById(R.id.errors_text_view);
        loadingSpinner = view.findViewById(R.id.loading_photos_spinner);
        layoutParent = view.findViewById(R.id.gallery_coordinator);
        newPhotoButton = view.findViewById(R.id.new_photo_button);

        newPhotoButton.setOnClickListener(button -> dispatchTakePhotoIntent());
        galleryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), COLUMNS_COUNT));

        return view;
    }

    private void dispatchTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File imageFile = createTempImageFile();
            Uri photoURI = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".fileprovider", imageFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            newPhotoAction = GalleryFragmentDirections.createNewPhoto(imageFile.getAbsolutePath());
            startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST_CODE);
        } catch (IOException e) {
            Snackbar.make(layoutParent, "Can't create tmp file", Snackbar.LENGTH_SHORT).show();
        }
    }

    private File createTempImageFile () throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File picturesDirectory =  getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(timestamp, ".jpg", picturesDirectory);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;
        if (requestCode == TAKE_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) navigationController.navigate(newPhotoAction);
        else Snackbar.make(layoutParent, "Something went wrong", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);

        PagedList.Config pagingConfig = createPagingConfig();
        FirestorePagingOptions<Photo> pagingOptions = createPagingOptions(viewModel, pagingConfig);
        galleryAdapter = createGalleryAdapter(pagingOptions);
        galleryRecyclerView.setAdapter(galleryAdapter);

        Photo photo = GalleryFragmentArgs.fromBundle(getArguments()).getDeletedPhoto();
        if(photo != null) displayPhotoDeletedMessage(photo);
    }

    private void displayPhotoDeletedMessage(Photo photo) {
        Snackbar.make(layoutParent, R.string.photo_deleted, Snackbar.LENGTH_LONG).show();
    }

    private FirestorePagingAdapter<Photo, PhotoViewHolder> createGalleryAdapter(FirestorePagingOptions<Photo> pagingOptions) {
        return new FirestorePagingAdapter<Photo, PhotoViewHolder>(pagingOptions) {
            @Override
            protected void onBindViewHolder(@NonNull PhotoViewHolder viewHolder, int position, @NonNull Photo photo) {
                viewHolder.bindPhoto(photo, viewModel.getUserId());
            }

            @NonNull
            @Override
            public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.photo_tile, parent, false);

                return new PhotoViewHolder(view, getContext(), navigationController);
            }

            @Override
            protected void onLoadingStateChanged(@NonNull LoadingState state) {
                super.onLoadingStateChanged(state);

                switch (state) {
                    case LOADING_INITIAL:
                        showLoadingSpinner();
                        break;
                    case LOADING_MORE:
                        break;
                    case LOADED:
                    case FINISHED:
                        showResults(getItemCount());
                        break;
                    case ERROR:
                        errorTextView.setText(R.string.cant_load_photos);
                        showErrorMessage();
                }
            }
        };
    }

    private FirestorePagingOptions<Photo> createPagingOptions(GalleryViewModel viewModel, PagedList.Config pagingConfig) {
        return new FirestorePagingOptions.Builder<Photo>()
                .setLifecycleOwner(this)
                .setQuery(viewModel.getPhotosDesc(), pagingConfig, Photo.class)
                .build();
    }

    private PagedList.Config createPagingConfig () {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(PREFETCH_DISTANCE)
                .setPageSize(PAGE_SIZE)
                .build();
    }

    private void showLoadingSpinner() {
        galleryRecyclerView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        loadingSpinner.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        galleryRecyclerView.setVisibility(View.GONE);
        loadingSpinner.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
    }

    private void showResults(int count) {
        if (count > 0) {
            errorTextView.setVisibility(View.GONE);
            loadingSpinner.setVisibility(View.GONE);
            galleryRecyclerView.setVisibility(View.VISIBLE);
        } else {
            errorTextView.setText(R.string.no_photos_yet);
            showErrorMessage();
        }
    }
}
