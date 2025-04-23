package ru.lisdevs.generaterandom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.lisdevs.generaterandom.database.DatabaseHelper;
import ru.lisdevs.generaterandom.R;

public class ColorAdapterHistory extends RecyclerView.Adapter<ColorAdapterHistory.ColorViewHolder> {

    private Context context;
    private List<Integer> colors;

    public ColorAdapterHistory(Context context, List<Integer> colors) {
        this.context = context;
        this.colors = colors;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.color_items, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        int color = colors.get(position);
        holder.itemView.setBackgroundColor(color);

        // Если нужно добавить текстовое представление цвета (например, HEX-код)
        holder.colorText.setText(String.format("#%06X", (0xFFFFFF & color)));

        // Установка обработчика нажатия для удаления цвета
        holder.deleteButton.setOnClickListener(v -> {
            removeColor(position);
            // Удаляем цвет из базы данных
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.removeFavoriteColor(color);
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public void removeColor(int position) {
        colors.remove(position);
        notifyItemRemoved(position);
    }

    static class ColorViewHolder extends RecyclerView.ViewHolder {
        TextView colorText;
        ImageButton deleteButton;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            colorText = itemView.findViewById(R.id.color_text); // Убедитесь, что у вас есть TextView в item_color.xml
            deleteButton = itemView.findViewById(R.id.delete_button);

        }
    }
}
