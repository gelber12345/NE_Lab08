package com.ne.ne_lab08.ui.paises;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ne.ne_lab08.R;
import com.ne.ne_lab08.databinding.ActivityMainPaisesBinding;
import com.ne.ne_lab08.model.DbPaises;
import com.ne.ne_lab08.ui.MainActivity;
import com.ne.ne_lab08.ui.adaptadores.PaisAdapter;
import com.ne.ne_lab08.viewmodel.paises.MainPaisesViewModel;


import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

public class MainPaises extends AppCompatActivity {

    private ActivityMainPaisesBinding mBinding;
    private MainPaisesViewModel mViewModel;
    RecyclerView rview;
    SearchView sv;
    PaisAdapter adapter;
    DbPaises dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_paises);
        mViewModel = new ViewModelProvider(this).get(MainPaisesViewModel.class);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
        sv = findViewById(R.id.buscarMainPaises);
        dbHelper = new DbPaises(this);
        rview = findViewById(R.id.listaPaises);
        adapter = new PaisAdapter(mViewModel);
        adapter.setPaisesList(dbHelper.mostrarPaises("nombre"));
        rview.setAdapter(adapter);

        sv.setOnQueryTextListener(mViewModel);
        setupNavigation();
    }
    private void setupNavigation() {
        // Abrir actividad para crear o editar categoría
        mViewModel.getEvento().observe(this, integerEvent -> {
            Integer id = integerEvent.getContentIfNotHandled();
            if (id != null) {
                if (id == -1){
                    goToControl(-1);
                } else if(id==-2){
                    back();
                }else{
                    goToControl(id);
                }

            }
        });
        mViewModel.getLista().observe(this, integerEvent -> {
            Integer id = integerEvent.getContentIfNotHandled();
            if (id != null) {
                if (id == 1){
                    ordenarId();
                }

            }
        });
        mViewModel.getFiltro().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                adapter.filtrado(s);
            }
        });
    }

    public void goToControl(int id){
        Intent intent=new Intent(this, ControlPaises.class);
        intent.putExtra("ID", id);
        startActivity(intent);
        this.finish();
    }
    public void back(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void ordenarId(){
        PaisAdapter aux = (PaisAdapter) rview.getAdapter();
        aux.setPaisesList(dbHelper.mostrarPaises("id"));
        rview.getAdapter().notifyDataSetChanged();
    }
}