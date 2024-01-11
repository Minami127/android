package com.minami.postingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.minami.postingapp.api.NetworkClient;
import com.minami.postingapp.api.UserApi;
import com.minami.postingapp.config.Config;
import com.minami.postingapp.model.User;
import com.minami.postingapp.model.UserRes;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;
    Button btnRegister;
    TextView txtLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if(email.isEmpty()){
                    Toast.makeText(RegisterActivity.this,
                            "항목을 모두 입력하세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if (pattern.matcher(email).matches() == false){
                    Toast.makeText(RegisterActivity.this,
                            "이메일 형식을 확인하세요",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 4 || password.length() > 12){
                    Toast.makeText(RegisterActivity.this,
                            "비번길이를 확인하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 네트워크  처리중이다 라는 표시를 해준다.
                showProgress();

                Retrofit retrofit = NetworkClient.getRetrofitClient(RegisterActivity.this);
                UserApi api = retrofit.create(UserApi.class);

                User user = new User (email, password);

                Call<UserRes> call = api.register(user);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        dismissProgress();

                        if (response.isSuccessful()){
                            UserRes userRes = response.body();

                            SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("token", userRes.accessToken);
                            editor.apply();

                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);

                            finish();
                        } if(response)


                    }



                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {

                    }
                });


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