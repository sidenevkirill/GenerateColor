package ru.lisdevs.generaterandom;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FullColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Устанавливаем разметку для этого активити (например, full_color_activity.xml)
        setContentView(R.layout.full_color_activity);

        // Получаем переданный цвет из интента
        int color = getIntent().getIntExtra("color", Color.WHITE);

        // Устанавливаем цвет фона для корневого элемента или другого представления
        findViewById(R.id.full_color_view).setBackgroundColor(color); // Предполагается наличие элемента с id full_color_view в разметке

        // Вы можете добавить дополнительные элементы UI для отображения информации о цвете.
    }
}
