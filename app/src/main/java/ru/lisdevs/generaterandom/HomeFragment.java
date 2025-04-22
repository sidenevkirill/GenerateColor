package ru.lisdevs.generaterandom;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;


public class HomeFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView, imageView1;
    private TextView colorTextView;
    private static final String PREFS_NAME = "ThemePrefs";
    private static final String KEY_THEME = "theme";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_blank, container, false);

        imageView = root.findViewById(R.id.imageView);
        imageView1 = root.findViewById(R.id.imageView1);
        colorTextView = root.findViewById(R.id.colorTextView);
        LinearLayout copyButton = root.findViewById(R.id.copy_button);

        // Обработчик клика для выбора изображения
        imageView.setOnClickListener(v -> openGallery());
        copyButton.setOnClickListener(v -> copyColorToClipboard());


        ImageButton toggleBottomSheet = root.findViewById(R.id.toggleBottomSheet);
        toggleBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });

        return root;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == -1 && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                loadImageAndExtractColor(imageUri);
            }
        }
    }

    private void loadImageAndExtractColor(Uri imageUri) {
        Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .placeholder(R.drawable.add_placeholder) // Замените своим изображением-заглушкой
                .error(R.drawable.add_placeholder) // Замените своим изображением для ошибки
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Установите изображение в ImageView
                        imageView.setImageBitmap(resource);

                        int pixel = resource.getPixel(resource.getWidth() / 2, resource.getHeight() / 2);
                        String hexColor = String.format("#%06X", (0xFFFFFF & pixel));
                        colorTextView.setText(hexColor);

                        // Генерируем палитру и извлекаем доминантный цвет
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(@NonNull Palette palette) {
                                int dominantColor = palette.getDominantColor(0xFF000000); // Черный по умолчанию

                                // Установите цвет фона для второго ImageView
                                imageView1.setBackgroundColor(dominantColor);
                            }
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Обработка если нужно, хотя это может быть редко вызываемым событием.
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        Toast.makeText(getContext(), "Не удалось загрузить изображение", Toast.LENGTH_SHORT).show();
                        imageView.setImageDrawable(errorDrawable); // Показать изображение ошибки в ImageView
                    }
                });
    }

    private void copyColorToClipboard() {
        String hexColor = colorTextView.getText().toString();

        if (!hexColor.isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Color", hexColor);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Цвет скопирован: " + hexColor, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Нет цвета для копирования", Toast.LENGTH_SHORT).show();
        }
    }

    private void showBottomDialog() {
        Dialog bottomSheetDialog = new Dialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);
        LinearLayout theme = bottomSheetDialog.findViewById(R.id.theme);
        LinearLayout settings = bottomSheetDialog.findViewById(R.id.down);
        View decorView = bottomSheetDialog.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SCREEN_STATE_OFF);
        bottomSheetDialog.dismiss();
        //
        Window window = bottomSheetDialog.getWindow();
        if (window == null) {
            return;
        }

        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTheme();
                bottomSheetDialog.dismiss();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAbout(v);
                bottomSheetDialog.dismiss();
            }
        });

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setNavigationBarColor(getResources().getColor(R.color.white));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);
        windowAttributes.gravity = Gravity.BOTTOM;

        //  btnClose.setOnClickListener(view -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();
    }

    public void onSettings(View view) {
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        startActivity(intent);
    }

    public void onAbout(View view) {
        Intent intent = new Intent(getContext(), AboutActivity.class);
        startActivity(intent);
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