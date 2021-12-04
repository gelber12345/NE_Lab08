package com.ne.ne_lab08.ui.personal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ne.ne_lab08.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.ne.ne_lab08.databinding.ActivityMainPersonalBinding;
import com.ne.ne_lab08.model.DbPersonal;
import com.ne.ne_lab08.ui.MainActivity;
import com.ne.ne_lab08.ui.adaptadores.PersonalAdapter;
import com.ne.ne_lab08.ui.personal.ControlPersonal;
import com.ne.ne_lab08.viewmodel.personal.MainPersonalViewModel;
import com.ne.ne_lab08.Event;

public class MainPersonal extends AppCompatActivity {


    private ActivityMainPersonalBinding mBinding;
    private MainPersonalViewModel mViewModel;
     RecyclerView rview;
     SearchView sv;
     PersonalAdapter adapter;
    DbPersonal dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_personal);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_personal);
        mViewModel = new ViewModelProvider(this).get(MainPersonalViewModel.class);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
        mViewModel.setContext(this);

        sv = findViewById(R.id.buscarMainPersonal);
         dbHelper = new DbPersonal (this);
        rview = findViewById(R.id.listaPersonal);
        adapter = new PersonalAdapter(mViewModel);
        adapter.setPersonalList(dbHelper.mostrarPersonales("nombre"));
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
        Intent intent=new Intent(this, ControlPersonal.class);
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
        PersonalAdapter aux = (PersonalAdapter) rview.getAdapter();
        aux.setPersonalList(dbHelper.mostrarPersonales("id"));
        rview.getAdapter().notifyDataSetChanged();
    }
}