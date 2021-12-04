package com.ne.ne_lab08.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ne.ne_lab08.model.entidades.Personal;

import java.util.ArrayList;

public class DbPersonal extends DbHelper{
    Context context;

    public DbPersonal(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public boolean insertarPersonal(String nombre, String dni,int dia,int mes,int year,int id_cargo,int id_pais) {

        boolean correcto = false;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("dni", dni);
            values.put("dia", dia);
            values.put("mes", mes);
            values.put("year", year);
            values.put("id_cargo", id_cargo);
            values.put("id_pais", id_pais);
            db.insert(TABLE_PERSONAL, null, values);
            correcto= true;
        } catch (Exception ex) {
            ex.toString();
        }

        return correcto;
    }

    public ArrayList<Personal> mostrarPersonales(String filtro) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Personal> listaPersonal = new ArrayList<>();
        Personal personal;
        Cursor cursorPersonal;

        cursorPersonal = db.rawQuery("SELECT * FROM " + TABLE_PERSONAL + " ORDER BY "+ filtro +" ASC", null);

        if (cursorPersonal.moveToFirst()) {
            do {
                personal = new Personal();
                personal.setId(cursorPersonal.getInt(0));
                personal.setNombre(cursorPersonal.getString(1));
                personal.setDni(cursorPersonal.getString(2));
                personal.setDia(cursorPersonal.getString(3));
                personal.setMes(cursorPersonal.getString(4));
                personal.setYear(cursorPersonal.getString(5));
                personal.setId_cargo(cursorPersonal.getInt(6));
                personal.setId_pais(cursorPersonal.getInt(7));
                personal.setEstado(cursorPersonal.getString(8));
                listaPersonal.add(personal);
            } while (cursorPersonal.moveToNext());
        }

        cursorPersonal.close();

        return listaPersonal;
    }

    public Personal verPersonal(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Personal personal = null;
        Cursor cursorPersonal;

        cursorPersonal = db.rawQuery("SELECT * FROM " + TABLE_PERSONAL + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorPersonal.moveToFirst()) {
            personal = new Personal();
            personal.setId(cursorPersonal.getInt(0));
            personal.setNombre(cursorPersonal.getString(1));
            personal.setDni(cursorPersonal.getString(2));
            personal.setDia(cursorPersonal.getString(3));
            personal.setMes(cursorPersonal.getString(4));
            personal.setYear(cursorPersonal.getString(5));
            personal.setId_cargo(cursorPersonal.getInt(6));
            personal.setId_pais(cursorPersonal.getInt(7));
            personal.setEstado(cursorPersonal.getString(8));
        }

        cursorPersonal.close();

        return personal;
    }
    public boolean editarPersonal(int id, String nombre, String dni,int dia,int mes,int year,int id_cargo,int id_pais) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_PERSONAL + " SET nombre = '" + nombre + "', dni = '" + dni+ "', dia = '"+ dia +"', mes = '"+ mes +"' , year = '"+ year +"' , id_cargo = '"+ id_cargo +"' , id_pais = '"+ id_pais +"' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            Log.d("Erorrrrrrr: ", ex.toString() );
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean activarPersonal(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_PERSONAL + " SET estado = 'A' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean inactivarPersonal(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_PERSONAL + " SET estado = 'I' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean eliminacionLogicaPersonal(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_PERSONAL + " SET estado = '*' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean eliminarPersonal(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_PERSONAL + " WHERE id = '" + id + "'");
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
