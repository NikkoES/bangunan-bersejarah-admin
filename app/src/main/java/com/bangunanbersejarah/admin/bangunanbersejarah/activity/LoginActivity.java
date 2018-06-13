package com.bangunanbersejarah.admin.bangunanbersejarah.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.bangunanbersejarah.admin.bangunanbersejarah.R;
import com.bangunanbersejarah.admin.bangunanbersejarah.api.BaseApiService;
import com.bangunanbersejarah.admin.bangunanbersejarah.api.UtilsApi;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.PostResponse;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.UserResponse;
import com.bangunanbersejarah.admin.bangunanbersejarah.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;

    private SharedPreferencesUtils session;

    JSONObject userProfile;

    private ProgressDialog loadingDaftar;

    String email, password;

    BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        apiService = UtilsApi.getAPIService();

        session = new SharedPreferencesUtils(this, "UserData");

        if(session.checkIfDataExists("userProfile")){
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        loadingDaftar = new ProgressDialog(this);
        loadingDaftar.setTitle("Loading");
        loadingDaftar.setMessage("Checking Data");
        loadingDaftar.setCancelable(false);
    }

    @OnClick(R.id.btn_login)
    public void login(){
        loadingDaftar.show();
        if(TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())){
            Toast.makeText(this, "Data belum lengkap !", Toast.LENGTH_SHORT).show();
            loadingDaftar.dismiss();
        }
        else{
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            checkLogin(email, password);
        }
    }

    private void checkLogin(String email, String password){
        final String sEmail = email;
        apiService.login(sEmail, password, "admin")
                .enqueue(new Callback<PostResponse>() {
                    @Override
                    public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                        if (response.body().getData().equalsIgnoreCase("1")){
                            loadingDaftar.dismiss();
                            getUserData(sEmail);
                            Toast.makeText(getApplicationContext(), "Berhasil masuk !", Toast.LENGTH_SHORT).show();
                        } else {
                            loadingDaftar.dismiss();
                            Toast.makeText(getApplicationContext(), "Gagal Masuk (Email dan Password tidak match) !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostResponse> call, Throwable t) {
                        loadingDaftar.dismiss();
                        Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getUserData(String email){
        apiService.getUserData(email).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.body().getStatus().equals("success")){
                    loadingDaftar.dismiss();
                    UserResponse.Data data = response.body().getData();

                    userProfile = new JSONObject();
                    try {
                        userProfile.put("id_user", data.getIdUser());
                        userProfile.put("name", data.getNamaUser());
                        userProfile.put("email", data.getEmail());
                        userProfile.put("phonenumber", data.getNoHp());
                        userProfile.put("profile_photo", ""+data.getImgProfile());

                        session.storeData("userProfile", userProfile.toString());

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    loadingDaftar.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
