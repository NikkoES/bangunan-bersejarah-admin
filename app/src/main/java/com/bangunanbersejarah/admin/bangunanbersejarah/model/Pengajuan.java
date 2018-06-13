package com.bangunanbersejarah.admin.bangunanbersejarah.model;

import com.google.gson.annotations.SerializedName;

public class Pengajuan {

    @SerializedName("id_bangunan")
    String idPengajuan;
    @SerializedName("nama_bangunan")
    String namaBangunan;
    @SerializedName("sejarah_bangunan")
    String sejarahBangunan;
    @SerializedName("alamat_bangunan")
    String alamatBangunan;
    @SerializedName("image_bangunan")
    String imageBangunan;
    @SerializedName("id_provinsi")
    String idProvinsi;
    @SerializedName("id_daerah")
    String idDaerah;
    @SerializedName("tanggal_pengajuan")
    String tanggalPengajuan;
    @SerializedName("status")
    String status;
    @SerializedName("id_user")
    String idUser;
    @SerializedName("nama_user")
    String namaUser;

    public Pengajuan(String idPengajuan, String namaBangunan, String sejarahBangunan, String alamatBangunan, String imageBangunan, String idProvinsi, String idDaerah, String tanggalPengajuan, String status, String idUser, String namaUser) {
        this.idPengajuan = idPengajuan;
        this.namaBangunan = namaBangunan;
        this.sejarahBangunan = sejarahBangunan;
        this.alamatBangunan = alamatBangunan;
        this.imageBangunan = imageBangunan;
        this.idProvinsi = idProvinsi;
        this.idDaerah = idDaerah;
        this.tanggalPengajuan = tanggalPengajuan;
        this.status = status;
        this.idUser = idUser;
        this.namaUser = namaUser;
    }

    public String getIdPengajuan() {
        return idPengajuan;
    }

    public String getNamaBangunan() {
        return namaBangunan;
    }

    public String getSejarahBangunan() {
        return sejarahBangunan;
    }

    public String getAlamatBangunan() {
        return alamatBangunan;
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

    public String getTanggalPengajuan() {
        return tanggalPengajuan;
    }

    public String getStatus() {
        return status;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }
}
