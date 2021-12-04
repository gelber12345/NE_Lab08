package com.ne.ne_lab08.viewmodel.personal;
import android.content.Context;
import android.widget.SearchView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ne.ne_lab08.Event;
import com.ne.ne_lab08.model.DbCargos;
import com.ne.ne_lab08.model.DbPaises;
import com.ne.ne_lab08.model.entidades.Cargo;
import com.ne.ne_lab08.model.entidades.Pais;

public class MainPersonalViewModel extends ViewModel implements SearchView.OnQueryTextListener{
    private final MutableLiveData<Event<Integer>> evento = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> lista = new MutableLiveData<>();
    private final MutableLiveData<String> filtro = new MutableLiveData<>();
    Context context;

    public void setContext(Context context){
        this.context =context;
    }
    // Abrir la categoría a editar
    public void goToControl(int id) {
        evento.setValue(new Event<>(id));
    }
    public void back(int id) {
        evento.setValue(new Event<>(id));
    }
    public void ordenar(){
        lista.setValue(new Event<>(1));
    }

    public MutableLiveData<Event<Integer>> getEvento() {
        return evento;
    }

    public MutableLiveData<Event<Integer>> getLista() {
        return lista;
    }

    public MutableLiveData<String> getFiltro() {
        return filtro;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filtro.setValue(s);
        return false;
    }
    public Context getContext(){
        return context;
    }

    public String getStringCargo(int id){
        final DbCargos dbCargo = new DbCargos(context);
        String res ="";
        res = dbCargo.verCargo(id).getNombre();
        return res;
    }
    public String getStringPais(int id){
        final DbPaises dbPais= new DbPaises(context);
        String res ="";
        res = dbPais.verPais(id).getNombre();
        return res;
    }
}
