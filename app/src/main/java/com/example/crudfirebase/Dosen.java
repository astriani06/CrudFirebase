package com.example.crudfirebase;

import java.io.Serializable;

public class Dosen implements Serializable {
    private String nik;
    private String nama;
    private String ja;
    private String key;

    public Dosen() {
    }

    public Dosen(String nk, String nm, String j) {
        this.nik = nk;
        this.nama = nm;
        this.ja = j;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJa() {
        return ja;
    }

    public void setJa(String ja) {
        this.ja = ja;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return nik + " " + nama + " " + ja;
    }
}
