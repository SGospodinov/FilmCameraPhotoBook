package com.example.filmcameraphotobook.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.filmcameraphotobook.R;

public class GalleryFragment extends Fragment {

    private GalleryViewModel mViewModel;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gallery_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String camera = sharedPreferences.getString("favorite_camera_preference", null);
        String film = sharedPreferences.getString("film_preference", null);
        Toast.makeText(getActivity(), String.format("Camera: %s\nFilm: %s", camera, film), Toast.LENGTH_LONG).show();
    }
}
