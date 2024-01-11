package com.minami.youtubeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.minami.youtubeapp.model.Video;

public class PhotoActivity extends AppCompatActivity {

    ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Video video = (Video) getIntent().getSerializableExtra("video");


        imgPhoto = findViewById(R.id.imgPhoto);

        Glide.with(PhotoActivity.this).load(video.highUrl).into(imgPhoto);



    }
}