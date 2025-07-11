package com.saveetha.studyplanner;

import android.annotation.SuppressLint;
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

        // Set default fragment on startup
        replaceFragment(new HomeFragment());

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

        // Set selected item explicitly to trigger listener if needed
        bottNav.setSelectedItemId(R.id.home);
    }
    public void navigateToIndex(int index) {
        if (bottNav == null) return;

        int menuSize = bottNav.getMenu().size();
        if (index < 0 || index >= menuSize) return;

        // Get the menu item by index and select it
        MenuItem item = bottNav.getMenu().getItem(index);
        bottNav.setSelectedItemId(item.getItemId());
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .commit();
    }
}
