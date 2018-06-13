package com.bangunanbersejarah.admin.bangunanbersejarah.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bangunanbersejarah.admin.bangunanbersejarah.fragment.PengajuanFragment;
import com.bumptech.glide.Glide;
import com.bangunanbersejarah.admin.bangunanbersejarah.R;
import com.bangunanbersejarah.admin.bangunanbersejarah.fragment.BantenFragment;
import com.bangunanbersejarah.admin.bangunanbersejarah.fragment.JakartaFragment;
import com.bangunanbersejarah.admin.bangunanbersejarah.fragment.JawaBaratFragment;
import com.bangunanbersejarah.admin.bangunanbersejarah.fragment.JawaTengahFragment;
import com.bangunanbersejarah.admin.bangunanbersejarah.fragment.JawaTimurFragment;
import com.bangunanbersejarah.admin.bangunanbersejarah.fragment.TentangFragment;
import com.bangunanbersejarah.admin.bangunanbersejarah.fragment.YogyakartaFragment;
import com.bangunanbersejarah.admin.bangunanbersejarah.fragment.MainFragment;
import com.bangunanbersejarah.admin.bangunanbersejarah.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferencesUtils userDataSharedPreferences;

    JSONObject userProfile;

    String userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userDataSharedPreferences = new SharedPreferencesUtils(this, "UserData");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View v =  navigationView.getHeaderView(0);
        ImageView imageProfile = v.findViewById(R.id.image_profile);
        TextView txtNama = v.findViewById(R.id.txt_nama);
        TextView txtEmail = v.findViewById(R.id.txt_email);

        try {
            userData = userDataSharedPreferences.getPreferenceData("userProfile");
            userProfile = new JSONObject(userData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glide.with(this).load(userProfile.get("profile_photo").toString()).placeholder(R.drawable.ic_profile).into(imageProfile);
            txtNama.setText(userProfile.get("name").toString());
            txtEmail.setText(userProfile.get("email").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        displaySelectedScreen(item.getItemId());

        return true;
    }

    public void displaySelectedScreen(int itemId){
        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new MainFragment();
                break;
            case R.id.nav_prov_banten:
                fragment = new BantenFragment();
                break;
            case R.id.nav_prov_jakarta:
                fragment = new JakartaFragment();
                break;
            case R.id.nav_prov_jawabarat:
                fragment = new JawaBaratFragment();
                break;
            case R.id.nav_prov_jawatengah:
                fragment = new JawaTengahFragment();
                break;
            case R.id.nav_prov_jawatimur:
                fragment = new JawaTimurFragment();
                break;
            case R.id.nav_prov_yogyakarta:
                fragment = new YogyakartaFragment();
                break;
            case R.id.nav_tambah_bangunan:
                startActivity(new Intent(getApplicationContext(), TambahBangunanActivity.class));
                break;
            case R.id.nav_pengajuan_bangunan:
                fragment = new PengajuanFragment();
                break;
            case R.id.nav_tentang:
                fragment = new TentangFragment();
                break;
            case R.id.nav_keluar:
                logout();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void logout(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Anda yakin ingin keluar ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userDataSharedPreferences.removeData("userProfile");
                        finish();
                        Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intentLogin);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();
    }
}
