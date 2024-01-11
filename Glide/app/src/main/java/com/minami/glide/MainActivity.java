package com.minami.glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ImageView img1;
    ImageView img2;
    ImageView img3;

    final String imgUrl1 = "https://block-yh-test2.s3.ap-northeast-2.amazonaws.com/2023-01-12T15_46_28.062874.jpg";
    final String imgUrl2 = "https://block-yh-test2.s3.ap-northeast-2.amazonaws.com/2023-01-13T03_31_12.564141.jpeg";
    final String imgUrl3 = "https://block-yh-test2.s3.ap-northeast-2.amazonaws.com/2023-01-13T03_46_46.079772.jpg";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);

        // 네트워크 통해서 이미지르 받아온 후
        // 이미지뷰에 셋팅한다.

        Glide.with(MainActivity.this)
                .load(imgUrl1).into(img1);
        Glide.with(MainActivity.this)
                .load(imgUrl2).into(img2);
        Glide.with(MainActivity.this)
                .load(imgUrl3).into(img3);



    }
}