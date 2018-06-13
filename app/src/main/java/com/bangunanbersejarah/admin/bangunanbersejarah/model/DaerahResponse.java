package com.bangunanbersejarah.admin.bangunanbersejarah.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nikko Eka Saputra on 4/3/2018.
 */

public class DaerahResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<Daerah> listDaerah;

    public String getStatus() {
        return status;
    }

    public List<Daerah> getListDaerah() {
        return listDaerah;
    }
}