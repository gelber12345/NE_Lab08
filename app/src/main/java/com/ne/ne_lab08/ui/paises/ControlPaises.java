package com.ne.ne_lab08.ui.paises;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ne.ne_lab08.R;
import com.ne.ne_lab08.databinding.ActivityControlPaisesBinding;
import com.ne.ne_lab08.ui.paises.ControlPaises;
import com.ne.ne_lab08.viewmodel.paises.ControlPaisesViewModel;

public class ControlPaises extends AppCompatActivity {
    private ActivityControlPaisesBinding mBinding;
    private ControlPaisesViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_control_paises);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_control_paises);
        mViewModel = new ViewModelProvider(this).get(ControlPaisesViewModel.class);
        mViewModel.setContext(this);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
        setupComponents();
        setupNavigation();
    }
    private void setupComponents(){
        if (isEdition() ){
            TextView textView = findViewById(R.id.txtTituloControlPaises);
            textView.setText("Añadir Pais");

            findViewById(R.id.txtEstadoControlPaises).setVisibility(View.INVISIBLE);
            findViewById(R.id.txtValorEstadoControlPaises).setVisibility(View.INVISIBLE);

            findViewById(R.id.btnEditarControlPaises).setVisibility(View.INVISIBLE);
            findViewById(R.id.btnEliminarControlPaises).setVisibility(View.INVISIBLE);
            findViewById(R.id.lin2ControlPaises).setVisibility(View.INVISIBLE);

        }else{
            findViewById(R.id.btnAddControlPaises).setVisibility(View.INVISIBLE);
            mViewModel.verPais(getIntent().getIntExtra("ID",-1));
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
                        Toast.makeText(ControlPaises.this,getText(R.string.registrado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.editado:
                        Toast.makeText(ControlPaises.this,getText(R.string.editado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.activado:
                        Toast.makeText(ControlPaises.this,getText(R.string.activado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.inactivado:
                        Toast.makeText(ControlPaises.this,getText(R.string.inactivado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.cancelar:
                        Toast.makeText(ControlPaises.this,getText(R.string.cancelar), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.eliminado:
                        Toast.makeText(ControlPaises.this,getText(R.string.eliminado), Toast.LENGTH_LONG).show();
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
                        mBinding.etNombreControlPaises.setError(getText(id));
                        break;
                    case R.string.fallo_database:
                        Toast.makeText(ControlPaises.this,getText(R.string.fallo_database), Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(this, MainPaises.class);;
        startActivity(intent);
        this.finish();
    }
}