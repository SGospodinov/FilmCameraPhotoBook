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
        navigationController.addOnDestinationChangedListener(destinationChangeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return  NavigationUI.onNavDestinationSelected(item, navigationController)
            || super.onOptionsItemSelected(item);
    }

    private final NavController.OnDestinationChangedListener destinationChangeListener =
        new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.userPreferencesFragment:
                        toolbar.setVisibility(View.GONE);
                        break;
                    default:
                        toolbar.setVisibility(View.VISIBLE);
                }
            }
        };
}
