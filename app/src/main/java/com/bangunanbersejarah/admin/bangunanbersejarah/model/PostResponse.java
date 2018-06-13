package com.bangunanbersejarah.admin.bangunanbersejarah.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostResponse {

    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("data")
    @Expose
    String data;

    public PostResponse(String status, String data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

}
