package ru.lisdevs.generaterandom;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
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
import java.util.Random;

import com.google.android.material.button.MaterialButton;

public class ColorGeneratorFragment extends Fragment {

    // UI Views
    private ImageView generatedColorView;
    private LinearLayout generateColorBtn;
    private LinearLayout copyColorBtn; // Кнопка для копирования цвета
    private int currentColor; // Переменная для хранения текущего цвета

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color_generator, container, false);

        // Initialize UI Views
        generatedColorView = view.findViewById(R.id.generatedColorView);
        generateColorBtn = view.findViewById(R.id.generateColorBtn);
        copyColorBtn = view.findViewById(R.id.copyColorBtn); // Инициализация кнопки копирования

        // Handle generateColorBtn click, generate random color
        generateColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Function call to generate random color
                generateRandomColor();
            }
        });

        // Handle copyColorBtn click, copy the current color to clipboard
        copyColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyColorToClipboard(currentColor);
            }
        });

        return view;
    }

    private void generateRandomColor() {
        // Generate Random Color
        Random rnd = new Random();
        currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        // Set the randomly generated color as background color of a UI View.
        generatedColorView.setBackgroundColor(currentColor);
    }

    private void copyColorToClipboard(int color) {
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboard != null) {
            String hexColor = String.format("#%06X", (0xFFFFFF & color)); // Преобразование цвета в HEX-формат
            ClipData clip = ClipData.newPlainText("Copied Color", hexColor);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(getContext(), "Цвет скопирован: " + hexColor, Toast.LENGTH_SHORT).show(); // Уведомление о копировании
        }
    }
}