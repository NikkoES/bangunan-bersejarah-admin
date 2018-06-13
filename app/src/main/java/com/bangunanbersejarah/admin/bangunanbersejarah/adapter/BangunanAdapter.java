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

import com.bangunanbersejarah.admin.bangunanbersejarah.activity.TambahBangunanActivity;
import com.bangunanbersejarah.admin.bangunanbersejarah.api.BaseApiService;
import com.bangunanbersejarah.admin.bangunanbersejarah.api.UtilsApi;
import com.bumptech.glide.Glide;
import com.bangunanbersejarah.admin.bangunanbersejarah.R;
import com.bangunanbersejarah.admin.bangunanbersejarah.activity.DetailBangunanActivity;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.Bangunan;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nikko Eka Saputra on 4/2/2018.
 */

public class BangunanAdapter extends RecyclerView.Adapter<BangunanAdapter.ViewHolder> {

    private Context context;
    private List<Bangunan> listBangunan;

    BaseApiService apiService;

    public BangunanAdapter(Context context, List<Bangunan> listBangunan){
        this.context = context;
        this.listBangunan = listBangunan;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_bangunan, null, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        apiService = UtilsApi.getAPIService();

        final Bangunan bangunan = listBangunan.get(position);
        Glide.with(context)
                .load(bangunan.getImageBangunan())
                .placeholder(R.drawable.no_image_icon)
                .into(holder.imageBangunan);
        holder.namaBangunan.setText(bangunan.getNamaBangunan());
        holder.btnBangunan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] dialogitem = {"Detail Bangunan", "Edit Bangunan", "Hapus Bangunan"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setItems(dialogitem, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int item){
                        switch(item){
                            case 0 : {
                                Intent i = new Intent(context, DetailBangunanActivity.class);
                                i.putExtra("id_bangunan", bangunan.getIdBangunan());
                                i.putExtra("nama_bangunan", bangunan.getNamaBangunan());
                                i.putExtra("sejarah_bangunan", bangunan.getSejarahBangunan());
                                i.putExtra("alamat_bangunan", bangunan.getAlamatBangunan());
                                i.putExtra("image_bangunan", bangunan.getImageBangunan());
                                i.putExtra("id_provinsi", bangunan.getIdProvinsi());
                                i.putExtra("id_daerah", bangunan.getIdDaerah());
                                context.startActivity(i);
                                ((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                break;
                            }
                            case 1 : {
                                Intent i = new Intent(context, TambahBangunanActivity.class);
                                i.putExtra("id_bangunan", bangunan.getIdBangunan());
                                i.putExtra("nama_bangunan", bangunan.getNamaBangunan());
                                i.putExtra("sejarah_bangunan", bangunan.getSejarahBangunan());
                                i.putExtra("alamat_bangunan", bangunan.getAlamatBangunan());
                                i.putExtra("image_bangunan", bangunan.getImageBangunan());
                                i.putExtra("id_provinsi", bangunan.getIdProvinsi());
                                i.putExtra("id_daerah", bangunan.getIdDaerah());
                                context.startActivity(i);
                                ((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                break;
                            }
                            case 2 : {
                                new AlertDialog.Builder(context)
                                        .setTitle("Anda yakin ingin menghapus bangunan ini ?")
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                hapusBangunan(bangunan.getIdBangunan(), position);
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
                    }
                });
                builder.create().show();
            }
        });
    }

    private void hapusBangunan(String idBangunan, final int position){
        apiService.deletePengajuan(idBangunan).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "Berhasil menghapus bangunan", Toast.LENGTH_SHORT).show();
                    listBangunan.remove(position);
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
        return listBangunan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView btnBangunan;
        private ImageView imageBangunan;
        private TextView namaBangunan;

        public ViewHolder(View itemView) {
            super(itemView);

            btnBangunan = (CardView) itemView.findViewById(R.id.cv_bangunan);
            imageBangunan = (ImageView) itemView.findViewById(R.id.image_bangunan);
            namaBangunan = (TextView) itemView.findViewById(R.id.nama_bangunan);
        }
    }
}