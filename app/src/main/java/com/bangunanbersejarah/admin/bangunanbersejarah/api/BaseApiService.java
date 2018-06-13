package com.bangunanbersejarah.admin.bangunanbersejarah.api;

import com.bangunanbersejarah.admin.bangunanbersejarah.model.BangunanResponse;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.DaerahResponse;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.Pengajuan;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.PengajuanResponse;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.PostResponse;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Nikko Eka Saputra on 4/2/2018.
 */

public interface BaseApiService {

    @FormUrlEncoded
    @POST("login/")
    Call<PostResponse> login(@Field("email") String email, @Field("password") String password, @Field("role") String role);

    @Multipart
    @POST("bangunan/image/{id_bangunan}")
    Call<PostResponse> uploadFile(@Part MultipartBody.Part file, @Part("image_bangunan") RequestBody name, @Path("id_bangunan") String idBangunan);

    @FormUrlEncoded
    @POST("bangunan/")
    Call<ResponseBody> tambahBangunan(@Field("id_bangunan") String idBangunan, @Field("nama_bangunan") String namaBangunan,
                                      @Field("sejarah_bangunan") String sejarahBangunan, @Field("alamat_bangunan") String alamatBangunan, @Field("image_bangunan") String imageBangunan,
                                      @Field("id_provinsi") String idProvinsi, @Field("id_daerah") String idDaerah, @Field("status") String status, @Field("id_user") String idUser);

    @FormUrlEncoded
    @POST("bangunan/{id}")
    Call<ResponseBody> editBangunan(@Path("id") String idBangunan, @Field("nama_bangunan") String namaBangunan, @Field("sejarah_bangunan") String sejarahBangunan,
                                    @Field("alamat_bangunan") String alamatBangunan, @Field("image_bangunan") String imageBangunan,
                                      @Field("id_provinsi") String idProvinsi, @Field("id_daerah") String idDaerah, @Field("status") String status);

    @GET("profile/{email}")
    Call<UserResponse> getUserData(@Path("email") String email);

    //list semua bangunan berdasarkan provinsi
    @GET("bangunan/{id_provinsi}/{status}")
    Call<BangunanResponse> getAllListBangunan(@Path("id_provinsi") String idProvinsi, @Path("status") String status);

    //list semua daerah berdasarkan provinsi
    @GET("daerah/{id_provinsi}")
    Call<DaerahResponse> getListDaerah(@Path("id_provinsi") String idProvinsi);

    //list bangunan berdasarkan daerah dan provinsi
    @GET("bangunan/{id_provinsi}/{id_daerah}/{status}")
    Call<BangunanResponse> getListBangunan(@Path("id_provinsi") String idProvinsi, @Path("id_daerah") String idDaerah, @Path("status") String status);

    //list semua bangunan berdasarkan provinsi
    @GET("pengajuan/all/{status}")
    Call<PengajuanResponse> getPengajuanByStatus(@Path("status") String status);

    @FormUrlEncoded
    @POST("bangunan/hapus/")
    Call<ResponseBody> deletePengajuan(@Field("id_bangunan") String idBangunan);

    @FormUrlEncoded
    @POST("ubahstatus/")
    Call<ResponseBody> ubahStatus(@Field("id_bangunan") String idBangunan, @Field("status") String status);
}