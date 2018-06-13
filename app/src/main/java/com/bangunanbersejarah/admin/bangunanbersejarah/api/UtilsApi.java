package com.bangunanbersejarah.admin.bangunanbersejarah.api;

/**
 * Created by Nikko Eka Saputra on 4/2/2018.
 */

public class UtilsApi {

    //http://bangunan-bersejarah.000webhostapp.com/public/

    // 10.0.2.2 ini adalah localhost.
    public static final String BASE_URL_API = "http://bangunan-bersejarah.000webhostapp.com/public/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
