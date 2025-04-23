package ru.lisdevs.generaterandom.activity.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.TaskStackBuilder;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import ru.lisdevs.generaterandom.MainActivity;
import ru.lisdevs.generaterandom.R;


public class SettingsFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String PREFS_NAME = "ThemePrefs";
    private static final String KEY_THEME = "theme";

    private static final int REQUEST_CODE_PICK_DIR = 9999;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.prefs);


    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()) {
            case KEY_DARK_MODE:
                if ((boolean) newValue) {
                    toggleTheme();
                }
                reloadTheme();
                break;
        }
        return true;
    }

    private void reloadTheme() {
        Activity activity = getActivity();

        TaskStackBuilder.create(activity)
                .addNextIntent(new Intent(activity, MainActivity.class))
                .addNextIntent(activity.getIntent())
                .startActivities();

    //    activity.overridePendingTransition(R.anim.alpha_out, R.anim.alpha_in);

    }

    private void toggleTheme() {
        // Получаем SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Получаем текущий режим темы
        int currentMode = AppCompatDelegate.getDefaultNightMode();

        if (currentMode == AppCompatDelegate.MODE_NIGHT_NO) {
            // Переключаем на темную тему
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            // Переключаем на светлую тему
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Применяем изменения
        editor.apply();
    }

}
