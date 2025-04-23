package ru.lisdevs.generaterandom.activity.generation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import ru.lisdevs.generaterandom.R;

public class GradientGenerationFragment extends Fragment {


    private ImageView generatedColorView;
    private LinearLayout generateColorBtn;
    private LinearLayout copyColorBtn;
    private LinearLayout saveColorBtn;
    private LinearLayout addGradientToFavoritesBtn; // Кнопка для добавления градиента в избранное
    private int startColor; // Переменная для хранения начального цвета
    private int endColor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gradiend_generator, container, false);

        // Initialize UI Views
        generatedColorView = view.findViewById(R.id.generatedColorView);
        generateColorBtn = view.findViewById(R.id.generateGradiendBtn);
        copyColorBtn = view.findViewById(R.id.copyColorBtn);

        // Handle generateColorBtn click, generate random gradient
        generateColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Function call to generate random gradient
                generateRandomGradient();
            }
        });

        // Handle copyColorBtn click, copy the current gradient colors to clipboard
        copyColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyGradientColorsToClipboard(startColor, endColor);
            }
        });

/*        saveColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGradientAsImage();
            }
        });

 */

        return view;
    }

    private void generateRandomGradient() {
        Random rnd = new Random();

        // Generate two random colors for the gradient
        startColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        endColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        // Create a gradient drawable and set it as background of the ImageView.
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{startColor, endColor});

        generatedColorView.setBackground(gradientDrawable);
    }

    private void copyGradientColorsToClipboard(int startColor, int endColor) {
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboard != null) {
            String hexStartColor = String.format("#%06X", (0xFFFFFF & startColor)); // Преобразование начального цвета в HEX-формат
            String hexEndColor = String.format("#%06X", (0xFFFFFF & endColor));     // Преобразование конечного цвета в HEX-формат

            String message = "Начальный цвет: " + hexStartColor + "\nКонечный цвет: " + hexEndColor;
            ClipData clip = ClipData.newPlainText("Copied Gradient Colors", message);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(getContext(), "Градиент скопирован:\n" + message, Toast.LENGTH_SHORT).show(); // Уведомление о копировании
        }
    }

    private void saveGradientAsImage() {
        // Получаем Bitmap из ImageView
        generatedColorView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(generatedColorView.getDrawingCache());
        generatedColorView.setDrawingCacheEnabled(false);

        // Сохраняем изображение
        String filename = "gradient_" + System.currentTimeMillis() + ".png";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDir, filename);

        try (FileOutputStream out = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // Сжимаем изображение и сохраняем
            Toast.makeText(getContext(), "Градиент сохранен: " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ошибка при сохранении изображения", Toast.LENGTH_SHORT).show();
        }
    }
}
