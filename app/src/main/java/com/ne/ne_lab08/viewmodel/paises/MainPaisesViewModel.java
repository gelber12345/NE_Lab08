package com.ne.ne_lab08.viewmodel.paises;

import android.widget.SearchView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ne.ne_lab08.Event;

public class MainPaisesViewModel extends ViewModel implements SearchView.OnQueryTextListener{

    private final MutableLiveData<Event<Integer>> evento = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> lista = new MutableLiveData<>();
    private final MutableLiveData<String> filtro = new MutableLiveData<>();

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
}
