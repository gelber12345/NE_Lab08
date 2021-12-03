package com.ne.ne_lab08.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ne.ne_lab08.Event;
public class MainViewModel extends ViewModel {

    private final MutableLiveData<Event<Integer>> evento = new MutableLiveData<>();

    // Abrir la categoría a editar
    public void goToPersonal(int id) {
        evento.setValue(new Event<>(id));
    }
    public void goToPersonal2(int id) {
        evento.setValue(new Event<>(id));
    }
    public void goToPersonal3(int id) {
        evento.setValue(new Event<>(id));
    }

    public MutableLiveData<Event<Integer>> getEvento() {
        return evento;
    }
}
