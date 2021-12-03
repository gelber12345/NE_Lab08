package com.ne.ne_lab08.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ne.ne_lab08.model.entidades.Cargo;
import com.ne.ne_lab08.model.entidades.Pais;

import java.util.ArrayList;

public class DbPaises extends DbHelper {
    Context context;

    public DbPaises(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public boolean insertarPais(String nombre) {

        boolean correcto = false;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);

            db.insert(TABLE_PAISES, null, values);
            correcto= true;
        } catch (Exception ex) {
            ex.toString();
        }

        return correcto;
    }

    public ArrayList<Pais> mostrarPaises(String filtro) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Pais> listaPaises = new ArrayList<>();
        Pais pais;
        Cursor cursorPaises;

        cursorPaises = db.rawQuery("SELECT * FROM " + TABLE_PAISES + " ORDER BY "+ filtro +" ASC", null);

        if (cursorPaises.moveToFirst()) {
            do {
                pais = new Pais();
                pais.setId(cursorPaises.getInt(0));
                pais.setNombre(cursorPaises.getString(1));
                pais.setEstado(cursorPaises.getString(2));
                listaPaises.add(pais);
            } while (cursorPaises.moveToNext());
        }

        cursorPaises.close();

        return listaPaises;
    }

    public Pais verPais(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Pais pais = null;
        Cursor cursorPaises;

        cursorPaises = db.rawQuery("SELECT * FROM " + TABLE_PAISES + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorPaises.moveToFirst()) {
            pais = new Pais();
            pais.setId(cursorPaises.getInt(0));
            pais.setNombre(cursorPaises.getString(1));
            pais.setEstado(cursorPaises.getString(2));
        }

        cursorPaises.close();

        return pais;
    }
    public boolean editarPais(int id, String nombre) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_PAISES + " SET nombre = '" + nombre + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            Log.d("Erorrrrrrr: ", ex.toString() );
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean activarPais(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_PAISES + " SET estado = 'A' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean inactivarPais(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_PAISES + " SET estado = 'I' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean eliminacionLogicaPais(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_PAISES + " SET estado = '*' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean eliminarPais(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_PAISES + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

}
