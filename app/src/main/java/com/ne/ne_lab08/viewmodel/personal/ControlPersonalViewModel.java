package com.ne.ne_lab08.viewmodel.personal;

import android.app.DatePickerDialog;
import android.app.Person;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ne.ne_lab08.Event;
import com.ne.ne_lab08.R;
import com.ne.ne_lab08.model.DbCargos;
import com.ne.ne_lab08.model.DbPaises;
import com.ne.ne_lab08.model.DbPersonal;
import com.ne.ne_lab08.model.entidades.Cargo;
import com.ne.ne_lab08.model.entidades.Pais;
import com.ne.ne_lab08.model.entidades.Personal;

import java.util.ArrayList;

public class ControlPersonalViewModel extends ViewModel implements DatePickerDialog.OnDateSetListener {
    private final MutableLiveData<String> dni = new MutableLiveData<>();
    private final MutableLiveData<String> nombre = new MutableLiveData<>();
    private final MutableLiveData<String> estado = new MutableLiveData<>();
    private final MutableLiveData<String> dia = new MutableLiveData<>();
    private final MutableLiveData<String> mes = new MutableLiveData<>();
    private final MutableLiveData<String> year = new MutableLiveData<>();

    private final MutableLiveData<Event<Integer>> evento = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> error = new MutableLiveData<>();
    private final ArrayList<String> cargos = new ArrayList<String>();
    private final ArrayList<String> paises = new ArrayList<String>();
    private   ArrayList<Cargo> cargoArrayList= new ArrayList<Cargo>();
    private   ArrayList<Pais> paisArrayList = new ArrayList<Pais>();
    private  Spinner spinnerCargos = null;
    private  Spinner spinnerPaises =null;
    private int idPersonal=-1;
    private int idPais = -1;
    private int idCargo =-1;
    Context context;


    public void setContext(Context context){
        this.context=context;
    }

