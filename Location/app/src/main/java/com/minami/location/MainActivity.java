package com.minami.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;

    LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 위치를 가져오기 위해서는, 시스템서비스로 부터
        // 로케이션 매니저를 받아온다

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // 리스너를 만든다
        // 위치가 변하ㅏㄹ때마다 호출되는 함수를 작성

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                // 위도, 경도값을 뽑아서, 우리의 맞는 코드를 작성한다
                //위도
                double lat = location.getLatitude();
                // 경도
                double lng = location.getLongitude();

                Log.i("AAA","위도: "+ lat);
                Log.i("AAA","위도: "+ lng);
                Log.i("AAA","   ");




            }
        };

        // 권한 허용하는 코드
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                                                Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,300, -1, locationListener
        );

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

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        100);
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
}