package com.minami.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                // 맵이 화면에 나타나면
                // 작업하고 싶은 코드를 여기에 작성한다

                // 위도,경도를 위치데이터 셋팅
                LatLng myLocation = new LatLng(37.5429,126.6772);

                // 지도의 중심을, 내가 정한 위치로 셋팅.
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));

                // 마커를 만들어서, 지도에 표시
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(myLocation);
                markerOptions.title("연희직업전문학교");
                googleMap.addMarker(markerOptions).setTag(0);

                MarkerOptions markerOptions1 = new MarkerOptions();
                markerOptions1.position(myLocation);
                markerOptions1.title("연희카페");
                googleMap.addMarker(markerOptions).setTag(1);

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {

                        int tagId = (int) marker.getTag();

                        return false;

                        if(tagId == 0){

                        }else if(tagId == 1){

                        }

                        String title = marker.gettitle();

                        Toast.makeText

                    }


                });


            }
        });
    }
}