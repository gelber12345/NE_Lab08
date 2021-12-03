package com.ne.ne_lab08.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ne.ne_lab08.model.entidades.Cargo;
import com.ne.ne_lab08.model.entidades.Personal;

import java.util.ArrayList;

public class DbCargos extends DbHelper{
    Context context;

    public DbCargos(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public boolean insertarCargo(String nombre) {

        boolean correcto = false;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);

            db.insert(TABLE_CARGOS, null, values);
            correcto= true;
        } catch (Exception ex) {
            ex.toString();
        }

        return correcto;
    }

    public ArrayList<Cargo> mostrarCargos(String filtro) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Cargo> listaCargos = new ArrayList<>();
        Cargo cargo;
        Cursor cursorCargos;

        cursorCargos = db.rawQuery("SELECT * FROM " + TABLE_CARGOS + " ORDER BY "+ filtro +" ASC", null);

        if (cursorCargos.moveToFirst()) {
            do {
                cargo = new Cargo();
                cargo.setId(cursorCargos.getInt(0));
                cargo.setNombre(cursorCargos.getString(1));
                cargo.setEstado(cursorCargos.getString(2));
                listaCargos.add(cargo);
            } while (cursorCargos.moveToNext());
        }

        cursorCargos.close();

        return listaCargos;
    }

    public Cargo verCargo(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cargo cargo = null;
        Cursor cursorCargos;

        cursorCargos = db.rawQuery("SELECT * FROM " + TABLE_CARGOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorCargos.moveToFirst()) {
            cargo = new Cargo();
            cargo.setId(cursorCargos.getInt(0));
            cargo.setNombre(cursorCargos.getString(1));
            cargo.setEstado(cursorCargos.getString(2));
        }

        cursorCargos.close();

        return cargo;
    }
    public boolean editarCargo(int id, String nombre) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CARGOS + " SET nombre = '" + nombre + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            Log.d("Erorrrrrrr: ", ex.toString() );
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean activarCargo(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CARGOS + " SET estado = 'A' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean inactivarCargo(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CARGOS + " SET estado = 'I' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean eliminacionLogicaCargo(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CARGOS + " SET estado = '*' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean eliminarCargo(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CARGOS + " WHERE id = '" + id + "'");
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
