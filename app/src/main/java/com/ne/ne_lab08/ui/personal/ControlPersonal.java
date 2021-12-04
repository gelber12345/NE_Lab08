package com.ne.ne_lab08.ui.personal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ne.ne_lab08.Event;
import com.ne.ne_lab08.R;
import com.ne.ne_lab08.databinding.ActivityControlPersonalBinding;
import com.ne.ne_lab08.viewmodel.personal.ControlPersonalViewModel;
import com.ne.ne_lab08.viewmodel.personal.MainPersonalViewModel;

import java.util.Calendar;

public class ControlPersonal extends AppCompatActivity {
    private ActivityControlPersonalBinding mBinding;
    private ControlPersonalViewModel mViewModel;
    Spinner spPaises,spCargos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_control_personal);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_control_personal);
        mViewModel = new ViewModelProvider(this).get(ControlPersonalViewModel.class);
        mViewModel.setContext(this);
        spPaises = findViewById(R.id.spPaises);
        spCargos = findViewById(R.id.spCargos);

        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
        setupComponents();
        setupNavigation();
    }
    private void setupComponents(){
        mViewModel.inicializarSpinner(spCargos,spPaises);
        ArrayAdapter<String> adaptarCargos = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,mViewModel.getCargos());
        ArrayAdapter<String> adaptarPaises = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,mViewModel.getPaises());

        spCargos.setAdapter(adaptarCargos);
        spPaises.setAdapter(adaptarPaises);

        if (isEdition() ){
            TextView textView = findViewById(R.id.txtTituloControlPersonal);
            textView.setText("Añadir Personal");

            findViewById(R.id.txtEstadoControlPersonal).setVisibility(View.INVISIBLE);
            findViewById(R.id.txtValorEstadoControlPersonal).setVisibility(View.INVISIBLE);

            findViewById(R.id.btnEditarControlPersonal).setVisibility(View.INVISIBLE);
            findViewById(R.id.btnEliminarControlPersonal).setVisibility(View.INVISIBLE);
            findViewById(R.id.lin2ControlPersonal).setVisibility(View.INVISIBLE);

        }else{
            findViewById(R.id.btnAddControlPersonal).setVisibility(View.INVISIBLE);
            mViewModel.verPersonal(getIntent().getIntExtra("ID",-1));
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
                        Toast.makeText(ControlPersonal.this,getText(R.string.registrado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.editado:
                        Toast.makeText(ControlPersonal.this,getText(R.string.editado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.activado:
                        Toast.makeText(ControlPersonal.this,getText(R.string.activado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.inactivado:
                        Toast.makeText(ControlPersonal.this,getText(R.string.inactivado), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.cancelar:
                        Toast.makeText(ControlPersonal.this,getText(R.string.cancelar), Toast.LENGTH_LONG).show();
                        Regresar();
                        break;
                    case R.string.fechaEdit:
                        editarFecha();
                        break;
                    case R.string.eliminado:
                        Toast.makeText(ControlPersonal.this,getText(R.string.eliminado), Toast.LENGTH_LONG).show();
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
                        mBinding.etNombreControlPersonal.setError(getText(id));
                        break;
                    case R.string.dni_invalido:
                        mBinding.etDniControlPersonal.setError(getText(id));
                        break;
                    case R.string.fecha_invalida:
                        Toast.makeText(ControlPersonal.this,getText(R.string.fecha_invalida), Toast.LENGTH_LONG).show();
                        break;
                    case R.string.fallo_database:
                        Toast.makeText(ControlPersonal.this,getText(R.string.fallo_database), Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(this, MainPersonal.class);;
        startActivity(intent);
        this.finish();
    }
    public void editarFecha(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                mViewModel,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)


        );
        datePickerDialog.show();
    }
}