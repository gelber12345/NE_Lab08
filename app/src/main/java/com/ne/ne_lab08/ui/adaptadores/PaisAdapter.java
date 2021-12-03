package com.ne.ne_lab08.ui.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ne.ne_lab08.R;
import com.ne.ne_lab08.databinding.ListaItemPaisBinding;

import com.ne.ne_lab08.model.entidades.Pais;
import com.ne.ne_lab08.viewmodel.paises.MainPaisesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaisAdapter extends RecyclerView.Adapter<PaisAdapter.PaisViewHolder> {
    ArrayList<Pais> listPaises;
    ArrayList<Pais> listaOriginal;
    private final MainPaisesViewModel mViewModel;

    public PaisAdapter(MainPaisesViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void setPaisesList(ArrayList<Pais> listPaises) {
        this.listPaises = listPaises;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listPaises);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PaisAdapter.PaisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListaItemPaisBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.lista_item_pais, parent, false);
        return new PaisAdapter.PaisViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaisAdapter.PaisViewHolder holder, int position) {
        holder.binding.setViewModel(mViewModel);
        holder.binding.setPais(listPaises.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return listPaises == null ? 0 : listPaises.size();
    }
    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listPaises.clear();
            listPaises.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Pais> collecion = listaOriginal.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())
                                || i.getEstado().toLowerCase().contains(txtBuscar.toLowerCase())
                                || i.getIdString().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listPaises.clear();
                listPaises.addAll(collecion);
            } else {
                for (Pais c : listaOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())
                            || c.getEstado().toLowerCase().contains(txtBuscar.toLowerCase())
                            || c.getIdString().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listPaises.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    public class PaisViewHolder extends RecyclerView.ViewHolder {

        final ListaItemPaisBinding binding;

        public PaisViewHolder(ListaItemPaisBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
