package com.example.demo.Objekty;

public class Samochod {


    private String nr_vin;
    private String marka;
    private String model;
    private String rok;
    private String przebieg;
    private String pojemnosc;
    private String kSilnika;
    private String kSkrzyni;
    private String kLakieru;


    public Samochod(String nr_vin, String marka, String model, String rok, String przebieg, String pojemnosc, String kSilnika, String kSkrzyni, String kLakieru) {
        this.nr_vin = nr_vin;
        this.marka = marka;
        this.model = model;
        this.rok = rok;
        this.przebieg = przebieg;
        this.pojemnosc = pojemnosc;
        this.kSilnika = kSilnika;
        this.kSkrzyni = kSkrzyni;
        this.kLakieru = kLakieru;
    }


    public String getNr_vin() {
        return nr_vin;
    }

    public void setNr_vin(String nr_vin) {
        this.nr_vin = nr_vin;
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
        return rok;
    }

    public void setRocznik(String rok) {
        this.rok = rok;
    }

    public String getPrzebieg() {
        return przebieg;
    }

    public void setPrzebieg(String przebieg) {
        this.przebieg = przebieg;
    }

    public String getPojemnosc() {
        return pojemnosc;
    }

    public void setPojemnosc(String pojemnosc) {
        this.pojemnosc = pojemnosc;
    }

    public String getKod_silnika() {
        return kSilnika;
    }

    public void setKod_silnika(String kSilnika) {
        this.kSilnika = kSilnika;
    }

    public String getKod_skrzyni() {
        return kSkrzyni;
    }

    public void setKod_skrzyni(String kSkrzyni) {
        this.kSkrzyni = kSkrzyni;
    }

    public String getKod_lakieru() {
        return kLakieru;
    }

    public void setKod_lakieru(String kLakieru) {
        this.kLakieru = kLakieru;
    }



}
