package com.example.demo.Objekty;

public class Etykieta {



    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRocznik() {
        return rocznik;
    }

    public void setRocznik(String rocznik) {
        this.rocznik = rocznik;
    }

    public String getMiejsceMagazynowe() {
        return miejsceMagazynowe;
    }

    public void setMiejsceMagazynowe(String miejsceMagazynowe) {
        this.miejsceMagazynowe = miejsceMagazynowe;
    }

    private String nazwa;
    private String marka;
    private String model;
    private String rocznik;
    private String miejsceMagazynowe;
    private String nr_vin;
    private String id_czesci;

    public String getId_czesci() {
        return id_czesci;
    }

    public void setId_czesci(String id_czesci) {
        this.id_czesci = id_czesci;
    }

    public String getNr_vin() {
        return nr_vin;
    }

    public void setNr_vin(String nr_vin) {
        this.nr_vin = nr_vin;
    }



    public Etykieta(String nazwa, String marka, String model, String rocznik, String miejsceMagazynowe, String nr_vin, String id_czesci) {
        this.nazwa = nazwa;
        this.marka = marka;
        this.model = model;
        this.rocznik = rocznik;
        this.miejsceMagazynowe = miejsceMagazynowe;
        this.nr_vin = nr_vin;
        this.id_czesci = id_czesci;
    }
}
