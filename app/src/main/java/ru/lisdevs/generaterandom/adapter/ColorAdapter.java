package ru.lisdevs.generaterandom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import android.widget.TextView;

import ru.lisdevs.generaterandom.R;

public class ColorAdapter extends BaseAdapter {

    private Context context;
    private List<Integer> colors;

    public ColorAdapter(Context context, List<Integer> colors) {
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
            // Инфлейтим лэйаут из XML
            convertView = LayoutInflater.from(context).inflate(R.layout.color_item, parent, false);
            holder = new ViewHolder();
            holder.colorView = convertView.findViewById(R.id.color_view);
            holder.colorText = convertView.findViewById(R.id.color_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Устанавливаем цвет фона
        int color = colors.get(position);
        holder.colorView.setBackgroundColor(color);

        // Устанавливаем текст цвета (в формате HEX)
        String colorHex = String.format("#%06X", (0xFFFFFF & color));
        holder.colorText.setText(colorHex);

        return convertView; // Возвращаем элемент для отображения в GridView
    }

    static class ViewHolder {
        View colorView; // Представление для отображения цвета
        TextView colorText; // Текстовое представление цвета
    }
}