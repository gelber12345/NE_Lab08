package com.ne.ne_lab08.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "sistema.db";
    public static final String TABLE_PERSONAL = "t_personal";
    public static final String TABLE_CARGOS = "t_cargos";
    public static final String TABLE_PAISES = "t_paises";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PERSONAL + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "dni TEXT NOT NULL," +
                "dia INTEGER NOT NULL," +
                "mes INTEGER NOT NULL," +
                "year INTEGER NOT NULL," +
                "estado TEXT DEFAULT 'A') ");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CARGOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "estado TEXT DEFAULT 'A') ");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PAISES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "estado TEXT DEFAULT 'A') ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PERSONAL);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CARGOS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PAISES);
        onCreate(sqLiteDatabase);

    }
}
