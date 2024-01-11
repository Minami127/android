package com.minami.diceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // 화면에 있는 뷰를 가져 온다.
    Button btnDice;
    ImageView imgDice1;
    ImageView imgDice2;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 뷰를 연결 한다
        btnDice = findViewById(R.id.btnDice);
        imgDice1 = findViewById(R.id.imgDice1);
        imgDice2 = findViewById(R.id.imgDice2);

        mp = MediaPlayer.create(this, R.raw.dice_sound);

        btnDice.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                // 버튼 눌렀을때 하고싶은 일을 여기에 작성 하는것이다

                mp.start();

                // 랜덤으로 숫자를 가져온다 2개!
                Random random = new Random();
                int num1 = random.nextInt(6);
                int num2 = random.nextInt(6);



                if(num1 == 0){
                    imgDice1.setImageResource(R.drawable.dice1);
                }else if(num1 == 1){
                    imgDice1.setImageResource(R.drawable.dice2);
                }else if(num1 == 2){
                    imgDice1.setImageResource(R.drawable.dice3);
                }else if(num1 == 3){
                    imgDice1.setImageResource(R.drawable.dice4);
                }else if(num1 == 4){
                    imgDice1.setImageResource(R.drawable.dice5);
                }else if(num1 == 5){
                    imgDice1.setImageResource(R.drawable.dice6);
                }
                if(num2 == 0){
                    imgDice1.setImageResource(R.drawable.dice1);
                }else if(num2 ==1){
                    imgDice1.setImageResource(R.drawable.dice2);
                }else if(num2 ==2){
                    imgDice1.setImageResource(R.drawable.dice3);
                }else if(num2 ==3){
                    imgDice1.setImageResource(R.drawable.dice4);
                }else if(num2 ==4){
                    imgDice1.setImageResource(R.drawable.dice5);
                }else if(num2 ==5){
                    imgDice1.setImageResource(R.drawable.dice6);
                }
                YoYo.with(Techniques.Shake).duration(400)
                        .repeat(5).playOn(imgDice1);
                YoYo.with(Techniques.Shake).duration(400)
                        .repeat(5).playOn(imgDice2);



            }
        });



    }
}