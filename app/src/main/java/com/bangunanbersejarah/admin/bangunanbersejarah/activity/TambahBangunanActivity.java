package com.bangunanbersejarah.admin.bangunanbersejarah.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bangunanbersejarah.admin.bangunanbersejarah.R;
import com.bangunanbersejarah.admin.bangunanbersejarah.api.BaseApiService;
import com.bangunanbersejarah.admin.bangunanbersejarah.api.UtilsApi;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.Daerah;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.DaerahResponse;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.PostResponse;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.Provinsi;
import com.bangunanbersejarah.admin.bangunanbersejarah.utils.SharedPreferencesUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBangunanActivity extends AppCompatActivity {

    @BindView(R.id.et_nama_bangunan)
    EditText etNamaBangunan;
    @BindView(R.id.et_sejarah_bangunan)
    EditText etSejarahBangunan;
    @BindView(R.id.et_alamat_bangunan)
    EditText etAlamatBangunan;
    @BindView(R.id.spin_daerah)
    Spinner spinDaerah;
    @BindView(R.id.spin_provinsi)
    Spinner spinProvinsi;
    @BindView(R.id.image_selected)
    ImageView imageSelected;

    @BindView(R.id.btn_tambah_bangunan)
    Button btnTambahBangunan;

    List<String> listDaerah = new ArrayList<>();
    List<Provinsi> listProvinsi = new ArrayList<>();
    List<Daerah> listResponseDaerah = new ArrayList<>();

    ProgressDialog loading;
    String mediaPath;
    String[] mediaColumns = { MediaStore.Video.Media._ID };

    String idBangunan, namaBangunan, sejarahBangunan, alamatBangunan, idProvinsi, idDaerah, idUser;

    BaseApiService apiService;

    private SharedPreferencesUtils userDataSharedPreferences;

    JSONObject userProfile;

    String userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_bangunan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tambah Bangunan Baru");

        loading = new ProgressDialog(this);
        loading.setMessage("Processing...");

        ButterKnife.bind(this);

        userDataSharedPreferences = new SharedPreferencesUtils(this, "UserData");

        try {
            userData = userDataSharedPreferences.getPreferenceData("userProfile");
            userProfile = new JSONObject(userData);
            idUser = userProfile.get("id_user").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if(getIntent().hasExtra("id_bangunan")){
            idBangunan = getIntent().getStringExtra("id_bangunan");
            namaBangunan = getIntent().getStringExtra("nama_bangunan");
            sejarahBangunan = getIntent().getStringExtra("sejarah_bangunan");
            alamatBangunan = getIntent().getStringExtra("alamat_bangunan");
            idProvinsi = getIntent().getStringExtra("id_provinsi");
            idDaerah = getIntent().getStringExtra("id_daerah");
            idUser = getIntent().getStringExtra("id_user");

            etNamaBangunan.setText(namaBangunan);
            etSejarahBangunan.setText(sejarahBangunan);
            etAlamatBangunan.setText(alamatBangunan);

            btnTambahBangunan.setText("Ubah Bangunan");
        }
        else{
            btnTambahBangunan.setText("Tambah Bangunan");
        }

        apiService = UtilsApi.getAPIService();

        pilihProvinsi();
    }

    @OnClick(R.id.select_image)
    public void selectImage(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }

    @OnClick(R.id.btn_tambah_bangunan)
    public void btnTambahBangunan(){
        loading.show();
        if(TextUtils.isEmpty(etNamaBangunan.getText().toString()) || TextUtils.isEmpty(etSejarahBangunan.getText().toString()) || TextUtils.isEmpty(etAlamatBangunan.getText().toString())){
            Toast.makeText(this, "Data belum lengkap !", Toast.LENGTH_SHORT).show();
        }
        else{
            namaBangunan = etNamaBangunan.getText().toString();
            sejarahBangunan = etSejarahBangunan.getText().toString();
            alamatBangunan = etAlamatBangunan.getText().toString();
            if(!getIntent().hasExtra("id_bangunan")){
                idBangunan = String.valueOf((int) ((Math.random() * (999 - 99) + 1) + 99));
                tambahDataBangunan(idBangunan, namaBangunan, sejarahBangunan, alamatBangunan, idProvinsi, idDaerah, idUser);
            }
            else{
                editBangunan(idBangunan, namaBangunan, sejarahBangunan, alamatBangunan, idProvinsi, idDaerah);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                imageSelected.setVisibility(View.VISIBLE);
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                imageSelected.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                Toast.makeText(this, "Berhasil !"+mediaPath, Toast.LENGTH_SHORT).show();
                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void tambahDataBangunan(final String idBangunan, String namaBangunan, String sejarahBangunan, String alamatBangunan,  String idProvinsi, String idDaerah, String idUser){
        apiService.tambahBangunan(idBangunan, namaBangunan, sejarahBangunan, alamatBangunan, "", idProvinsi, idDaerah, "1", idUser)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
//                            uploadFile(idBangunan);
                            loading.dismiss();
                            finish();
                            Toast.makeText(TambahBangunanActivity.this, "Berhasil menambahkan bangunan", Toast.LENGTH_SHORT).show();
                        } else {
                            loading.dismiss();
                            Toast.makeText(TambahBangunanActivity.this, "Gagal menambahkan bangunan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void editBangunan(final String idBangunan, String namaBangunan, String sejarahBangunan, String alamatBangunan,  String idProvinsi, String idDaerah){
        apiService.editBangunan(idBangunan, namaBangunan, sejarahBangunan, alamatBangunan, "", idProvinsi, idDaerah, "1")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
//                            uploadFile(idBangunan);
                            loading.dismiss();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            Toast.makeText(TambahBangunanActivity.this, "Berhasil mengubah bangunan", Toast.LENGTH_SHORT).show();
                        } else {
                            loading.dismiss();
                            Toast.makeText(TambahBangunanActivity.this, "Gagal mengubah bangunan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadFile(String idBangunan) {
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image_bangunan", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        apiService.uploadFile(fileToUpload, filename, idBangunan).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                PostResponse serverResponse = response.body();
                if (serverResponse != null) {
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), "Berhasil menambahkan data !",Toast.LENGTH_SHORT).show();
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
    }

    public void pilihProvinsi(){
        listProvinsi.add(new Provinsi("0", "--Pilih Provinsi--"));
        listProvinsi.add(new Provinsi("1", "Banten"));
        listProvinsi.add(new Provinsi("2", "DKI Jakarta"));
        listProvinsi.add(new Provinsi("3", "Jawa Barat"));
        listProvinsi.add(new Provinsi("4", "Jawa Tengah"));
        listProvinsi.add(new Provinsi("5", "Jawa Timur"));
        listProvinsi.add(new Provinsi("6", "DIY Yogyakarta"));

        ArrayAdapter<Provinsi> dataAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listProvinsi);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinProvinsi.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();

        spinProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Provinsi key = (Provinsi) spinProvinsi.getSelectedItem();
                if(!(spinProvinsi.getSelectedItem() == null)){
                    listDaerah.clear();
                    idProvinsi = key.getIdProvinsi();
                    searchDaerah(idProvinsi);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void searchDaerah(String idProvinsi){
        apiService.getListDaerah(idProvinsi).enqueue(new Callback<DaerahResponse>() {
            @Override
            public void onResponse(Call<DaerahResponse> call, Response<DaerahResponse> response) {
                if (response.isSuccessful()){
                    listResponseDaerah = response.body().getListDaerah();

                    listDaerah.add("--Pilih Daerah--");
                    for(int i=0;i<listResponseDaerah.size();i++){
                        listDaerah.add(listResponseDaerah.get(i).getNamaDaerah());
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, listDaerah);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinDaerah.setAdapter(dataAdapter);
                    dataAdapter.notifyDataSetChanged();

                    spinDaerah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i>0){
                                idDaerah = listResponseDaerah.get(i).getIdDaerah();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
                else {
                    Toast.makeText(getApplicationContext(), "Failed to Fetch Data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DaerahResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(TambahBangunanActivity.this)
                .setTitle("Data tidak akan disimpan ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                new AlertDialog.Builder(TambahBangunanActivity.this)
                        .setTitle("Data tidak akan disimpan ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setCancelable(false)
                        .show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
