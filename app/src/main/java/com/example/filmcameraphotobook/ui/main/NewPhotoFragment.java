package com.example.filmcameraphotobook.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.filmcameraphotobook.R;
import com.example.filmcameraphotobook.camera.Camera;
import com.example.filmcameraphotobook.film.Film;
import com.example.filmcameraphotobook.photo.Photo;

import java.io.File;
import java.util.List;

public class NewPhotoFragment extends Fragment {
    private NewPhotoViewModel viewModel;

    private ImageView imagePreview;
    private Spinner cameraSpinner;
    private Spinner filmSpinner;
    private Spinner apertureSpinner;
    private Spinner shutterSpeedSpinner;
    private EditText focusDistanceEditText;
    private EditText notesEditText;
    private Button saveButton;
    private Button cancelButton;

    private NavController navigationController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        navigationController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        View view = inflater.inflate(R.layout.new_photo_fragment, container, false);

        imagePreview = view.findViewById(R.id.new_photo_image_view);
        cameraSpinner = view.findViewById(R.id.new_photo_camera_spinner);
        filmSpinner = view.findViewById(R.id.new_photo_film_spinner);
        apertureSpinner = view.findViewById(R.id.new_photo_aperture_spinner);
        shutterSpeedSpinner = view.findViewById(R.id.new_photo_shutter_speed_spinner);
        focusDistanceEditText = view.findViewById(R.id.new_photo_focus_distance_edit_text);
        notesEditText = view.findViewById(R.id.new_photo_notes_edit_text);
        saveButton = view.findViewById(R.id.save_photo_button);
        cancelButton = view.findViewById(R.id.cancel_new_photo_button);

        cameraSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Camera selectedCamera = (Camera) parent.getItemAtPosition(position);

                populateSpinner(apertureSpinner, selectedCamera.getAvailableApertures());
                populateSpinner(shutterSpeedSpinner, selectedCamera.getAvailableShutterSpeeds());

                viewModel.setSelectedCamera(selectedCamera);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        filmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Film selectedFilm = (Film) parent.getItemAtPosition(position);
                viewModel.setSelectedFilm(selectedFilm);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        apertureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Float selectedAperture = (Float) parent.getItemAtPosition(position);
                viewModel.setSelectedAperture(selectedAperture);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        shutterSpeedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedShutterSpeed = (String) parent.getItemAtPosition(position);
                viewModel.setSelectedShutterSpeed(selectedShutterSpeed);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        focusDistanceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()) {
                    viewModel.setSelectedFocusDistance(Float.parseFloat(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        notesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSelectedNote(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveButton.setOnClickListener(button -> {
            cameraSpinner.setEnabled(false);
            filmSpinner.setEnabled(false);
            apertureSpinner.setEnabled(false);
            shutterSpeedSpinner.setEnabled(false);
            focusDistanceEditText.setEnabled(false);
            notesEditText.setEnabled(false);
            saveButton.setEnabled(false);
            cancelButton.setEnabled(false);

            viewModel.savePhoto();
        });
        cancelButton.setOnClickListener(button -> navigationController.popBackStack());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(NewPhotoViewModel.class);
        viewModel.setPreferences(PreferenceManager.getDefaultSharedPreferences(getActivity()));
        viewModel.setImageFile(NewPhotoFragmentArgs.fromBundle(getArguments()).getImageAbsolutePath());
        viewModel.setOnPhotoDeletedListener(photo -> navigationController.popBackStack());

        viewModel.getCameras().addOnCompleteListener(getActivity(), task -> {
            if(task.isSuccessful()) {
                List<Camera> cameras = task.getResult().toObjects(Camera.class);
                populateSpinner(cameraSpinner, cameras);
                selectPreferredCamera(cameras, viewModel.getCameraPreference());
            }
        });

        viewModel.getFilms().addOnCompleteListener(getActivity(), task -> {
            if(task.isSuccessful()){
                List<Film> films = task.getResult().toObjects(Film.class);
                populateSpinner(filmSpinner, films);
                selectPreferredFilm(films, viewModel.getFilmPreference());
            }
        });

        Glide.with(getContext())
            .load(viewModel.getImageFile())
            .placeholder(new CircularProgressDrawable(getContext()))
            .into(imagePreview);
    }

    private void populateSpinner(Spinner spinner, List collection) {
        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(getContext(),
                android.R.layout.simple_spinner_item, collection);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void selectPreferredCamera(List<Camera> cameras, String preferredCameraId) {
        int index = -1;
        if (preferredCameraId == null) return;
        for (int i = 0; i < cameras.size(); i++)
            if (cameras.get(i).getId().equals(preferredCameraId)) index = i;
        if (index >= 0) cameraSpinner.setSelection(index);
    }

    private void selectPreferredFilm(List<Film> films, String preferredFilmId) {
        int index = -1;
        if (preferredFilmId == null) return;
        for (int i = 0; i < films.size(); i++)
            if (films.get(i).getId().equals(preferredFilmId)) index = i;
        if (index >= 0) filmSpinner.setSelection(index);
    }
}
