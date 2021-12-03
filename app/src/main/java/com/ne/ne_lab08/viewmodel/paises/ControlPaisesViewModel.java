package com.ne.ne_lab08.viewmodel.paises;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ne.ne_lab08.Event;
import com.ne.ne_lab08.R;
import com.ne.ne_lab08.model.DbPaises;
import com.ne.ne_lab08.model.entidades.Pais;

public class ControlPaisesViewModel extends ViewModel {
    private final MutableLiveData<String> nombre = new MutableLiveData<>();
    private final MutableLiveData<String> estado = new MutableLiveData<>();

    private final MutableLiveData<Event<Integer>> evento = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> error = new MutableLiveData<>();
    private int idPais=-1;
    Context context;

    public void setContext(Context context){
        this.context=context;
    }


    public void addPais(){
        boolean correcto = false;

        if ( nombre.getValue() == null || nombre.getValue().length()<=2 ){
            error.setValue(new Event<>(R.string.nombre_vacio));
            return;
        }

        final DbPaises dbPais= new DbPaises(context);

        correcto = dbPais.insertarPais(nombre.getValue());
        if(correcto){
            evento.setValue(new Event<>(R.string.registrado));
        }else{
            error.setValue(new Event<>(R.string.nombre_vacio));
        }
    }
    public void verPais(int id){
        final DbPaises dbPais = new DbPaises(context);
        Pais Pais = dbPais.verPais(id);

        if(Pais != null){
            nombre.setValue(Pais.getNombre());
            estado.setValue(Pais.getEstado());
            idPais=id;
        }
    }
    public void editarPais(){
        boolean correcto = false;

        if ( nombre.getValue() == null || nombre.getValue().length()<=2 ){
            error.setValue(new Event<>(R.string.nombre_vacio));
            return;
        }

        final DbPaises dbPais= new DbPaises(context);

        correcto = dbPais.editarPais(idPais,nombre.getValue());
        if(correcto){
            evento.setValue(new Event<>(R.string.editado));
        }else{
            error.setValue(new Event<>(R.string.fallo_database));
        }
    }
    public void eliminarPais(){
        final DbPaises dbPais= new DbPaises(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Desea eliminar este Pais?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (dbPais.eliminacionLogicaPais(idPais)) {
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
    public void activarPais(){
        boolean correcto = false;

        final DbPaises dbPais= new DbPaises(context);

        correcto = dbPais.activarPais(idPais);
        if(correcto){
            evento.setValue(new Event<>(R.string.activado));
        }else{
            error.setValue(new Event<>(R.string.fallo_database));
        }
    }
    public void inactivarPais(){
        boolean correcto = false;

        final DbPaises dbPais= new DbPaises(context);

        correcto = dbPais.inactivarPais(idPais);
        if(correcto){
            evento.setValue(new Event<>(R.string.inactivado));
        }else{
            error.setValue(new Event<>(R.string.fallo_database));
        }
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

}
