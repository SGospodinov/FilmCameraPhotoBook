package com.example.filmcameraphotobook.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filmcameraphotobook.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

import static android.app.Activity.RESULT_OK;

public class LandingFragment extends Fragment {
    private static final int SIGN_IN_REQUEST_CODE = 124;
    private LandingViewModel viewModel;

    private NavController navigationController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.landing_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(LandingViewModel.class);
        navigationController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        if(viewModel.getCurrentUser() == null) startSignInActivity();
        else navigateToGalleryFragment();
    }

    private void startSignInActivity() {
        Intent signInActivitIntenet = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()
                )).build();

        startActivityForResult(signInActivitIntenet, SIGN_IN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE && resultCode == RESULT_OK) {
            navigateToGalleryFragment();
        }
    }

    private void navigateToGalleryFragment() {
        navigationController.navigate(LandingFragmentDirections.loginSuccess());
    }
}
