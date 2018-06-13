package com.bangunanbersejarah.admin.bangunanbersejarah.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bangunanbersejarah.admin.bangunanbersejarah.R;
import com.bangunanbersejarah.admin.bangunanbersejarah.adapter.BangunanAdapter;
import com.bangunanbersejarah.admin.bangunanbersejarah.api.BaseApiService;
import com.bangunanbersejarah.admin.bangunanbersejarah.api.UtilsApi;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.Bangunan;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.BangunanResponse;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.Daerah;
import com.bangunanbersejarah.admin.bangunanbersejarah.model.DaerahResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class JawaBaratFragment extends Fragment {

    private RecyclerView rvBangunan;
    private BangunanAdapter adapter;
    List<Bangunan> listBangunan = new ArrayList<>();
    List<Daerah> listResponseDaerah = new ArrayList<>();
    List<String> listDaerah = new ArrayList<>();

    Spinner spinner;

    final String idProvinsi = "3";

    ProgressDialog loading;

    BaseApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jawa_barat, container, false);

        rvBangunan = (RecyclerView) view.findViewById(R.id.rv_bangunan);
        spinner = (Spinner) view.findViewById(R.id.spinner_daerah);

        apiService = UtilsApi.getAPIService();

        adapter = new BangunanAdapter(getContext(), listBangunan);

        rvBangunan.setHasFixedSize(true);
        rvBangunan.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvBangunan.setAdapter(adapter);

        addSpinnerItems();

        getAllBangunan();

        return view;
    }

    public void addSpinnerItems() {
        apiService.getListDaerah(idProvinsi).enqueue(new Callback<DaerahResponse>() {
            @Override
            public void onResponse(Call<DaerahResponse> call, Response<DaerahResponse> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    listResponseDaerah = response.body().getListDaerah();

                    listDaerah.add("--Pilih Daerah--");
                    for(int i=0;i<listResponseDaerah.size();i++){
                        listDaerah.add(listResponseDaerah.get(i).getNamaDaerah());
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_spinner_item, listDaerah);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dataAdapter);
                    dataAdapter.notifyDataSetChanged();

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String key = adapterView.getItemAtPosition(i).toString();
                            int index = listDaerah.indexOf(key); //0, 1, 2
                            if(index>0){
                                getBangunan(listResponseDaerah.get(index-1).getIdDaerah());
                            }
                            else {
                                getAllBangunan();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
                else {
                    loading.dismiss();
                    Toast.makeText(getContext(), "Failed to Fetch Data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DaerahResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getContext(), "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Provinsi Jawa Barat");
    }

    public void getAllBangunan() {
        loading = ProgressDialog.show(getContext(), null, "Harap Tunggu...", true, false);

        apiService.getAllListBangunan(idProvinsi, "1").enqueue(new Callback<BangunanResponse>() {
            @Override
            public void onResponse(Call<BangunanResponse> call, Response<BangunanResponse> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    listBangunan = response.body().getListBangunan();

                    rvBangunan.setAdapter(new BangunanAdapter(getContext(), listBangunan));
                    adapter.notifyDataSetChanged();
                }
                else {
                    loading.dismiss();
                    Toast.makeText(getContext(), "Failed to Fetch Data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BangunanResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getContext(), "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getBangunan(String idDaerah) {
        loading = ProgressDialog.show(getContext(), null, "Harap Tunggu...", true, false);

        apiService.getListBangunan(idProvinsi, idDaerah, "1").enqueue(new Callback<BangunanResponse>() {
            @Override
            public void onResponse(Call<BangunanResponse> call, Response<BangunanResponse> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    listBangunan = response.body().getListBangunan();

                    rvBangunan.setAdapter(new BangunanAdapter(getContext(), listBangunan));
                    adapter.notifyDataSetChanged();
                }
                else {
                    loading.dismiss();
                    Toast.makeText(getContext(), "Failed to Fetch Data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BangunanResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getContext(), "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
