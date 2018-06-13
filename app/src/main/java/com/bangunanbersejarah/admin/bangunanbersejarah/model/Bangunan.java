package com.bangunanbersejarah.admin.bangunanbersejarah.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikko Eka Saputra on 4/2/2018.
 */

public class Bangunan {

    @SerializedName("id_bangunan")
    String idBangunan;
    @SerializedName("nama_bangunan")
    String namaBangunan;
    @SerializedName("sejarah_bangunan")
    String sejarahBangunan;
    @SerializedName("image_bangunan")
    String imageBangunan;
    @SerializedName("alamat_bangunan")
    String alamatBangunan;
    @SerializedName("id_provinsi")
    String idProvinsi;
    @SerializedName("id_daerah")
    String idDaerah;

    public Bangunan(String idBangunan, String namaBangunan, String sejarahBangunan, String imageBangunan, String alamatBangunan, String idProvinsi, String idDaerah) {
        this.idBangunan = idBangunan;
        this.namaBangunan = namaBangunan;
        this.sejarahBangunan = sejarahBangunan;
        this.imageBangunan = imageBangunan;
        this.alamatBangunan = alamatBangunan;
        this.idProvinsi = idProvinsi;
        this.idDaerah = idDaerah;
    }

    public String getAlamatBangunan() {
        return alamatBangunan;
    }

    public String getIdBangunan() {
        return idBangunan;
    }

    public String getNamaBangunan() {
        return namaBangunan;
    }

    public String getSejarahBangunan() {
        return sejarahBangunan;
    }

    public String getImageBangunan() {
        return imageBangunan;
    }

    public String getIdProvinsi() {
        return idProvinsi;
    }

    public String getIdDaerah() {
        return idDaerah;
    }
}