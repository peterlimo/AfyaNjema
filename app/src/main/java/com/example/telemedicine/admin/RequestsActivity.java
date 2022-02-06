package com.example.telemedicine.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.telemedicine.R;
import com.example.telemedicine.activities.PatientDetailActivity;
import com.example.telemedicine.data.Consult;
import com.example.telemedicine.data.RequestsAdapter;
import com.example.telemedicine.helpers.PreferenceHelper;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;


public class RequestsActivity extends AppCompatActivity implements RequestsAdapter.OnItemClickListener {
String key;
FirebaseFirestore db;
String docId="xOG6JL8pnFvlUU1uSSBO";
RequestsAdapter adapter;
List<Consult> list=new ArrayList<>();
RecyclerView recyclerView;
PreferenceHelper helper;
String confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        key=getIntent().getStringExtra("key");
        db=FirebaseFirestore.getInstance();
        adapter=new RequestsAdapter(list,this,this);
        recyclerView=findViewById(R.id.req_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        helper=new PreferenceHelper(this);
        list.clear();
        if (key.equals("all")||key=="all")
        {
            getAllRequests();
        }
        else
        {
            getMyRequests();
        }
    }

    private void getAllRequests() {
        db.collection("requests")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
for (DocumentSnapshot doc:queryDocumentSnapshots) {
    if (doc.exists()) {
        String email = doc.get("email").toString();
        if (email == helper.getEmail() || email.equals(helper.getEmail())) {
            String name = doc.get("docName").toString();
            String isCon = doc.get("confirmed").toString();
            String desease = doc.get("disease").toString();
            String d_id=doc.get("docId").toString().trim();
            String id=doc.get("reqId").toString();
            String desc=doc.get("description").toString();
            String docName=doc.get("docName").toString();
            String loc=doc.get("address").toString();
            String emil=doc.get("email").toString();
            if (isCon == "no" || isCon.equals("no")) {
                confirm = "Request pending";
            } else {
                confirm = "Approved";
            }
            Consult doctor = new Consult(emil, name, desease, loc, desc, d_id, docName, confirm,id);

            list.add(doctor);

            adapter.notifyDataSetChanged();
        }
    }
    else {
        Toast.makeText(this, "Doc does not exist", Toast.LENGTH_SHORT).show();
    }
}
});

        }
    private void getMyRequests() {
        db.collection("doctors")
                .document(docId)
                .collection("requests")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc:queryDocumentSnapshots)
                    {
                        if (doc.exists())
                        {
                        String email = doc.get("email").toString();
                        if (email == helper.getEmail() || email.equals(helper.getEmail()))
                        {
                            String name = doc.get("docName").toString();
                            String isCon = doc.get("confirmed").toString();
                            String desease = doc.get("disease").toString();
                            String d_id=doc.get("docId").toString().trim();
                            String id=doc.get("reqId").toString();
                            String desc=doc.get("description").toString();
                            String docName=doc.get("docName").toString();
                            String loc=doc.get("address").toString();
                            String emil=doc.get("email").toString();
                            if (isCon == "no" || isCon.equals("no"))
                            {
                                confirm = "Request pending";
                            }
                            else
                            {
                                confirm = "Approved";
                            }
                            Consult doctor = new Consult(emil, name, desease, loc, desc, d_id, docName, confirm,id);
                            list.add(doctor);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else
                        {
                            Toast.makeText(this, "Doc does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

                }



    @Override
    public void onItemDetailsButtonClick(int position, View v) {
//        rId=getIntent().getStringExtra("id");
//        name=getIntent().getStringExtra("name");
//        location=getIntent().getStringExtra("location");
//        des=getIntent().getStringExtra("des");
//        desc=getIntent().getStringExtra("desc");
//        dId=getIntent().getStringExtra("dId");
        Consult item=list.get(position);
        String rId=item.getReqId();
        String name=item.getName();
        String location=item.getAddress();
        String des=item.getDisease();
        String desc=item.getDescription();
        String dId=item.getDocId();
        Toast.makeText(this, "Request id:"+"/n"+rId+"/n"+"Doctor id"+"/n"+dId, Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getApplicationContext(), PatientDetailActivity.class);
        intent.putExtra("id",rId);
        intent.putExtra("name",name);
        intent.putExtra("location",location);
        intent.putExtra("des",des);
        intent.putExtra("desc",desc);
        intent.putExtra("dId",dId);
        startActivity(intent);
    }
}