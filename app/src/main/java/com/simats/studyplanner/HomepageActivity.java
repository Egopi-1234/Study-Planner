package com.simats.studyplanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomepageActivity extends AppCompatActivity {

    FrameLayout frameContainer;
    BottomNavigationView bottNav;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);

        frameContainer = findViewById(R.id.frameContainer);
        bottNav = findViewById(R.id.bottNav);

        // Handle navigation item selection
        bottNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.home) {
                    replaceFragment(new HomeFragment());
                } else if (id == R.id.task) {
                    replaceFragment(new TasksFragment());
                } else if (id == R.id.calendar) {
                    replaceFragment(new CalendartasksFragment());
                } else if (id == R.id.material) {
                    replaceFragment(new ItemStudyMaterialFragment());
                } else if (id == R.id.profile) {
                    replaceFragment(new profilepageFragment());
                }

                return true;
            }
        });

        // If another activity told us to open a specific fragment
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("openFragment")) {
            String fragmentName = intent.getStringExtra("openFragment");
            openSpecificFragment(fragmentName);
        } else {
            // Default fragment on startup
            bottNav.setSelectedItemId(R.id.home);
            replaceFragment(new HomeFragment());
        }
    }

    // Navigate to a fragment by index (if needed elsewhere)
    public void navigateToIndex(int index) {
        if (bottNav == null) return;

        int menuSize = bottNav.getMenu().size();
        if (index < 0 || index >= menuSize) return;

        // Get the menu item by index and select it
        MenuItem item = bottNav.getMenu().getItem(index);
        bottNav.setSelectedItemId(item.getItemId());
    }

    // Replace fragment helper
    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .commit();
    }

    // Open specific fragment by name from an external intent
    private void openSpecificFragment(String fragmentName) {
        if (fragmentName == null) {
            bottNav.setSelectedItemId(R.id.home);
            replaceFragment(new HomeFragment());
            return;
        }

        switch (fragmentName) {
            case "material":
                bottNav.setSelectedItemId(R.id.material);
                replaceFragment(new ItemStudyMaterialFragment());
                break;
            case "task":
                bottNav.setSelectedItemId(R.id.task);
                replaceFragment(new TasksFragment());
                break;
            case "calendar":
                bottNav.setSelectedItemId(R.id.calendar);
                replaceFragment(new CalendartasksFragment());
                break;
            case "profile":
                bottNav.setSelectedItemId(R.id.profile);
                replaceFragment(new profilepageFragment());
                break;
            default:
                bottNav.setSelectedItemId(R.id.home);
                replaceFragment(new HomeFragment());
                break;
        }
    }
}
