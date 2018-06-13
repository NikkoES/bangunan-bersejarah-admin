package com.bangunanbersejarah.admin.bangunanbersejarah.model;

public class Provinsi {

    String idProvinsi, namaProvnsi;

    public Provinsi(String idProvinsi, String namaProvnsi) {
        this.idProvinsi = idProvinsi;
        this.namaProvnsi = namaProvnsi;
    }

    public String getIdProvinsi() {
        return idProvinsi;
    }

    public String getNamaProvnsi() {
        return namaProvnsi;
    }

    public String toString()
    {
        return namaProvnsi;
    }
}
