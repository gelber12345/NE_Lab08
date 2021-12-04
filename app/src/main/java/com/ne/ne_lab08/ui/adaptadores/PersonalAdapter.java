package com.ne.ne_lab08.ui.adaptadores;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.ne.ne_lab08.databinding.ListaItemBinding;
import com.ne.ne_lab08.model.DbCargos;
import com.ne.ne_lab08.model.DbPaises;
import com.ne.ne_lab08.model.entidades.Personal;
import com.ne.ne_lab08.viewmodel.personal.MainPersonalViewModel;
import com.ne.ne_lab08.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder>{
    ArrayList<Personal> listPersonal;
    ArrayList<Personal> listaOriginal;
    private final MainPersonalViewModel mViewModel;
    public PersonalAdapter(MainPersonalViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void setPersonalList(ArrayList<Personal> listPersonal) {
        this.listPersonal = listPersonal;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listPersonal);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PersonalAdapter.PersonalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListaItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.lista_item, parent, false);
        return new PersonalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalAdapter.PersonalViewHolder holder, int position) {
        holder.binding.setViewModel(mViewModel);
        holder.binding.setPersonal(listPersonal.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return listPersonal == null ? 0 : listPersonal.size();
    }

    public void filtrado(final String txtBuscar) {
        final DbCargos dbCargo = new DbCargos(mViewModel.getContext());
        final DbPaises dbPais= new DbPaises(mViewModel.getContext());
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listPersonal.clear();
            listPersonal.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Personal> collecion = listaOriginal.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())
                                || i.getDni().toLowerCase().contains(txtBuscar.toLowerCase())
                                || i.getFecha().toLowerCase().contains(txtBuscar.toLowerCase())
                                || i.getEstado().toLowerCase().contains(txtBuscar.toLowerCase())
                                || i.getIdString().toLowerCase().contains(txtBuscar.toLowerCase())
                                || dbCargo.verCargo(i.getId_cargo()).getNombre().toLowerCase().contains(txtBuscar.toLowerCase())
                                || dbPais.verPais(i.getId_pais()).getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listPersonal.clear();
                listPersonal.addAll(collecion);
            } else {
                for (Personal c : listaOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())
                            || c.getDni().toLowerCase().contains(txtBuscar.toLowerCase())
                            || c.getFecha().toLowerCase().contains(txtBuscar.toLowerCase())
                            || c.getEstado().toLowerCase().contains(txtBuscar.toLowerCase())
                            || c.getIdString().toLowerCase().contains(txtBuscar.toLowerCase())
                            || dbCargo.verCargo(c.getId_cargo()).getNombre().toLowerCase().contains(txtBuscar.toLowerCase())
                            || dbPais.verPais(c.getId_pais()).getNombre().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listPersonal.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class PersonalViewHolder extends RecyclerView.ViewHolder{
        final ListaItemBinding binding;

        public PersonalViewHolder(ListaItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
