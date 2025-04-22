package ru.lisdevs.generaterandom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private List<ColorItem> favoriteColors;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Получаем список избранных цветов из аргументов или другого источника
        favoriteColors = getFavoriteColors(); // Реализуйте этот метод по своему усмотрению

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        GridView gridView = view.findViewById(R.id.grid_view);
        TextView emptyMessage = view.findViewById(R.id.empty_message); // Получаем TextView

        ColorAdapterNew adapter = new ColorAdapterNew(getContext(), favoriteColors);

        gridView.setAdapter(adapter);

        // Проверяем, есть ли избранные цвета
        if (favoriteColors.isEmpty()) {
            gridView.setVisibility(View.GONE); // Скрываем GridView
            emptyMessage.setVisibility(View.VISIBLE); // Показываем сообщение о пустом списке
        } else {
            gridView.setVisibility(View.VISIBLE); // Показываем GridView
            emptyMessage.setVisibility(View.GONE); // Скрываем сообщение о пустом списке
        }

        return view;
    }

    private List<ColorItem> getFavoriteColors() {
        // Здесь вы можете реализовать логику получения избранных цветов,
        // например, из SharedPreferences или базы данных.
        return new ArrayList<>(); // Верните список ваших избранных цветов.
    }
}
