package ru.lisdevs.generaterandom.activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.lisdevs.generaterandom.R;

public class FullColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_color_activity);

        int color = getIntent().getIntExtra("color", Color.WHITE);

        findViewById(R.id.full_color_view).setBackgroundColor(color);

    }
}
