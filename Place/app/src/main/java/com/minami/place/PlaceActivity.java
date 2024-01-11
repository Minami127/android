package com.minami.place;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.minami.place.model.Place;

import java.util.ArrayList;

public class PlaceActivity extends AppCompatActivity {

    ArrayList<Place> placeArrayList;

    double myLat;
    double myLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        placeArrayList = (ArrayList<Place>) getIntent().getSerializableExtra("placeArrayList");
        myLat = getIntent().getDoubleArrayExtra("myLat", 0);
        myLng = getIntent().getDoubleArrayExtra("myLng", 0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                // 나의 위치를 지도의 중심에 놓고
                LatLng myLocation = new LatLng(myLat,myLng);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,17));

                // 상점들의 위치를 마커로 표시한다.
                for( Place place : placeArrayList){
                    LatLng latLng = new LatLng( Place.Geometry.Location.lat,Place.Geometry.Location.lng);

                    MarkerOptions options = new MarkerOptions();

                    googleMap.addMarker( options.position(latLng).title(place.name));

                }
            }
        });




    }
}