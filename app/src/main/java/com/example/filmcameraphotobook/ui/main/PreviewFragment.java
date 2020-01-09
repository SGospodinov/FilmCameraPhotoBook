package com.example.filmcameraphotobook.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.filmcameraphotobook.R;
import com.example.filmcameraphotobook.photo.PhotoDecorator;

public class PreviewFragment extends Fragment {
    private PreviewViewModel viewModel;

    private ImageView photoView;
    private TextView cameraNameTextView;
    private TextView filmNameTextView;
    private TextView shutterSpeedTextView;
    private TextView apertureTextView;
    private TextView focusDistanceTextView;
    private TextView dateTextView;
    private TextView noteTextView;
    private Button deleteButton;

    private AlertDialog deletePhotoDialog;

    public static PreviewFragment newInstance() {
        return new PreviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preview_fragment, container, false);

        photoView = view.findViewById(R.id.preview_photo_image_view);
        cameraNameTextView = view.findViewById(R.id.preview_camera_name_text_view);
        filmNameTextView = view.findViewById(R.id.preview_film_name_text_view);
        shutterSpeedTextView = view.findViewById(R.id.preview_shutter_speed_text_view);
        apertureTextView = view.findViewById(R.id.preview_aperture_text_view);
        focusDistanceTextView = view.findViewById(R.id.preview_focus_distance_text_view);
        dateTextView = view.findViewById(R.id.preview_date_text_view);
        noteTextView = view.findViewById(R.id.preview_notes_text_view);
        deleteButton = view.findViewById(R.id.delete_button);

        deletePhotoDialog = createDeleteDialog();
        deleteButton.setOnClickListener(v -> deletePhotoDialog.show());

        return view;
    }

    private AlertDialog createDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true)
                .setTitle(R.string.delete_photo)
                .setPositiveButton(R.string.delete, (dialog, which) -> viewModel.deletePhoto())
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
        return builder.create();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(PreviewViewModel.class);
        viewModel.setPhoto(PreviewFragmentArgs.fromBundle(getArguments()).getPhoto());

        viewModel.setPhotoDeletedListener(photoDeletedListener);

        PhotoDecorator photoDecorator = new PhotoDecorator(viewModel.getPhoto(), getResources());

        cameraNameTextView.setText(viewModel.getPhoto().getCameraName());
        filmNameTextView.setText(viewModel.getPhoto().getFilmName());

        apertureTextView.setText(photoDecorator.getFormattedAperture());
        shutterSpeedTextView.setText(photoDecorator.getFormattedShutterSpeed());
        focusDistanceTextView.setText(photoDecorator.getFormattedFocusDistance());
        noteTextView.setText(photoDecorator.getFormattedNote());
        dateTextView.setText(photoDecorator.getFormattedDate(DateFormat.getLongDateFormat(getContext())));

        CircularProgressDrawable loadingSpinner = new CircularProgressDrawable(getContext());
        loadingSpinner.setStyle(CircularProgressDrawable.LARGE);
        loadingSpinner.start();

        Glide.with(getContext())
                .load(viewModel.getPhotoImageRef())
                .placeholder(loadingSpinner)
                .into(photoView);
    }

    private final PreviewViewModel.OnPhotoDeletedListener photoDeletedListener =
            photo -> {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.popBackStack();
            };
}
