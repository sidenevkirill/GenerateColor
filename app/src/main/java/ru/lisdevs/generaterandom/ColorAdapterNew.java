package ru.lisdevs.generaterandom;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;

public class ColorAdapterNew extends BaseAdapter {

    private Context context;
    private List<ColorItem> colors; // Список объектов ColorItem

    public ColorAdapterNew(Context context, List<ColorItem> colors) {
        this.context = context;
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {
        return colors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.color_item, parent, false);
            holder = new ViewHolder();
            holder.colorView = convertView.findViewById(R.id.color_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ColorItem colorItem = colors.get(position);

        // Устанавливаем цвет фона
        holder.colorView.setBackgroundColor(colorItem.getColor());

        // Слушатель нажатия для копирования цвета в буфер обмена
        holder.colorView.setOnClickListener(v -> copyColorToClipboard(colorItem.getColor()));

        // Слушатель нажатия для добавления/удаления из избранного
        holder.colorView.setOnLongClickListener(v -> {
            colorItem.setFavorite(!colorItem.isFavorite());
            Toast.makeText(context, colorItem.isFavorite() ? "Добавлено в избранное" : "Удалено из избранного", Toast.LENGTH_SHORT).show();
            saveFavorites(); // Сохраняем изменения в избранном
            return true; // Возвращаем true, чтобы показать, что событие обработано
        });

        return convertView;
    }

    private void copyColorToClipboard(int color) {
        String hexColor = String.format("#%06X", (0xFFFFFF & color));

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Color", hexColor);

        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Цвет скопирован: " + hexColor, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveFavorites() {
        // Здесь вы можете реализовать логику сохранения избранных цветов,
        // например, в SharedPreferences или базе данных.
    }

    static class ViewHolder {
        View colorView; // Представление для отображения цвета
    }
}
