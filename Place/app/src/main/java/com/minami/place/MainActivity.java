package com.minami.place;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.minami.place.adpater.PlaceAdapter;
import com.minami.place.api.NetworkClient;
import com.minami.place.api.PlaceApi;
import com.minami.place.config.Config;
import com.minami.place.model.Place;
import com.minami.place.model.PlaceList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText editKeyword;
    ImageView imgSearch;
    ProgressBar progressBar;

    // 리사이클러뷰 관련 변수
    RecyclerView recyclerView;
    PlaceAdapter adapter;
    ArrayList<Place> placeArrayList = new ArrayList<>();

    // GPS 정보 가져오기 위한 변수
    LocationManager locationManager;
    LocationListener locationListener;

    // 현재 나의 위치값을 저장할 변수.
    double lat;
    double lng;

    boolean isLocationReady = false;

    final int radius = 2000;
    final String language = "ko";

    String keyword = "";

    // 페이징 처리를 위한 변수
    String pagetoken ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editKeyword = findViewById(R.id.editKeyword);
        imgSearch = findViewById(R.id.imgSearch);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCont = recyclerView.getAdapter().getItemCount();

                if(lastPosition + 1 == totalCont){
                    if(pagetoken.isEmpty() == false){
                        addNetworkData();
                    }
                }
            }
        });


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();

                isLocationReady = true;
            }
        };

        // 권한 허용하는 코드
        if(ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    100);
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                3000,
                -1,
                locationListener
        );


        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = editKeyword.getText().toString().trim();

                if(keyword.isEmpty()){
                    Toast.makeText(MainActivity.this,
                            "검색어를 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isLocationReady == false){
                    Toast.makeText(MainActivity.this,
                            "아직 내 위치를 못찾았습니다. 잠시후에 이용하세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                getNetworkData();

            }
        });
    }

    private void addNetworkData() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
        PlaceApi api = retrofit.create(PlaceApi.class);
        Call<PlaceList> call = api.getPlaceList(
                lat+","+lng ,
                radius,
                language,
                keyword,
                pagetoken,
                Config.API_KEY
        );
        call.enqueue(new Callback<PlaceList>() {
            @Override
            public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){

                    PlaceList placeList = response.body();

                    if( placeList.next_page_token != null){
                        pagetoken = placeList.next_page_token;
                    }else{
                        pagetoken = "";
                    }

                    placeArrayList.addAll( placeList.results );

                    adapter.notifyDataSetChanged();

                }else{

                }
            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void getNetworkData() {

        // API 호출
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);

        PlaceApi api = retrofit.create(PlaceApi.class);

        Call<PlaceList> call = api.getPlaceList(
                lat+","+lng ,
                radius,
                language,
                keyword,
                Config.API_KEY);

        call.enqueue(new Callback<PlaceList>() {
            @Override
            public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
                Log.i("AAA", ""+response.code());

                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){

                    PlaceList placeList = response.body();

                    if(placeList.next_page_token != null){
                        pagetoken = placeList.next_page_token;
                    }

                    placeArrayList.clear();

                    placeArrayList.addAll(placeList.results);

                    // 어댑터 생성.
                    adapter = new PlaceAdapter(MainActivity.this, placeArrayList);
                    recyclerView.setAdapter(adapter);

                }else{

                }
            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100){
            // 허용하지 않았으면, 다시 허용하라는 알러트 띄운다.
            if(ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                finish();
                return;
            }
            // 허용했으면, GPS 정보 가져오는 코드 넣는다.
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    3000,
                    -1,
                    locationListener
            );
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {dl;jk;.kl;

        int itemId = item.getItemId();

        if(itemId == R.id.menuMap){

            Intent intent = new Intent(MainActivity.this, PlaceActivity.class);
            intent.putExtra("placeArrayList", placeArrayList);
            intent.putExtra("myLat", lat);
            intent.putExtra("myLng", lng);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}