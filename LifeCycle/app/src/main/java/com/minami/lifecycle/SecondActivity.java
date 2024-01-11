package com.minami.lifecycle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.i("AAA","Main : onCreate 함수실행");

        txtName = findViewById(R.id.txtName);
        txtAge = findViewById(R.id.txtAge);

        // 만약에 다른 액티비티가 이 액티비티로 데이터를 전달하면
        // 데이터를 받아준다
        String name = getIntent().getStringExtra("name");
        int age = getIntent().getIntExtra("age", 0);

        txtName.setText(name);
        txtAge.setText(""+age);

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback() {
            @Override
            public void handleOnBackPressed() {
                // 백버튼 눌렀을때 하고싶은 코드를 작성.

                // 메인 액티비티로 나이 + 10한 수를 보내고싶다
                age = age + 10;

                Intent intent = new Intent();
                intent.putExtra("age",age);
                setResult(OK);
            }
        });



    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("AAA","Main : onPause 함수실행");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("AAA","Main : onStop 함수실행");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("AAA","Main : onStart 함수실행");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("AAA","Main : onResume 함수실행");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("AAA","Main : onDestroy 함수실행");
    }
}