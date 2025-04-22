package ru.lisdevs.generaterandom;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.OutputStream;

public class ColorDisplayFragment extends Fragment {

    private static final String ARG_COLOR = "color";
    private TextView textView;
    private int color; // Объявляем переменную color как поле класса

    public static ColorDisplayFragment newInstance(int color) {
        ColorDisplayFragment fragment = new ColorDisplayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_display, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // Получаем цвет из аргументов
        color = getArguments() != null ? getArguments().getInt(ARG_COLOR) : Color.WHITE; // Инициализируем поле color

        // Добавляем кнопку для копирования цвета и сохранения изображения
        toolbar.inflateMenu(R.menu.main_menu); // Предполагается, что у вас есть меню

        // Устанавливаем слушатель для кнопки копирования и сохранения изображения
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.main_copy) {
                    copyColorToClipboard(color); // Копируем цвет в буфер обмена
                    return true;
                } else if (item.getItemId() == R.id.main_save) { // Предполагается, что у вас есть элемент меню для сохранения изображения
                    saveColorAsImage(color); // Сохраняем цвет как изображение
                    return true;
                }
                return false;
            }
        });

        // Устанавливаем цвет фона
        view.setBackgroundColor(color);

        // Если нужно, можно добавить текстовое поле для отображения цвета
        textView = view.findViewById(R.id.color_text);
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        textView.setText("Выбранный цвет: " + hexColor);

        return view;
    }

    private void copyColorToClipboard(int color) {
        String hexColor = String.format("#%06X", (0xFFFFFF & color));

        ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Color", hexColor);

        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Цвет скопирован: " + hexColor, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveColorAsImage(int color) {
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Заливаем цветом
        canvas.drawRGB(Color.red(color), Color.green(color), Color.blue(color));

        // Сохраняем изображение в MediaStore
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "color_" + System.currentTimeMillis() + ".png");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

            Uri uri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            try (OutputStream out = requireContext().getContentResolver().openOutputStream(uri)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                Toast.makeText(getContext(), "Цвет сохранен как изображение!", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ошибка при сохранении изображения", Toast.LENGTH_SHORT).show();
        }
    }
}