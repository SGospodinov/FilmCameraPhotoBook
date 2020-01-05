package com.example.filmcameraphotobook.ui.main;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.DropDownPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.filmcameraphotobook.R;
import com.example.filmcameraphotobook.camera.Camera;
import com.example.filmcameraphotobook.film.Film;
import com.google.firebase.firestore.util.Listener;

import java.util.ArrayList;

public class UserPreferencesFragment extends PreferenceFragmentCompat {
    private static final String FAVORITE_CAMERA_PREFERENCE_KEY = "favorite_camera_preference";
    private static final String FILM_PREFERENCE_KEY = "film_preference";

    private UserPreferencesViewModel viewModel;

    private DropDownPreference favoriteCameraPreference;
    private DropDownPreference filmPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.user_preferences, rootKey);
        viewModel = ViewModelProviders.of(this).get(UserPreferencesViewModel.class);

        favoriteCameraPreference = findPreference(FAVORITE_CAMERA_PREFERENCE_KEY);
        filmPreference = findPreference(FILM_PREFERENCE_KEY);

        viewModel.setCamerasFetchedObserver(camerasFetchedListener);
        viewModel.setFilmsFetchedObserver(filmsFetchedListener);
    }

    private final Listener<ArrayList<Film>> filmsFetchedListener = new Listener<ArrayList<Film>>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onValue(ArrayList<Film> films) {
            CharSequence[] filmDisplayValues = films.stream().map(Film::toString)
                    .toArray(CharSequence[]::new);

            CharSequence[] filmIDs = films.stream().map(Film::getId)
                    .toArray(CharSequence[]::new);

            filmPreference.setEntryValues(filmIDs);
            filmPreference.setEntries(filmDisplayValues);
        }
    };

    private final Listener<ArrayList<Camera>> camerasFetchedListener = new Listener<ArrayList<Camera>>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onValue(ArrayList<Camera> cameras) {
            CharSequence[] cameraNames = cameras.stream().map(Camera::getName)
                    .toArray(CharSequence[]::new);

            CharSequence[] cameraIDs = cameras.stream().map(Camera::getId)
                    .toArray(CharSequence[]::new);

            favoriteCameraPreference.setEntryValues(cameraIDs);
            favoriteCameraPreference.setEntries(cameraNames);
        }
    };
}
