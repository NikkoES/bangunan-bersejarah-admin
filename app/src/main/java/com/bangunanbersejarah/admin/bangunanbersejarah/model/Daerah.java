package com.bangunanbersejarah.admin.bangunanbersejarah.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikko Eka Saputra on 4/3/2018.
 */

public class Daerah {

    @SerializedName("id_daerah")
    String idDaerah;
    @SerializedName("nama_daerah")
    String namaDaerah;
    @SerializedName("id_provinsi")
    String idProvinsi;

    public Daerah(String idDaerah, String namaDaerah, String idProvinsi) {
        this.idDaerah = idDaerah;
        this.namaDaerah = namaDaerah;
        this.idProvinsi = idProvinsi;
    }

    public String getIdDaerah() {
        return idDaerah;
    }

    public String getNamaDaerah() {
        return namaDaerah;
    }

    public String getIdProvinsi() {
        return idProvinsi;
    }
}
