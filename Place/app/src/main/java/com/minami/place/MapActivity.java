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

public class MapActivity extends AppCompatActivity {

    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        place = (Place) getIntent().getSerializableExtra("place");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                // 지도의 중심을, 가게 죄표로 이동시키고
                // 마커도 표시한ㄴ다

                double lat = place.geometry.location.lat;
                double lng = place.geometry.location.lng;

                LatLng latLng =new LatLng(lat, lng);

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

                MarkerOptions options = new MarkerOptions();
                googleMap.addMarker(options.position(latLng).title(place.name));

            }
        });
    }
}