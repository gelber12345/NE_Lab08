package com.ne.ne_lab08.model.entidades;

public class Personal {
    private int id;
    private String nombre;
    private String dni;
    private int dia;
    private int mes;
    private int year;
    private String estado;

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
}
