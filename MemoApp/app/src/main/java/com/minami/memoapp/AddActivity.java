package com.minami.memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.minami.memoapp.api.MemoApi;
import com.minami.memoapp.api.NetworkClient;
import com.minami.memoapp.config.Config;

import java.util.Calendar;

import retrofit2.Retrofit;

public class AddActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button btnDate;
    Button btnTime;
    Button btnSave;

    String date = " ";
    String time = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        btnSave = findViewById(R.id.btnSave);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(
                        AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int i, int i1, int i2) {

                                // 월은, +1 해줘야한다
                                int month = i1 + 1;

                                // 월이나 일은, 한자리면 왼쪽에 0 붙여야 한다.
                                String strMonth;
                                if(month < 10) {
                                    strMonth = "0" + month;
                                }else{
                                    strMonth = "" + month;
                                }
                                String strDay;
                                if(i2 < 10){
                                    strDay = "0" + i2;
                                } else{
                                    strDay = ""+i2;
                                }

                                date = i + "-" + strMonth + "-" + strDay;

                                btnDate.setText(date);

                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(
                        AddActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int i, int i1) {
                                String strHour;
                                if(i < 10){
                                    strHour = "0" + i;
                                }else {
                                    strHour = "" + i;
                                }
                                String strMin;
                                if(i1 < 10){
                                    strMin = "0" + i1;
                                }else {
                                    strMin = "" + i1;
                                }
                                time = strHour + ":" + strMin;
                                btnDate.setText(time);
                            }
                        },
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        true
                );
                dialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String content = editContent.getText().toString().trim();
                String datetime = date + " " + time;

                if(title.isEmpty() || content.isEmpty() || date.isEmpty() || time.isEmpty()){
                    Toast.makeText(AddActivity.this,
                            "항목을 모두 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return;

                }
                showProgress();

                Retrofit retrofit = NetworkClient.getRetrofitClient(AddActivity.this);

                MemoApi api = retrofit.create(MemoApi.class);

                // 토큰 가져온다
                SharedPreferences sp - getSharedPreferences(Config.PREFERENCE_NAME);
                String token = sp.getString("token","");
                token = "Bearer" + token;




                api.addMemo();

            }
        });

    }
    Dialog dialog;

    private void showProgress(){
        dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(new ProgressBar(this));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void dismissProgress(){
        dialog.dismiss();
    }



}
