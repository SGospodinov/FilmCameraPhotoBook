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
import android.widget.TextView;

import com.example.filmcameraphotobook.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;

import static android.app.Activity.RESULT_OK;

public class LandingFragment extends Fragment {
    private static final int SIGN_IN_REQUEST_CODE = 124;
    private LandingViewModel viewModel;

    private NavController navigationController;
    private View loadingPanel;
    private View errorPanel;
    private TextView errorMessageTextView;
    private TextView retryTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.landing_fragment, container, false);

        loadingPanel = view.findViewById(R.id.loading_panel);
        errorPanel = view.findViewById(R.id.error_panel);

        errorMessageTextView = view.findViewById(R.id.landing_error_message);
        retryTextView = view.findViewById(R.id.retry_text_view);

        retryTextView.setOnClickListener(textView -> startSignInActivity());
        return view;
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
        Intent signInActivityIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.AppTheme_NoActionBar)
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()
                )).build();

        startActivityForResult(signInActivityIntent, SIGN_IN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE && resultCode == RESULT_OK) {
            displayLoadingPanel();
            navigateToGalleryFragment();
        } else {
            displayErrorMessage(IdpResponse.fromResultIntent(data));
        }
    }

    private void displayLoadingPanel() {
        loadingPanel.setVisibility(View.VISIBLE);
        errorPanel.setVisibility(View.GONE);
    }

    private void displayErrorMessage(IdpResponse response) {
        if(response == null || response.getError() == null) errorMessageTextView.setText(R.string.unknown_error);
        else if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) errorMessageTextView.setText(R.string.no_network);

        loadingPanel.setVisibility(View.GONE);
        errorPanel.setVisibility(View.VISIBLE);
    }

    private void navigateToGalleryFragment() {
        navigationController.navigate(LandingFragmentDirections.loginSuccess());
    }
}
