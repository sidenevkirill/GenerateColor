package ru.lisdevs.generaterandom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ColorGeneratorNewFragment extends Fragment {

    // UI Views
    private ImageView generatedColorView;
    private LinearLayout generateColorBtn;
    private RandomColors randomColors; // Экземпляр RandomColors

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_generator, container, false);

        // Initialize UI Views
        generatedColorView = view.findViewById(R.id.generatedColorView);
        generateColorBtn = view.findViewById(R.id.generateColorBtn);

        // Инициализация RandomColors
        randomColors = new RandomColors();

        // Handle generateColorBtn click, generate random color
        generateColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Function call to generate random color using RandomColors
                generateRandomColor();
            }
        });

        return view;
    }

    private void generateRandomColor() {
        // Получение случайного цвета из RandomColors
        int color = randomColors.getColor();

        // Установка сгенерированного цвета как фона ImageView
        generatedColorView.setBackgroundColor(color);

        // Пример уведомления о сгенерированном цвете (можно убрать)
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        Toast.makeText(getContext(), "Сгенерированный цвет: " + hexColor, Toast.LENGTH_SHORT).show();
    }
}
