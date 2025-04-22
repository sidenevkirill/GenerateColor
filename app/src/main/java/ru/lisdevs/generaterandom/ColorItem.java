package ru.lisdevs.generaterandom;

public class ColorItem {
    private int color;
    private boolean isFavorite;

    public ColorItem(int color) {
        this.color = color;
        this.isFavorite = false; // По умолчанию не избранный
    }

    public int getColor() {
        return color;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
