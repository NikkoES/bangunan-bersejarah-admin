## 1. SPLASHSCREEN

![alt text](https://raw.githubusercontent.com/username/projectname/branch/path/to/img.png)

### activity_splash_screen.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/colorWhite">

    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true">

        <ImageView
            android:id="@+id/icon"
            android:layout_width="300dp"
            android:layout_height="300dp"
            android:layout_centerHorizontal="true"
            android:src="@drawable/ic_maskot" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_below="@+id/icon"
            android:text="Aplikasi Bangunan Bersejarah"
            android:gravity="center"
            android:textSize="18sp"
            android:textStyle="bold"
            android:textColor="@color/colorPrimary"
            android:textAllCaps="true"/>

    </RelativeLayout>

</RelativeLayout>
```

### SplashScreenActivity.java
```java
public class SplashScreenActivity extends AppCompatActivity {

    final int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
```

## 2. HOME

![alt text](https://raw.githubusercontent.com/username/projectname/branch/path/to/img.png)

### fragment_main.xml
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/colorWhite"
    tools:context="com.example.nikkoekasaputra.bangunanbersejarah.fragment.BantenFragment">

    <ImageView
        android:id="@+id/image_maskot"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="3.5"
        android:src="@drawable/ic_maskot"/>

    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:layout_margin="8dp">
        
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/colorPrimary"
            android:padding="16dp">

            <TextView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:gravity="center_vertical|center_horizontal"
                android:text="Hai, Selamat Datang di Aplikasi Belajar Bangunan Bersejarah yang ada di Pulau Jawa"
                android:textColor="@color/colorWhite"
                android:textSize="18sp"
                android:textStyle="bold"/>

        </LinearLayout>

    </android.support.v7.widget.CardView>

</LinearLayout>
```

### MainFragment.java
```java
public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Aplikasi Bangunan Bersejarah");
    }

}
```

## 3. MENU

![alt text](https://raw.githubusercontent.com/username/projectname/branch/path/to/img.png)

### activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:openDrawer="start">

    <include
        layout="@layout/app_bar_main"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <android.support.design.widget.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        app:headerLayout="@layout/nav_header_main"
        app:menu="@menu/activity_main_drawer" />

</android.support.v4.widget.DrawerLayout>
```

### MainActivity.java
```java
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
            case R.id.nav_tentang:
                fragment = new TentangFragment();
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
}
```

## 4. BANTEN

![alt text](https://raw.githubusercontent.com/username/projectname/branch/path/to/img.png)

### fragment_banten.xml
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.example.nikkoekasaputra.bangunanbersejarah.fragment.BantenFragment">

    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="8dp">

        <Spinner
            android:id="@+id/spinner_daerah"
            android:layout_width="match_parent"
            android:layout_height="48dp" />

    </android.support.v7.widget.CardView>

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_bangunan"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

### BantenFragment.java
```java
public class BantenFragment extends Fragment {

    private RecyclerView rvBangunan;
    private BangunanAdapter adapter;
    List<Bangunan> listBangunan = new ArrayList<>();
    List<Daerah> listResponseDaerah = new ArrayList<>();
    List<String> listDaerah = new ArrayList<>();

    Spinner spinner;

    final String idProvinsi = "1";

    ProgressDialog loading;

    BaseApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banten, container, false);

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
        getActivity().setTitle("Provinsi Banten");
    }

    public void getAllBangunan() {
        loading = ProgressDialog.show(getContext(), null, "Harap Tunggu...", true, false);

        apiService.getAllListBangunan(idProvinsi).enqueue(new Callback<BangunanResponse>() {
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

        apiService.getListBangunan(idProvinsi, idDaerah).enqueue(new Callback<BangunanResponse>() {
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
```

## 5. JAKARTA

![alt text](https://raw.githubusercontent.com/username/projectname/branch/path/to/img.png)

### fragment_jakarta.xml
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.example.nikkoekasaputra.bangunanbersejarah.fragment.BantenFragment">

    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="8dp">

        <Spinner
            android:id="@+id/spinner_daerah"
            android:layout_width="match_parent"
            android:layout_height="48dp" />

    </android.support.v7.widget.CardView>

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_bangunan"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

### JakartaFragment.java
```java
public class JakartaFragment extends Fragment {

    //TODO TERAPKAN FITUR DI FRAGMENT JAKARTA KE SEMUA FRAGMENT !!

    private RecyclerView rvBangunan;
    private BangunanAdapter adapter;
    List<Bangunan> listBangunan = new ArrayList<>();
    List<Daerah> listResponseDaerah = new ArrayList<>();
    List<String> listDaerah = new ArrayList<>();

    Spinner spinner;

    final String idProvinsi = "2";

    ProgressDialog loading;

    BaseApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jakarta, container, false);

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
        getActivity().setTitle("Provinsi DKI Jakarta");
    }

    public void getAllBangunan() {
        loading = ProgressDialog.show(getContext(), null, "Harap Tunggu...", true, false);

        apiService.getAllListBangunan(idProvinsi).enqueue(new Callback<BangunanResponse>() {
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

        apiService.getListBangunan(idProvinsi, idDaerah).enqueue(new Callback<BangunanResponse>() {
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
```

## 6. JAWA BARAT

![alt text](https://raw.githubusercontent.com/username/projectname/branch/path/to/img.png)

### fragment_jawa_barat.xml
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.example.nikkoekasaputra.bangunanbersejarah.fragment.BantenFragment">

    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="8dp">

        <Spinner
            android:id="@+id/spinner_daerah"
            android:layout_width="match_parent"
            android:layout_height="48dp" />

    </android.support.v7.widget.CardView>

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_bangunan"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

### JawaBaratFragment.java
```java
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

        apiService.getAllListBangunan(idProvinsi).enqueue(new Callback<BangunanResponse>() {
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

        apiService.getListBangunan(idProvinsi, idDaerah).enqueue(new Callback<BangunanResponse>() {
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
```

## 7. JAWA TENGAH

![alt text](https://raw.githubusercontent.com/username/projectname/branch/path/to/img.png)

### fragment_jawa_tengah.xml
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.example.nikkoekasaputra.bangunanbersejarah.fragment.BantenFragment">

    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="8dp">

        <Spinner
            android:id="@+id/spinner_daerah"
            android:layout_width="match_parent"
            android:layout_height="48dp" />

    </android.support.v7.widget.CardView>

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_bangunan"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

### JawaTengahFragment.java
```java
public class JawaTengahFragment extends Fragment {

    private RecyclerView rvBangunan;
    private BangunanAdapter adapter;
    List<Bangunan> listBangunan = new ArrayList<>();
    List<Daerah> listResponseDaerah = new ArrayList<>();
    List<String> listDaerah = new ArrayList<>();

    Spinner spinner;

    final String idProvinsi = "4";

    ProgressDialog loading;

    BaseApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jawa_tengah, container, false);

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
        getActivity().setTitle("Provinsi Jawa Tengah");
    }

    public void getAllBangunan() {
        loading = ProgressDialog.show(getContext(), null, "Harap Tunggu...", true, false);

        apiService.getAllListBangunan(idProvinsi).enqueue(new Callback<BangunanResponse>() {
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

        apiService.getListBangunan(idProvinsi, idDaerah).enqueue(new Callback<BangunanResponse>() {
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
```

## 8. JAWA TIMUR

![alt text](https://raw.githubusercontent.com/username/projectname/branch/path/to/img.png)

### fragment_jawa_timur.xml
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.example.nikkoekasaputra.bangunanbersejarah.fragment.BantenFragment">

    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="8dp">

        <Spinner
            android:id="@+id/spinner_daerah"
            android:layout_width="match_parent"
            android:layout_height="48dp" />

    </android.support.v7.widget.CardView>

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_bangunan"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

### JawaTimurFragment.java
```java
public class JawaTimurFragment extends Fragment {

    private RecyclerView rvBangunan;
    private BangunanAdapter adapter;
    List<Bangunan> listBangunan = new ArrayList<>();
    List<Daerah> listResponseDaerah = new ArrayList<>();
    List<String> listDaerah = new ArrayList<>();

    Spinner spinner;

    final String idProvinsi = "5";

    ProgressDialog loading;

    BaseApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jawa_timur, container, false);

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
        getActivity().setTitle("Provinsi Jawa Timur");
    }

    public void getAllBangunan() {
        loading = ProgressDialog.show(getContext(), null, "Harap Tunggu...", true, false);

        apiService.getAllListBangunan(idProvinsi).enqueue(new Callback<BangunanResponse>() {
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

        apiService.getListBangunan(idProvinsi, idDaerah).enqueue(new Callback<BangunanResponse>() {
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
```

## 9. YOGYAKARTA

![alt text](https://raw.githubusercontent.com/username/projectname/branch/path/to/img.png)

### fragment_yogyakarta.xml
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.example.nikkoekasaputra.bangunanbersejarah.fragment.BantenFragment">

    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="8dp">

        <Spinner
            android:id="@+id/spinner_daerah"
            android:layout_width="match_parent"
            android:layout_height="48dp" />

    </android.support.v7.widget.CardView>

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_bangunan"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>

```

### YogyakartaFragment.java
```java
public class YogyakartaFragment extends Fragment {

    private RecyclerView rvBangunan;
    private BangunanAdapter adapter;
    List<Bangunan> listBangunan = new ArrayList<>();
    List<Daerah> listResponseDaerah = new ArrayList<>();
    List<String> listDaerah = new ArrayList<>();

    Spinner spinner;

    final String idProvinsi = "6";

    ProgressDialog loading;

    BaseApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yogyakarta, container, false);

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
        getActivity().setTitle("Provinsi DIY Yogyakarta");
    }

    public void getAllBangunan() {
        loading = ProgressDialog.show(getContext(), null, "Harap Tunggu...", true, false);

        apiService.getAllListBangunan(idProvinsi).enqueue(new Callback<BangunanResponse>() {
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

        apiService.getListBangunan(idProvinsi, idDaerah).enqueue(new Callback<BangunanResponse>() {
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
```

## 10. TENTANG

![alt text](https://raw.githubusercontent.com/username/projectname/branch/path/to/img.png)

### fragment_tentang.xml
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/colorWhite"
    tools:context="com.example.nikkoekasaputra.bangunanbersejarah.fragment.BantenFragment">

    <ImageView
        android:id="@+id/image_maskot"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:src="@drawable/ic_maskot"/>

    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/colorPrimary"
            android:padding="16dp">

            <TextView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:gravity="center_vertical|center_horizontal"
                android:text="Aplikasi Bangunan Bersejarah adalah aplikasi untuk mengetahui informasi sejarah bangunan bersejarah yang ada di Provinsi Jawa Barat.\nDalam aplikasi ini dapat dilihat berbagai macam bangunan berdasarkan provinsi dan daerahnya."
                android:textColor="@color/colorWhite"
                android:textSize="18sp"
                android:textStyle="bold"/>

        </LinearLayout>

    </android.support.v7.widget.CardView>


</LinearLayout>

```

### TentangFragment.java
```java
public class TentangFragment extends Fragment {


    public TentangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tentang, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Tentang Aplikasi");
    }

}
```

## 11. DETAIL BANGUNAN

![alt text](https://raw.githubusercontent.com/username/projectname/branch/path/to/img.png)

### activity_detail_bangunan.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <android.support.design.widget.AppBarLayout
        android:id="@+id/appbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:fitsSystemWindows="true"
        android:theme="@style/AppTheme.AppBarOverlay">

        <android.support.design.widget.CollapsingToolbarLayout
            android:id="@+id/ctolbar"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:fitsSystemWindows="true"
            app:contentScrim="?attr/colorPrimary"
            app:expandedTitleMargin="18dp"
            app:layout_scrollFlags="scroll|exitUntilCollapsed|snap">

            <ImageView
                android:id="@+id/iv_bangunan"
                android:layout_width="match_parent"
                android:layout_height="200dp"
                android:background="@color/colorPrimary"
                android:contentDescription="@string/app_name"
                android:fitsSystemWindows="true"
                android:scaleType="centerCrop"
                app:layout_collapseMode="parallax"
                app:srcCompat="@drawable/ic_nav_header" />

            <android.support.v7.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                app:layout_collapseMode="pin"
                app:popupTheme="@style/ThemeOverlay.AppCompat.Light" />

        </android.support.design.widget.CollapsingToolbarLayout>

    </android.support.design.widget.AppBarLayout>

    <android.support.v4.widget.NestedScrollView
        android:id="@+id/nested"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="10dp"
            android:orientation="vertical">

            <TextView
                android:id="@+id/tv_nama_bangunan"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="How To Create Parallax ActionBar"
                android:textSize="21sp"
                android:textStyle="bold" />

            <TextView
                android:id="@+id/tv_sejarah_bangunan"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Hello, Im the clipcodes on youtube and blogger."
                android:textSize="18sp" />

        </LinearLayout>

    </android.support.v4.widget.NestedScrollView>

</android.support.design.widget.CoordinatorLayout>
```

### DetailBangunanActivity.java
```java
public class DetailBangunanActivity extends AppCompatActivity {

    AppBarLayout Appbar;
    CollapsingToolbarLayout CoolToolbar;
    Toolbar toolbar;

    boolean ExpandedActionBar = true;

    String idBangunan, namaBangunan, sejarahBangunan, imageBangunan, idProvinsi, idDaerah;

    @BindView(R.id.iv_bangunan)
    ImageView imgBangunan;
    @BindView(R.id.tv_nama_bangunan)
    TextView tvNamaBangunan;
    @BindView(R.id.tv_sejarah_bangunan)
    TextView tvSejarahBangunan;

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
        idProvinsi = getIntent().getStringExtra("id_provinsi");
        idDaerah = getIntent().getStringExtra("id_daerah");

        Glide.with(getApplicationContext())
                .load(imageBangunan)
                .placeholder(R.drawable.ic_nav_header)
                .into(imgBangunan);
        tvNamaBangunan.setText(namaBangunan);
        tvSejarahBangunan.setText(sejarahBangunan);

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
        }
        return super.onOptionsItemSelected(item);
    }
}
```