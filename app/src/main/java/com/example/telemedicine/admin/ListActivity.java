package com.example.telemedicine.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.telemedicine.R;
import com.example.telemedicine.data.UserAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener{
FirebaseFirestore db;
RecyclerView recyclerView;
List<com.example.telemedicine.data.List> list=new ArrayList<>();
UserAdapter adapter;
String docId="xOG6JL8pnFvlUU1uSSBO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        db=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.user_recycler);
        adapter=new UserAdapter(list,getApplicationContext(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        setData();
    }

    private void setData() {
        db.collection("doctors")
                .document(docId)
                .collection("inbox")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots ->
                {
                    for (DocumentSnapshot doc:queryDocumentSnapshots)
                    {
                        if (doc.exists())
                        {
                            String id=doc.getId();
                            String sender=doc.get("name").toString();
                            com.example.telemedicine.data.List lll=new com.example.telemedicine.data.List(sender,id);
                            list.add(lll);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onItemClick(int position, View v) {
        com.example.telemedicine.data.List item=list.get(position);
        String id=item.getId();
        String na=item.getName();
        String docId="xOG6JL8pnFvlUU1uSSBO";
        Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
        intent.putExtra("patientId",id);
        intent.putExtra("patientName",na);
        intent.putExtra("docId",docId);
        intent.putExtra("key","admin");
        startActivity(intent);
    }
}