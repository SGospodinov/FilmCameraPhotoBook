package com.example.filmcameraphotobook.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.filmcameraphotobook.R;

public class NewPhotoFragment extends Fragment {
    private NewPhotoViewModel viewModel;

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

        cameraSpinner = view.findViewById(R.id.new_photo_camera_spinner);
        filmSpinner = view.findViewById(R.id.new_photo_film_spinner);
        apertureSpinner = view.findViewById(R.id.new_photo_aperture_spinner);
        shutterSpeedSpinner = view.findViewById(R.id.new_photo_shutter_speed_spinner);
        focusDistanceEditText = view.findViewById(R.id.new_photo_focus_distance_edit_text);
        notesEditText = view.findViewById(R.id.new_photo_notes_edit_text);
        saveButton = view.findViewById(R.id.save_photo_button);
        cancelButton = view.findViewById(R.id.cancel_new_photo_button);

        saveButton.setOnClickListener(button -> savePhoto());
        cancelButton.setOnClickListener(button -> navigationController.popBackStack());

        return view;
    }

    private void savePhoto() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(NewPhotoViewModel.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String camera = sharedPreferences.getString("favorite_camera_preference", null);
        String film = sharedPreferences.getString("film_preference", null);
        Toast.makeText(getActivity(), String.format("Camera: %s\nFilm: %s", camera, film), Toast.LENGTH_LONG).show();
    }
}
