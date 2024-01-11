package com.minami.intentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                composeEmail(new String[]{"acv@naver.com","bbb@naver.com"}, "안녕하세요" );


            }
        });
    }
    // 연락처 선택하는 액티비티 띄우는 함수
    void selectContact(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivity(intent);
    }
    // 웹 브라우저를 실행시키는 함수
    void openWebPage(String url){
        Uri uri = android.net.Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
    // sms를 보내기 위한 화면 실행하는 함수.
    void composeSMS(String phone){
        Uri uri = Uri.parse("smsto:"+phone);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    // 이메일 작성하는 액티비티 실행하는 함수.
    void composeEmail(String[] address,String subject){
        Uri uri =Uri.parse("mailto");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setData();
        intent.putExtra(Intent.EXTRA_EMAIL,address);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        startActivity(intent);
    }

    // 텍스트를 공유하는 함수.
    void shareText(String sentence){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, sentence);
        intent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(intent,)
    }

}