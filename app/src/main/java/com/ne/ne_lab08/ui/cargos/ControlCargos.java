package com.ne.ne_lab08.ui.cargos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.ne.ne_lab08.R;
import com.ne.ne_lab08.databinding.ActivityControlCargosBinding;
import com.ne.ne_lab08.viewmodel.cargos.ControlCargosViewModel;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



public class ControlCargos extends AppCompatActivity {

    private ActivityControlCargosBinding mBinding;
    private ControlCargosViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_control_cargos);
        mViewModel = new ViewModelProvider(this).get(ControlCargosViewModel.class);
        mViewModel.setContext(this);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
        setupComponents();
        setupNavigation();
    }
    private void setupComponents(){
        if (isEdition() ){
            TextView textView = findViewById(R.id.txtTituloControlCargos);
            textView.setText("Añadir Cargos");

            findViewById(R.id.txtEstadoControlCargos).setVisibility(View.INVISIBLE);
            findViewById(R.id.txtValorEstadoControlCargos).setVisibility(View.INVISIBLE);

            findViewById(R.id.btnEditarControlCargos).setVisibility(View.INVISIBLE);
            findViewById(R.id.btnEliminarControlCargos).setVisibility(View.INVISIBLE);
            findViewById(R.id.lin2ControlCargos).setVisibility(View.INVISIBLE);

        }else{
            findViewById(R.id.btnAddControlCargos).setVisibility(View.INVISIBLE);
            mViewModel.verCargo(getIntent().getIntExtra("ID",-1));
        }
    }
    private void setupNavigation() {
        // Abrir actividad para crear o editar categoría
        mViewModel.getEvento().observe(this, integerEvent -> {
            Integer id = integerEvent.getContentIfNotHandled();
            if (id != null) {
                switch (id)
                {
                    case R.string.registrado:
                        Toast.makeText(ControlCargos.this,getText(R.string.registrado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.editado:
                        Toast.makeText(ControlCargos.this,getText(R.string.editado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.activado:
                        Toast.makeText(ControlCargos.this,getText(R.string.activado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.inactivado:
                        Toast.makeText(ControlCargos.this,getText(R.string.inactivado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.cancelar:
                        Toast.makeText(ControlCargos.this,getText(R.string.cancelar), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.eliminado:
                        Toast.makeText(ControlCargos.this,getText(R.string.eliminado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                }

            }
        });
        mViewModel.getError().observe(this, integerEvent -> {
            Integer id = integerEvent.getContentIfNotHandled();
            if (id != null) {
                switch (id)
                {
                    case R.string.nombre_vacio:
                        mBinding.etNombreControlCargos.setError(getText(id));
                        break;
                    case R.string.fallo_database:
                        Toast.makeText(ControlCargos.this,getText(R.string.fallo_database), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;

                }

            }
        });

    }

    private boolean isEdition() {

        Log.d("ID" , "aaaaaaaaaaaaaaaaaaaaaaaaaaaa  "+getIntent().getIntExtra("ID",-2));
        return getIntent() != null
                && getIntent().getIntExtra("ID",-2) == -1;
    }

    private void Regresar(){
        Intent intent = new Intent(this, MainCargos.class);;
        startActivity(intent);
        this.finish();
    }
}