package com.example.filmcameraphotobook.ui.main;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.filmcameraphotobook.camera.Camera;
import com.example.filmcameraphotobook.film.Film;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.util.Listener;

import java.util.ArrayList;

public class UserPreferencesViewModel extends ViewModel {
    private FirebaseFirestore databaseRef = FirebaseFirestore.getInstance();
    private CollectionReference filmsReference = databaseRef.collection("films");
    private CollectionReference camerasReference = databaseRef.collection("cameras");
    private Listener<ArrayList<Camera>> camerasFetchedListener;
    private Listener<ArrayList<Film>> filmsFetchedListener;

    public UserPreferencesViewModel() {
        filmsReference.orderBy("name")
                .get().addOnCompleteListener(filmsFetched);
        camerasReference.orderBy("name")
                .get().addOnCompleteListener(camerasFetched);
    }


    public void setCamerasFetchedObserver(Listener<ArrayList<Camera>> listener) {
        this.camerasFetchedListener = listener;
    }


    public void setFilmsFetchedObserver(Listener<ArrayList<Film>> listener) {
        this.filmsFetchedListener = listener;
    }

    private final OnCompleteListener<QuerySnapshot> filmsFetched = new OnCompleteListener<QuerySnapshot>() {
        @SuppressLint("RestrictedApi")
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> fetch) {
            if(fetch.isSuccessful()) {
                ArrayList<Film> films = new ArrayList<>();
                for (QueryDocumentSnapshot document: fetch.getResult())
                    films.add(document.toObject(Film.class));
                if(filmsFetchedListener != null) filmsFetchedListener.onValue(films);
            }
        }
    };

    private final OnCompleteListener<QuerySnapshot> camerasFetched = new OnCompleteListener<QuerySnapshot>() {
        @SuppressLint("RestrictedApi")
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> fetch) {
            if(fetch.isSuccessful()) {
                ArrayList<Camera> cameras = new ArrayList<>();
                for (QueryDocumentSnapshot document: fetch.getResult())
                    cameras.add(document.toObject(Camera.class));
                if(camerasFetchedListener != null) camerasFetchedListener.onValue(cameras);
            }
        }
    };
}
