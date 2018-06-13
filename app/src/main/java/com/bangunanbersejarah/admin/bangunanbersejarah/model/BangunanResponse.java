package com.bangunanbersejarah.admin.bangunanbersejarah.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nikko Eka Saputra on 4/3/2018.
 */

public class BangunanResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<Bangunan> listBangunan;

    public String getStatus() {
        return status;
    }

    public List<Bangunan> getListBangunan() {
        return listBangunan;
    }

}
