package com.minami.networklist1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.minami.networklist1.model.Employee;

public class AddActivity extends AppCompatActivity {

    EditText editName;
    EditText editSalary;
    EditText editAge;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editName = findViewById(R.id.editName);
        editSalary = findViewById(R.id.editSalary);
        editAge = findViewById(R.id.editAge);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String strSalary = editSalary.getText().toString().trim();
                String strAge = editAge.getText().toString().trim();

                if(name.isEmpty() || strSalary.isEmpty() || strAge.isEmpty()) {
                    Toast.makeText("필수항목을 모두 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int salary = Integer.parseInt(strSalary);
                int age = Integer.parseInt(strAge);

                Employee employee = new extents(name,salary,age);

                Intent intent = new Intent();
                intent.putExtra("employee",employee);


            }

        });


        }





    }
}
