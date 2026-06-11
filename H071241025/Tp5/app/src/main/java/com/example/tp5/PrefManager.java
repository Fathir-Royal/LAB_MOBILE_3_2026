package com.example.tp5;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;

public class PrefManager {
    private static final String PREF_NAME = "library_pref";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_THEME_MODE = "theme_mode";

    private static final String KEY_REGISTERED_USERS = "registered_users";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public boolean registerUser(String username, String password) {
        String existingPassword = getUserPassword(username);
        if (existingPassword != null) {
            return false;
        }

        editor.putString("user_" + username, password);
        editor.apply();
        return true;
    }

    public boolean checkLogin(String username, String password) {
        String savedPassword = getUserPassword(username);
        if (savedPassword != null && savedPassword.equals(password)) {
            setLogin(true, username);
            return true;
        }
        return false;
    }

    private String getUserPassword(String username) {
        return pref.getString("user_" + username, null);
    }

    public void setLogin(boolean isLoggedIn, String username) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "");
    }

    public void logout() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.remove(KEY_USERNAME);
        editor.apply();
    }

    public void setThemeMode(int mode) {
        editor.putInt(KEY_THEME_MODE, mode);
        editor.apply();
    }

    public int getThemeMode() {
        return pref.getInt(KEY_THEME_MODE, 0);
    }
}