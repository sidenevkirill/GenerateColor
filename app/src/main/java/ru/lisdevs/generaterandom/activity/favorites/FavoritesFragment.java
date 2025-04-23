package ru.lisdevs.generaterandom.activity.favorites;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.lisdevs.generaterandom.database.DatabaseHelper;
import ru.lisdevs.generaterandom.R;
import ru.lisdevs.generaterandom.adapter.ColorAdapterHistory;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ColorAdapterHistory colorAdapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_colors, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // Инициализируем базу данных
        databaseHelper = new DatabaseHelper(getContext());

        // Получаем избранные цвета из базы данных
        List<Integer> favoriteColors = databaseHelper.getFavoriteColors();

        // Создаем адаптер и устанавливаем его для RecyclerView
        colorAdapter = new ColorAdapterHistory(getContext(), favoriteColors);
        recyclerView.setAdapter(colorAdapter);

        return view;
    }
}