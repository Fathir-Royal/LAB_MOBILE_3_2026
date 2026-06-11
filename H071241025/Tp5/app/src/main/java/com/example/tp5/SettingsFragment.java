package com.example.tp5;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private PrefManager prefManager;
    private TextView tvUsername;
    private RadioGroup rgTheme;
    private Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefManager = new PrefManager(requireContext());

        tvUsername = view.findViewById(R.id.tv_username);
        rgTheme = view.findViewById(R.id.rg_theme);
        btnLogout = view.findViewById(R.id.btn_logout);

        tvUsername.setText("Hello, " + prefManager.getUsername() + "!");

        int currentTheme = prefManager.getThemeMode();
        if (currentTheme == 1) {
            rgTheme.check(R.id.rb_dark);
        } else {
            rgTheme.check(R.id.rb_light);
        }

        rgTheme.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_dark) {
                prefManager.setThemeMode(1);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(getContext(), "Tema gelap aktif, restart aplikasi", Toast.LENGTH_SHORT).show();
            } else {
                prefManager.setThemeMode(0);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(getContext(), "Tema terang aktif, restart aplikasi", Toast.LENGTH_SHORT).show();
            }

            requireActivity().recreate();
        });

        btnLogout.setOnClickListener(v -> {
            prefManager.logout();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}