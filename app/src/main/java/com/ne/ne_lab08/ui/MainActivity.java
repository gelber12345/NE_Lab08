package com.ne.ne_lab08.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import com.ne.ne_lab08.model.DbHelper;
import android.widget.Toast;
import com.ne.ne_lab08.Event;
import com.ne.ne_lab08.R;
import com.ne.ne_lab08.databinding.ActivityMainBinding;
import com.ne.ne_lab08.ui.cargos.MainCargos;
import com.ne.ne_lab08.ui.paises.MainPaises;
import com.ne.ne_lab08.ui.personal.MainPersonal;
import com.ne.ne_lab08.viewmodel.MainViewModel;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private MainViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
        setupDataBase();
        setupNavigation();
    }

    private void setupDataBase() {
        DbHelper dbHelper = new DbHelper (this);
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        if ( db!= null){
            Toast.makeText(this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "BASE DE DATOS NO CREADA", Toast.LENGTH_LONG).show();
        }

    }
    private void setupNavigation() {
        // Abrir actividad para crear o editar categoría
        mViewModel.getEvento().observe(this, integerEvent -> {
            Integer id = integerEvent.getContentIfNotHandled();
            if (id != null) {
                if (id==0){
                    goToPersonal();
                }else if (id==1){
                    goToCargos();
                }else {
                    goToPaises();
                }

            }
        });
    }

    public void goToPersonal(){
        Intent intent=new Intent(this, MainPersonal.class);
        startActivity(intent);
    }
    public void goToCargos(){
        Intent intent=new Intent(this, MainCargos.class);
        startActivity(intent);
    }
    public void goToPaises(){
        Intent intent=new Intent(this, MainPaises.class);
        startActivity(intent);
    }
}