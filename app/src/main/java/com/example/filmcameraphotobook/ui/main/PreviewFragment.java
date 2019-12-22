package com.example.filmcameraphotobook.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(PreviewViewModel.class);
        viewModel.setPhoto(PreviewFragmentArgs.fromBundle(getArguments()).getPhoto());

        PhotoDecorator photoDecorator = new PhotoDecorator(viewModel.getPhoto(), getResources());

        cameraNameTextView.setText(viewModel.getPhoto().getCameraId());
        filmNameTextView.setText(viewModel.getPhoto().getFilmId());

        apertureTextView.setText(photoDecorator.getFormattedAperture());
        shutterSpeedTextView.setText(photoDecorator.getFormattedShutterSpeed());
        focusDistanceTextView.setText(photoDecorator.getFormattedFocusDistance());
        noteTextView.setText(photoDecorator.getFormattedNote());
        dateTextView.setText(photoDecorator.getFormattedDate(DateFormat.getLongDateFormat(getContext())));

        CircularProgressDrawable loadingSpinner = new CircularProgressDrawable(getContext());
        loadingSpinner.setStyle(CircularProgressDrawable.LARGE);
        loadingSpinner.start();

        Glide.with(getContext())
                .load(viewModel.getPhotoRef())
                .placeholder(loadingSpinner)
                .into(photoView);
    }
}
