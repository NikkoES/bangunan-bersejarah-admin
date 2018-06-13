package com.bangunanbersejarah.admin.bangunanbersejarah.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bangunanbersejarah.admin.bangunanbersejarah.R;
import com.bangunanbersejarah.admin.bangunanbersejarah.activity.DetailBangunanActivity;
import com.bangunanbersejarah.admin.bangunanbersejarah.api.BaseApiService;
import com.bangunanbersejarah.admin.bangunanbersejarah.api.UtilsApi;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.Bangunan;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.Pengajuan;
import com.bumptech.glide.Glide;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nikko Eka Saputra on 4/2/2018.
 */

public class PengajuanAdapter extends RecyclerView.Adapter<PengajuanAdapter.ViewHolder> {

    private Context context;
    private List<Pengajuan> listPengajuan;

    BaseApiService apiService;

    public PengajuanAdapter(Context context, List<Pengajuan> listPengajuan){
        this.context = context;
        this.listPengajuan = listPengajuan;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pengajuan, null, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        apiService = UtilsApi.getAPIService();

        final Pengajuan bangunan = listPengajuan.get(position);

        holder.namaBangunan.setText(bangunan.getNamaBangunan());
        holder.tanggalPengajuan.setText(bangunan.getTanggalPengajuan());
        holder.txtPengaju.setText(bangunan.getNamaUser());
        holder.btnPengajuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailBangunanActivity.class);
                i.putExtra("id_pengajuan", bangunan.getIdPengajuan());
                i.putExtra("nama_bangunan", bangunan.getNamaBangunan());
                i.putExtra("sejarah_bangunan", bangunan.getSejarahBangunan());
                i.putExtra("image_bangunan", bangunan.getImageBangunan());
                i.putExtra("alamat_bangunan", bangunan.getAlamatBangunan());
                i.putExtra("id_provinsi", bangunan.getIdProvinsi());
                i.putExtra("id_daerah", bangunan.getIdDaerah());
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        holder.btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Anda yakin ingin menerima ajuan ini ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ubahStatus(bangunan.getIdPengajuan(), position, "1");
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
        });
        holder.btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Anda yakin ingin menolak ajuan ini ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ubahStatus(bangunan.getIdPengajuan(), position, "2");
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
        });
    }

    private void ubahStatus(String idPengajuan, final int position, final String status){
        apiService.ubahStatus(idPengajuan, status).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    if(status.equalsIgnoreCase("1")){
                        Toast.makeText(context, "Pengajuan berhasil diterima", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context, "Pengajuan berhasil ditolak", Toast.LENGTH_SHORT).show();
                    }
                    listPengajuan.remove(position);
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(context, "Ada kesalahan !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPengajuan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView btnPengajuan;
        private TextView namaBangunan, tanggalPengajuan, txtPengaju;
        private ImageView btnTerima, btnTolak;


        public ViewHolder(View itemView) {
            super(itemView);

            btnPengajuan = (CardView) itemView.findViewById(R.id.cv_pengajuan);
            namaBangunan = (TextView) itemView.findViewById(R.id.txt_nama_bangunan);
            txtPengaju = (TextView) itemView.findViewById(R.id.txt_pengaju);
            tanggalPengajuan = (TextView) itemView.findViewById(R.id.txt_tanggal_pengajuan);
            btnTerima = (ImageView) itemView.findViewById(R.id.btn_terima);
            btnTolak = (ImageView) itemView.findViewById(R.id.btn_tolak);
        }
    }
}