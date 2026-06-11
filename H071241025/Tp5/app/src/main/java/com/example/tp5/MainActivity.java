package com.example.tp5;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefManager prefManager = new PrefManager(this);
        if (prefManager.getThemeMode() == 1) {
            setTheme(R.style.Theme_Tp5_Dark);
        } else {
            setTheme(R.style.Theme_Tp5);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequestPermission();

        DataDummy.initDummyData();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        PrefManager pref = new PrefManager(this);
        if (pref.getThemeMode() == 1) {
            // mode dark
            bottomNav.setBackgroundColor(ContextCompat.getColor(this, R.color.bottom_nav_bg_dark));
            bottomNav.setItemIconTintList(ContextCompat.getColorStateList(this, R.color.bottom_nav_icon_dark));
            bottomNav.setItemTextColor(ContextCompat.getColorStateList(this, R.color.bottom_nav_icon_dark));
        } else {
            // mode light
            bottomNav.setBackgroundColor(ContextCompat.getColor(this, R.color.bottom_nav_bg_light));
            bottomNav.setItemIconTintList(ContextCompat.getColorStateList(this, R.color.bottom_nav_icon_light));
            bottomNav.setItemTextColor(ContextCompat.getColorStateList(this, R.color.bottom_nav_icon_light));
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new FragmentHome())
                    .commit();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new FragmentHome();
            } else if (itemId == R.id.nav_favorites) {
                selectedFragment = new FavoritesFragment();
            } else if (itemId == R.id.nav_add) {
                selectedFragment = new AddBookFragment();
            } else if (itemId == R.id.nav_settings) {
                selectedFragment = new SettingsFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

    private void checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        PERMISSION_REQUEST_CODE
                );
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE
                );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}