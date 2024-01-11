package com.minami.contacts;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.minami.contacts.adapter.ContactAdapter;
import com.minami.contacts.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtAdd;

    // 리사이클러뷰는 이따가 다른것들하고 함께.
    RecyclerView recyclerView;
    ContactAdapter adapter;
    ArrayList<Contact> contactArrayList = new ArrayList<>();


    // 애드액티비티로부터 데이터 받는 코드
    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult o) {

                            if(o.getResultCode() == 100){
                                String name = o.getData().getStringExtra("name");
                                String phone = o.getData().getStringExtra("phone");

                                Log.i("AAA" , name + " , " + phone);

                                Contact contact = new Contact(name, phone);

                                contactArrayList.add(contact);

                                adapter.notifyDataSetChanged();

                            } else if (o.getResultCode() == 200){
                                String name = o.getData().getStringExtra("name");
                                String phone = o.getData().getStringExtra("phone");
                                int index = o.getData().getIntExtra("index",0);

                                Contact contact = new Contact(name, phone);

                                contactArrayList.set( index , contact);

                                adapter.notifyDataSetChanged();
                            }

                        }
                    }) ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtAdd = findViewById(R.id.txtAdd3);
        // 리사이클러뷰는 여기.
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 연락처 추가하는 새 액티비티를 실행한다.
                Intent intent = new Intent(MainActivity.this, com.minami.contacts.AddActivity.class);
                launcher.launch(intent);
            }
        });

        // 어댑터 만들고, 리사이클러뷰에 적용!
        adapter = new ContactAdapter(MainActivity.this, contactArrayList);
        recyclerView.setAdapter(adapter);

    }
}