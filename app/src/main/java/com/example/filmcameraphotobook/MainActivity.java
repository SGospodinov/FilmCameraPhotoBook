package com.example.filmcameraphotobook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private NavController navigationController;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(toolbar, navigationController);
        NavigationUI.setupActionBarWithNavController(this, navigationController);
        navigationController.addOnDestinationChangedListener(destinationChangeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu_item:
                signOut();
               break;
            case R.id.openPreferences:
                navigationController.navigate(R.id.userPreferencesFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        navigationController.popBackStack(R.id.navigation_graph, true);
        navigationController.navigate(R.id.landingFragment);
    }

    private final NavController.OnDestinationChangedListener destinationChangeListener =
        new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.userPreferencesFragment:
                    case R.id.landingFragment:
                        toolbar.setVisibility(View.GONE);
                        break;
                    case R.id.galleryFragment:
                        toolbar.setNavigationIcon(null);
                    default:
                        toolbar.setVisibility(View.VISIBLE);
                }
            }
        };
}
