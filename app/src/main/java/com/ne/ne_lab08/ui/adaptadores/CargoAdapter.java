package com.ne.ne_lab08.ui.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ne.ne_lab08.R;
import com.ne.ne_lab08.databinding.ListaItemAuxBinding;
import com.ne.ne_lab08.databinding.ListaItemBinding;
import com.ne.ne_lab08.model.entidades.Cargo;
import com.ne.ne_lab08.model.entidades.Personal;
import com.ne.ne_lab08.viewmodel.cargos.MainCargosViewModel;
import com.ne.ne_lab08.viewmodel.personal.MainPersonalViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CargoAdapter extends RecyclerView.Adapter<CargoAdapter.CargoViewHolder>{
    ArrayList<Cargo> listCargos;
    ArrayList<Cargo> listaOriginal;
    private final MainCargosViewModel mViewModel;

    public CargoAdapter(MainCargosViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void setCargosList(ArrayList<Cargo> listCargos) {
        this.listCargos = listCargos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listCargos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CargoAdapter.CargoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListaItemAuxBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.lista_item_aux, parent, false);
        return new CargoAdapter.CargoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CargoAdapter.CargoViewHolder holder, int position) {
        holder.binding.setViewModel(mViewModel);
        holder.binding.setCargo(listCargos.get(position));
        holder.binding.executePendingBindings();
    }
    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listCargos.clear();
            listCargos.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Cargo> collecion = listaOriginal.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())
                                || i.getEstado().toLowerCase().contains(txtBuscar.toLowerCase())
                                || i.getIdString().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listCargos.clear();
                listCargos.addAll(collecion);
            } else {
                for (Cargo c : listaOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())
                            || c.getEstado().toLowerCase().contains(txtBuscar.toLowerCase())
                            || c.getIdString().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listCargos.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listCargos == null ? 0 : listCargos.size();
    }

    public class CargoViewHolder extends RecyclerView.ViewHolder {

        final ListaItemAuxBinding binding;

        public CargoViewHolder(ListaItemAuxBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
