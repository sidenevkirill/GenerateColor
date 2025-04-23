package ru.lisdevs.generaterandom.activity.palette;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import ru.lisdevs.generaterandom.R;
import ru.lisdevs.generaterandom.adapter.ColorAdapter;
import ru.lisdevs.generaterandom.database.DatabaseHelper;

public class ColorPickerFragment extends Fragment {

    private GridView gridView;
    private List<Integer> colors;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_picker, container, false);

        gridView = view.findViewById(R.id.grid_view);

        databaseHelper = new DatabaseHelper(getContext());

        colors = generateColors(3000); // Генерируем 3000 цветов

        ColorAdapter colorAdapter = new ColorAdapter(getContext(), colors);
        gridView.setAdapter(colorAdapter);

        // Устанавливаем слушатель кликов на элементы GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int color = colors.get(position);
                onColorClick(color);
            }
        });

        return view;
    }

    private List<Integer> generateColors(int count) {
        List<Integer> colorList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            // Генерируем случайные значения для RGB
            int r = (int) (Math.random() * 256);
            int g = (int) (Math.random() * 256);
            int b = (int) (Math.random() * 256);

            // Создаем цвет в формате ARGB
            int color = 0xff000000 | (r << 16) | (g << 8) | b; // Прозрачность - 255 (0xff)
            colorList.add(color);
        }

        return colorList;
    }

    public void onColorClick(int color) {

        databaseHelper.addFavoriteColor(color);

        ColorDisplayFragment colorDisplayFragment = ColorDisplayFragment.newInstance(color);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, colorDisplayFragment)
                .addToBackStack(null)
                .commit();
    }
}