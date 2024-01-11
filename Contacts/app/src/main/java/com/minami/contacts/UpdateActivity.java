package com.minami.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AddActivity{

    EditText editName;
    EditText editPhone;
    Button btnSave;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);

        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        int index = getIntent().getIntExtra("index",0);

        editName.setText(name);
        editPhone.setText(phone);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if(name.isEmpty() || phone.isEmpty()){
                    Toast.makeText(UpdateActivity.this,"내용을 입력하세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                // 메인 액티비티로 데이터를 돌려줘야한다.
                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("index",index);
                setResult(200,intent);

                Toast.makeText(UpdateActivity.this, "수정완료되었습니다.",Toast.LENGTH_SHORT).show();

                finish();
            }
        });


    }
}
