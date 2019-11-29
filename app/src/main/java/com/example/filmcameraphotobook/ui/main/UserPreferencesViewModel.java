package com.example.filmcameraphotobook.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.filmcameraphotobook.camera.Camera;
import com.example.filmcameraphotobook.camera.CamerasRepository;
import com.example.filmcameraphotobook.film.Film;
import com.example.filmcameraphotobook.film.FilmsRepository;

import java.util.HashSet;

public class UserPreferencesViewModel extends ViewModel {
    private CamerasRepository camerasRepository = CamerasRepository.getInstance();
    private FilmsRepository filmsRepository = FilmsRepository.getInstance();
    private MutableLiveData<HashSet<Camera>> cameras;
    private MutableLiveData<HashSet<Film>> films;

    public MutableLiveData<HashSet<Film>> getAllFilmNames() {
        if (films == null)
            films = new MutableLiveData<HashSet<Film>>(filmsRepository.getAllFilms());
        return films;
    }

    public MutableLiveData<HashSet<Camera>> getAllCameraNames() {
        if (cameras == null)
            cameras = new MutableLiveData<HashSet<Camera>>(camerasRepository.getAllCameras());
        return cameras;
    }
}
