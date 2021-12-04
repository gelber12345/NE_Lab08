package com.ne.ne_lab08.model.entidades;

import com.ne.ne_lab08.model.DbCargos;

public class Personal {
    private int id;
    private String nombre;
    private String dni;
    private int dia;
    private int mes;
    private int year;
    private String estado;
    private int id_cargo;
    private int id_pais;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
    public void setDia(String dia) {
        this.dia = Integer.parseInt(dia);
    }
    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }
    public void setMes(String mes) {
        this.mes = Integer.parseInt(mes);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public void setYear(String year) {
        this.year = Integer.parseInt(year);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getFecha(){
        String res= ""+ dia +"/"+mes+"/"+year;
        return res;
    }

    public int getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }

    public int getId_pais() {
        return id_pais;
    }

    public void setId_pais(int id_pais) {
        this.id_pais = id_pais;
    }

    public String getDiaString(){
        return "" + dia;
    }
    public String getMesString(){
        return "" + mes;
    }
    public String getYearString(){
        return "" + year;
    }
    public String getIdString(){
        return "" + id;
    }
    public String getIdCargo(){
        return "" + id_cargo;
    }
    public String getIdPais(){
        return "" + id_pais;
    }


}
