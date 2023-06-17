package com.example.demo.Objekty;

public class Produkty {
    private String nazwa;
    private String marka;
    private String model;
    private String cena;

    public Produkty(String nazwa, String marka, String model, String cena) {
        this.nazwa = nazwa;
        this.marka = marka;
        this.model = model;
        this.cena = cena;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String czesc) {
        this.nazwa = czesc;
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

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }
}
