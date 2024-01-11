package com.minami.network2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.minami.network2.model.Post;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    ArrayList<Post> postArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        // 네트워크를 통해서 데이터를 받아온다.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonArrayRequest request = new JsonArrayRequest("https://jsonplaceholder.typicode.com/posts",
                null,
                new Response.Listener<>() {
                    @Override
                    public void onResponse(Object response) {

                        for(int i = 0; i < response.length();i++){

                        try{
                            int userId = response.getJSONObject(0).getInt("userId");
                            int id = response.getJSONObject(0).getInt("Id");
                            String title = response.getJSONObject(0).getString("title");
                            String body = response.getJSONObject(0).getString("body");


                        }
                        
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }


        )

    }
}