package ru.lisdevs.generaterandom.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "colors.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAVORITE_COLORS = "favorite_colors";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_COLOR = "color";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_FAVORITE_COLORS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COLOR + " INTEGER NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_COLORS);
        onCreate(db);
    }

    // Метод для добавления цвета в избранное
    public void addFavoriteColor(int color) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COLOR, color);
        db.insert(TABLE_FAVORITE_COLORS, null, values);
        db.close();
    }

    // Метод для получения всех избранных цветов
    public List<Integer> getFavoriteColors() {
        List<Integer> colors = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITE_COLORS,
                new String[]{COLUMN_COLOR},
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                colors.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return colors;
    }

    // Метод для очистки базы данных
    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FAVORITE_COLORS);
        db.close();
    }

    // Метод для добавления цвета в избранное
    public void addColorToFavorites(int color) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("color", color);
        db.insert(TABLE_FAVORITE_COLORS, null, values);
        db.close();
    }

    public void removeFavoriteColor(int color) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITE_COLORS, "color=?", new String[]{String.valueOf(color)});
        db.close();
    }
}
