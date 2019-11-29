package com.example.filmcameraphotobook.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.DropDownPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.filmcameraphotobook.R;
import com.example.filmcameraphotobook.camera.Camera;
import com.example.filmcameraphotobook.film.Film;

import java.util.HashSet;
import java.util.stream.Stream;

public class UserPreferencesFragment extends PreferenceFragmentCompat {
    private static final String FAVORITE_CAMERA_PREFERENCE_KEY = "favorite_camera_preference";
    private static final String FAVORITE_LENSES_PREFERENCE_KEY = "favorite_lenses_preference";
    private static final String FILM_PREFERENCE_KEY = "film_preference";

    private UserPreferencesViewModel viewModel;

    private DropDownPreference favoriteCameraPreference;
    private DropDownPreference filmPreference;
    private DropDownPreference favoriteLensesPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.user_preferences, rootKey);
        viewModel = ViewModelProviders.of(this).get(UserPreferencesViewModel.class);

        favoriteCameraPreference = findPreference(FAVORITE_CAMERA_PREFERENCE_KEY);
        favoriteLensesPreference = findPreference(FAVORITE_LENSES_PREFERENCE_KEY);
        filmPreference = findPreference(FILM_PREFERENCE_KEY);

        viewModel.getAllCameraNames().observe(this, camerasObserver);
        viewModel.getAllFilmNames().observe(this, filmsObserver);
    }

    private final Observer<HashSet<Film>> filmsObserver = new Observer<HashSet<Film>>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onChanged(HashSet<Film> films) {
            CharSequence[] filmDisplayValues = films.stream().map(Film::toString)
                    .toArray(CharSequence[]::new);

            CharSequence[] filmIDs = films.stream().map(Film::getID)
                    .toArray(CharSequence[]::new);

            filmPreference.setEntryValues(filmIDs);
            filmPreference.setEntries(filmDisplayValues);
        }
    };

    private final Observer<HashSet<Camera>> camerasObserver = new Observer<HashSet<Camera>>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onChanged(HashSet<Camera> cameras) {
            CharSequence[] cameraNames = cameras.stream().map(Camera::getName)
                    .toArray(CharSequence[]::new);

            CharSequence[] cameraIDs = cameras.stream().map(Camera::getID)
                    .toArray(CharSequence[]::new);

            favoriteCameraPreference.setEntryValues(cameraIDs);
            favoriteCameraPreference.setEntries(cameraNames);
        }
    };
}
