package com.minami.tapbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.minami.tapbar.FirstFragment;
import com.minami.tapbar.SecondFragment;
import com.minami.tapbar.ThirdFragment;

public class MainActivity extends AppCompatActivity {
    // 아래 아이콘 나오는 영역
    BottomNavigationView bottomNavigationView;

    // 각 프레그먼트를 멤버변수로 만든다.
    Fragment firstFragment;
    Fragment secondFragment;
    Fragment thirdFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                Fragment fragment = null;

                if(itemId == R.id.firstFragment){

                    fragment = firstFragment;

                }else if(itemId == R.id.secondFragment){

                    fragment = secondFragment;

                }else if(itemId == R.id.thirdFragment){
                    fragment = thirdFragment;
                }

                return loadFragment(fragment);
            }
        });

    }

    boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();
            return true;
        } else {
            return false;
        }
    }

}