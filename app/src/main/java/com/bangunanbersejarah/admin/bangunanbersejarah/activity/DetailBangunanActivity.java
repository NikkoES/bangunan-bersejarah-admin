package com.bangunanbersejarah.admin.bangunanbersejarah.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bangunanbersejarah.admin.bangunanbersejarah.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailBangunanActivity extends AppCompatActivity {

    AppBarLayout Appbar;
    CollapsingToolbarLayout CoolToolbar;
    Toolbar toolbar;

    boolean ExpandedActionBar = true;

    String idBangunan, namaBangunan, sejarahBangunan, imageBangunan, alamatBangunan, idProvinsi, idDaerah, tanggal;

    @BindView(R.id.iv_bangunan)
    ImageView imgBangunan;
    @BindView(R.id.tv_nama_bangunan)
    TextView tvNamaBangunan;
    @BindView(R.id.tv_sejarah_bangunan)
    TextView tvSejarahBangunan;
    @BindView(R.id.tv_tanggal)
    TextView tvTanggal;
    @BindView(R.id.tv_alamat_bangunan)
    TextView tvAlamatBangunan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bangunan);

        Appbar = (AppBarLayout) findViewById(R.id.appbar);
        CoolToolbar = (CollapsingToolbarLayout) findViewById(R.id.ctolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idBangunan = getIntent().getStringExtra("id_bangunan");
        namaBangunan = getIntent().getStringExtra("nama_bangunan");
        sejarahBangunan = getIntent().getStringExtra("sejarah_bangunan");
        imageBangunan = getIntent().getStringExtra("image_bangunan");
        alamatBangunan = getIntent().getStringExtra("alamat_bangunan");
        idProvinsi = getIntent().getStringExtra("id_provinsi");
        idDaerah = getIntent().getStringExtra("id_daerah");
        tanggal = getIntent().getStringExtra("tanggal");

        Glide.with(getApplicationContext())
                .load(imageBangunan)
                .placeholder(R.drawable.ic_nav_header)
                .into(imgBangunan);
        tvNamaBangunan.setText(namaBangunan);
        tvSejarahBangunan.setText(sejarahBangunan);
        tvTanggal.setText(tanggal);
        tvAlamatBangunan.setText("Alamat : "+alamatBangunan);

        Appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 200) {
                    ExpandedActionBar = false;
                    CoolToolbar.setTitle(namaBangunan);
                    invalidateOptionsMenu();
                }
                else {
                    ExpandedActionBar = true;
                    CoolToolbar.setTitle(namaBangunan);
                    invalidateOptionsMenu();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            }
            case R.id.share : {
                Intent i = new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
//                i.putExtra(android.content.Intent.EXTRA_TEXT, titleNews+"\n\n"+contentNews+"\n\n"+sourceNews);
                startActivity(Intent.createChooser(i, "Share this Article ?"));
                break;
            }
            case R.id.map : {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+namaBangunan);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
