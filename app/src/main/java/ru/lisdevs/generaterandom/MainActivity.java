package ru.lisdevs.generaterandom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ColorGeneratorFragment colorGeneratorFragment;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colorGeneratorFragment = new ColorGeneratorFragment();
        homeFragment = new HomeFragment();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.item_generated_color:
                            changeFragment(new HomeFragment());
                            break;

                        case R.id.item_generated_color_image:
                            changeFragment(new ColorGeneratorFragment());
                            break;

                        case R.id.item_color_picker:
                            changeFragment(new ColorPickerFragment());
                            break;

                        case R.id.item_favorite:
                            changeFragment(new FavoritesFragment());
                            break;
                    }
                    return true;
                });

        changeFragment(new HomeFragment());
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

    }

    public void onSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onAbout(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}