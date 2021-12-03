package com.ne.ne_lab08.viewmodel.cargos;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ne.ne_lab08.Event;
import com.ne.ne_lab08.R;
import com.ne.ne_lab08.model.DbCargos;
import com.ne.ne_lab08.model.entidades.Cargo;

public class ControlCargosViewModel extends ViewModel {
    private final MutableLiveData<String> nombre = new MutableLiveData<>();
    private final MutableLiveData<String> estado = new MutableLiveData<>();

    private final MutableLiveData<Event<Integer>> evento = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> error = new MutableLiveData<>();
    private int idCargo=-1;
    Context context;

    public void setContext(Context context){
        this.context=context;
    }


    public void addCargo(){
        boolean correcto = false;

        if ( nombre.getValue() == null || nombre.getValue().length()<=2 ){
            error.setValue(new Event<>(R.string.nombre_vacio));
            return;
        }

        final DbCargos dbCargo= new DbCargos(context);

        correcto = dbCargo.insertarCargo(nombre.getValue());
        if(correcto){
            evento.setValue(new Event<>(R.string.registrado));
        }else{
            error.setValue(new Event<>(R.string.nombre_vacio));
        }
    }
    public void verCargo(int id){
        final DbCargos dbCargo = new DbCargos(context);
        Cargo Cargo = dbCargo.verCargo(id);

        if(Cargo != null){
            nombre.setValue(Cargo.getNombre());
            estado.setValue(Cargo.getEstado());
            idCargo=id;
        }
    }
    public void editarCargo(){
        boolean correcto = false;

        if ( nombre.getValue() == null || nombre.getValue().length()<=2 ){
            error.setValue(new Event<>(R.string.nombre_vacio));
            return;
        }

        final DbCargos dbCargo= new DbCargos(context);

        correcto = dbCargo.editarCargo(idCargo,nombre.getValue());
        if(correcto){
            evento.setValue(new Event<>(R.string.editado));
        }else{
            error.setValue(new Event<>(R.string.fallo_database));
        }
    }
    public void eliminarCargo(){
        final DbCargos dbCargo= new DbCargos(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Desea eliminar este Cargo?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (dbCargo.eliminacionLogicaCargo(idCargo)) {
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
    public void activarCargo(){
        boolean correcto = false;

        final DbCargos dbCargo= new DbCargos(context);

        correcto = dbCargo.activarCargo(idCargo);
        if(correcto){
            evento.setValue(new Event<>(R.string.activado));
        }else{
            error.setValue(new Event<>(R.string.fallo_database));
        }
    }
    public void inactivarCargo(){
        boolean correcto = false;

        final DbCargos dbCargo= new DbCargos(context);

        correcto = dbCargo.inactivarCargo(idCargo);
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
