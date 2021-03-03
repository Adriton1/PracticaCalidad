package com.example.pracprocesos;

import java.util.ArrayList;

public class Ciudad {

    private String nombre;
    private String comunidad;
    private float superficie; //en km2
    private int poblacion;
    private int sanos;
    private int infectados;
    private int muertos;
    private int totalsanitario;
    private int hospitalizados;
    private ArrayList<String> tierra;
    private ArrayList<String> aire;
    private ArrayList<String> mar;


    public Ciudad(String nombre, String comunidad, float superficie, int poblacion, int sanos, int infectados, int muertos, int totalsanitario, int hospitalizados, ArrayList<String> tierra, ArrayList<String> aire, ArrayList<String> mar) {
        this.nombre = nombre;
        this.comunidad = comunidad;
        this.superficie = superficie;
        this.poblacion = poblacion;
        this.sanos = sanos;
        this.infectados = infectados;
        this.muertos = muertos;
        this.totalsanitario = totalsanitario;
        this.hospitalizados = hospitalizados;
        this.tierra = tierra;
        this.aire = aire;
        this.mar = mar;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public void sumPoblacion(int poblacion) {
        this.poblacion += poblacion;
    }

    public float getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public int getSanos() {
        return sanos;
    }

    public void sumSanos(int sanos) {
        if (this.sanos + sanos < 0) {
            this.sanos = 0;
        }
        else if (this.sanos + sanos > this.poblacion - this.muertos) {
            this.sanos = this.poblacion - this.muertos;
        }
        else {
            this.sanos += sanos;
        }
    }

    public int getInfectados() {
        return infectados;
    }

    public void sumInfectados(int infectados) {
        if (this.infectados + infectados < 0) {
            this.infectados = 0;
        }
        else if (this.infectados + infectados > this.poblacion - this.muertos){
            this.infectados = this.poblacion - this.muertos;
        }
        else {
            this.infectados += infectados;
        }
    }

    public int getMuertos() {
        return muertos;
    }

    public void sumMuertos(int muertos) {
        if (this.muertos + muertos > this.poblacion) {
            this.muertos = this.poblacion;
        }
        else if (this.muertos + muertos < 0) {
            this.muertos = 0;
        }
        else {
            this.muertos += muertos;
        }
    }

    public int getTotalsanitario() {
        return totalsanitario;
    }

    public void setTotalsanitario(int totalsanitario) {
        this.totalsanitario = totalsanitario;
    }

    public int getHospitalizados() {
        return hospitalizados;
    }

    public void setHospitalizados(int hospitalizados) {
        this.hospitalizados = hospitalizados;
    }

    public ArrayList<String> getTierra() {
        return tierra;
    }

    public void setTierra(ArrayList<String> tierra) {
        this.tierra = tierra;
    }

    public ArrayList<String> getAire() { return aire; }

    public void setAire(ArrayList<String> aire) { this.aire = aire; }

    public ArrayList<String> getMar() {
        return mar;
    }

    public void setMar(ArrayList<String> mar) {
        this.mar = mar;
    }

    public void setInfectados(int infectados) {
        this.infectados = infectados;
    }

    public void setMuertos(int muertos) {
        this.muertos = muertos;
    }
}
