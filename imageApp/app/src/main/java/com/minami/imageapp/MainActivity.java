package com.minami.imageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.minami.imageapp.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerView;


    final String url = "https://block1-image-test.s3.ap-northeast-2.amazonaws.com/photos.json";


    ArrayList<Post> postArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);


        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.Get,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(view.GONE);
                        try {
                            JSONArray data = response.getJSONArray("data");

                            for(int i = 0; i < data.length();i++) {
                                Post post = new Post(
                                        data.getJSONObject(i).getInt("albumId"),
                                        data.getJSONObject(i).getInt("id"),
                                        data.getJSONObject(i).getString("title"),
                                        data.getJSONObject(i).getString("url"),
                                        data.getJSONObject(i).getString("thumbnailUrl")
                                );

                                postArrayList.add(post);


                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
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