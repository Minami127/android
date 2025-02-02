package com.minami.networklist1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.minami.networklist1.model.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ProgressBar progressBar;
    RecyclerView recyclerView;


    ArrayList<Employee> employeeArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
            }
        });


        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "https://block1-image-test.s3.ap-northeast-2.amazonaws.com/employees.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray data = response.getJSONArray("data");

                            for(int i = 0; i < data.length(); i++ ){
                                JSONObject item = data.getJSONObject(i);
                                int id = item.getInt("id");
                                String name = item.getString("employee_name");
                                int salary = item.getInt("employee_salary");
                                int age = item.getInt("employee_age");
                                String profileImage=item.getString("profile_image");

                                Employee employee = new extents(name, salary, age);

                                employeeArrayList.add(employee);
                            }

                            // 화면에 표시

                        } catch (JSONException e) {
                            // 유저한테 알린다.

                            return;
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(request);
    }
}