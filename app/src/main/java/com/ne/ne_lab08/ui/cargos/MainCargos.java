package com.ne.ne_lab08.ui.cargos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ne.ne_lab08.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.ne.ne_lab08.databinding.ActivityMainCargosBinding;
import com.ne.ne_lab08.model.DbCargos;

import com.ne.ne_lab08.ui.MainActivity;
import com.ne.ne_lab08.ui.adaptadores.CargoAdapter;

import com.ne.ne_lab08.viewmodel.cargos.MainCargosViewModel;



public class MainCargos extends AppCompatActivity {

    private ActivityMainCargosBinding mBinding;
    private MainCargosViewModel mViewModel;
    RecyclerView rview;
    SearchView sv;
    CargoAdapter adapter;
    DbCargos dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_cargos);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_cargos);
        mViewModel = new ViewModelProvider(this).get(MainCargosViewModel.class);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
        sv = findViewById(R.id.buscarMainCargos);
        dbHelper = new DbCargos(this);
        rview = findViewById(R.id.listaCargos);

        adapter = new CargoAdapter(mViewModel);
        adapter.setCargosList(dbHelper.mostrarCargos("nombre"));
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
       Intent intent=new Intent(this, ControlCargos.class);
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
        CargoAdapter aux = (CargoAdapter) rview.getAdapter();
        aux.setCargosList(dbHelper.mostrarCargos("id"));
        rview.getAdapter().notifyDataSetChanged();
    }

}