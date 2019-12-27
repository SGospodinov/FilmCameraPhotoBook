package com.example.filmcameraphotobook.ui.main;

import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class NewPhotoViewModel extends ViewModel {
    private FirebaseFirestore databaseRef = FirebaseFirestore.getInstance();

    private String cameraPreference = null;
    private String filmPreference = null;

    public Task<QuerySnapshot> getCameras() {
        return databaseRef.collection("cameras").orderBy("name").get();
    }

    public Task<QuerySnapshot> getFilms() {
        return databaseRef.collection("films").orderBy("name").get();
    }

    public void setPreferences(SharedPreferences preferences) {
        cameraPreference = preferences.getString("favorite_camera_preference", null);
        filmPreference = preferences.getString("film_preference", null);
    }

    public String getCameraPreference() {
        return cameraPreference;
    }

    public String getFilmPreference() {
        return filmPreference;
    }
}
