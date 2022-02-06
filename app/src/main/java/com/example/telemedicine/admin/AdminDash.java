package com.example.telemedicine.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.telemedicine.R;
import com.example.telemedicine.activities.ListFragment;
import com.google.android.material.card.MaterialCardView;

public class AdminDash extends AppCompatActivity implements View.OnClickListener{
LinearLayout open_chats,open_all,open_my;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);
        open_chats=findViewById(R.id.open_all_chats);
        open_all=findViewById(R.id.open_all_requests);
        open_my=findViewById(R.id.open_my_requests);
        open_my.setOnClickListener(this);
        open_all.setOnClickListener(this);
        open_chats.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    switch (view.getId())
    {
        case R.id.open_all_chats:
           Intent intent=new Intent(getApplicationContext(),ListActivity.class);
           intent.putExtra("key","admin");
            startActivity(intent);
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.frameLayout, new ListFragment()).commit();
            break;
        case R.id.open_all_requests:
            Intent i=new Intent(getApplicationContext(),RequestsActivity.class);
            i.putExtra("key","all");
            startActivity(i);
            break;
        case R.id.open_my_requests:

            Intent in=new Intent(getApplicationContext(),RequestsActivity.class);
            in.putExtra("key","my");
            startActivity(in);
            break;
    }
    }
}