    public void inicializarSpinner(Spinner spinner1,Spinner spinner2 ){
        this.spinnerCargos =spinner1;
        this.spinnerPaises =spinner2;
        final DbCargos dbCargo = new DbCargos(context);
        final DbPaises dbPais= new DbPaises(context);

        paisArrayList = dbPais.mostrarPaises("id");
        cargoArrayList = dbCargo.mostrarCargos("id");

        for (int i = 0 ; i< cargoArrayList.size();i++){
            cargos.add(cargoArrayList.get(i).getNombre());
        }
        for (int i = 0 ; i< paisArrayList.size();i++){
            paises.add(paisArrayList.get(i).getNombre());
        }
    }
    public void addPersonal(){
        boolean correcto = false;

        if ( nombre.getValue() == null || nombre.getValue().length()<4 ){
            error.setValue(new Event<>(R.string.nombre_vacio));
            return;
        }
        if ( dni.getValue() == null || dni.getValue().length()<8){
            error.setValue(new Event<>(R.string.dni_invalido));
            return;
        }
        if ( dia.getValue() == null){
            error.setValue(new Event<>(R.string.fecha_invalida));
            return;
        }
        if ( mes.getValue() == null ){
            error.setValue(new Event<>(R.string.fecha_invalida));
            return;
        }
        if ( year.getValue() == null ){
            error.setValue(new Event<>(R.string.fecha_invalida));
            return;
        }

        idCargo= spinnerCargos.getSelectedItemPosition()+1;
        idPais= spinnerPaises.getSelectedItemPosition()+1;
        final DbPersonal dbPersonal= new DbPersonal(context);

        correcto = dbPersonal.insertarPersonal(nombre.getValue(),dni.getValue(),Integer.parseInt(dia.getValue()),Integer.parseInt(mes.getValue()),Integer.parseInt(year.getValue()),idCargo,idPais);
        if(correcto){
            evento.setValue(new Event<>(R.string.registrado));
        }else{
            error.setValue(new Event<>(R.string.nombre_vacio));
        }
    }
    public void verPersonal(int id){
        final DbPersonal dbPersonal = new DbPersonal(context);
        Personal personal = dbPersonal.verPersonal(id);

        if(personal != null){
            nombre.setValue(personal.getNombre());
            dni.setValue(personal.getDni());
            dia.setValue(""+personal.getDia());
            mes.setValue(""+personal.getMes());
            year.setValue(""+personal.getYear());
            estado.setValue(personal.getEstado());
            spinnerPaises.setSelection(personal.getId_pais()-1);
            spinnerCargos.setSelection(personal.getId_cargo()-1);
            idPersonal=id;
        }
    }
    public void editarPersonal(){
        boolean correcto = false;

        if ( nombre.getValue() == null || nombre.getValue().length()<4 ){
            error.setValue(new Event<>(R.string.nombre_vacio));
            return;
        }
        if ( dni.getValue() == null || dni.getValue().length()<8){
            error.setValue(new Event<>(R.string.dni_invalido));
            return;
        }
        if ( dia.getValue() == null){
            error.setValue(new Event<>(R.string.fecha_invalida));
            return;
        }
        if ( mes.getValue() == null ){
            error.setValue(new Event<>(R.string.fecha_invalida));
            return;
        }
        if ( year.getValue() == null ){
            error.setValue(new Event<>(R.string.fecha_invalida));
            return;
        }
        idCargo= spinnerCargos.getSelectedItemPosition()+1;
        idPais= spinnerPaises.getSelectedItemPosition()+1;
        final DbPersonal dbPersonal= new DbPersonal(context);

        correcto = dbPersonal.editarPersonal(idPersonal,nombre.getValue(),dni.getValue(),Integer.parseInt(dia.getValue()),Integer.parseInt(mes.getValue()),Integer.parseInt(year.getValue()),idCargo,idPais);
        if(correcto){
            evento.setValue(new Event<>(R.string.editado));
        }else{
            error.setValue(new Event<>(R.string.fallo_database));
        }
    }
    public void eliminarPersonal(){
        final DbPersonal dbPersonal= new DbPersonal(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Desea eliminar este personal?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (dbPersonal.eliminacionLogicaPersonal(idPersonal)) {
                            evento.setValue(new Event<>(R.string.eliminado));
                        }
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
    public void activarPersonal(){
        boolean correcto = false;

        final DbPersonal dbPersonal= new DbPersonal(context);

        correcto = dbPersonal.activarPersonal(idPersonal);
        if(correcto){
            evento.setValue(new Event<>(R.string.activado));
        }else{
            error.setValue(new Event<>(R.string.fallo_database));
        }
    }
    public void inactivarPersonal(){
        boolean correcto = false;

        final DbPersonal dbPersonal= new DbPersonal(context);

        correcto = dbPersonal.inactivarPersonal(idPersonal);
        if(correcto){
            evento.setValue(new Event<>(R.string.inactivado));
        }else{
            error.setValue(new Event<>(R.string.fallo_database));
        }
    }
    public void editarFecha(){
        evento.setValue(new Event<>(R.string.fechaEdit));
    }

    public void cancelar(){
        evento.setValue(new Event<>(R.string.cancelar));
    }

    public MutableLiveData<Event<Integer>> getEvento() {
        return evento;
    }


    public MutableLiveData<String> getEstado() {
        return estado;
    }

    public MutableLiveData<String> getNombre() {
        return nombre;
    }

    public MutableLiveData<Event<Integer>> getError() {
        return error;
    }

    public MutableLiveData<String> getDia() {
        return dia;
    }

    public MutableLiveData<String> getMes() {
        return mes;
    }

    public MutableLiveData<String> getYear() {
        return year;
    }

    public MutableLiveData<String> getDni() {
        return dni;
    }

    public ArrayList<String> getCargos() {
        return cargos;
    }

    public ArrayList<String> getPaises() {
        return paises;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        year.setValue("" +i);
        mes.setValue("" +i1);
        dia.setValue(""+ i2);
    }


}

