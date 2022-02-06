package com.example.telemedicine.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telemedicine.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientDetailActivity extends AppCompatActivity {
TextView tv_name,tv_decease,tv_location,tv_desc;
Button btn_confirm,btn_cancel_request;
FirebaseFirestore db;
String rId,name,location,des,desc,dId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        tv_decease=findViewById(R.id.tv_desease_output);
        tv_name=findViewById(R.id.tv_name_output);
        tv_location=findViewById(R.id.tv_location_output);
        tv_desc=findViewById(R.id.tv_description_output);
        btn_cancel_request=findViewById(R.id.btn_cancel_request);
        btn_confirm=findViewById(R.id.btn_confirm);
        db=FirebaseFirestore.getInstance();
        rId=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        location=getIntent().getStringExtra("location");
        des=getIntent().getStringExtra("des");
        desc=getIntent().getStringExtra("desc");
        dId=getIntent().getStringExtra("dId");
        tv_name.setText(name);
        tv_desc.setText(desc);
        tv_location.setText(location);
        tv_decease.setText(des);
        btn_confirm.setOnClickListener(view ->
                db.collection("doctors")
                .document("xOG6JL8pnFvlUU1uSSBO")
                .collection("requests")
                .document("98CMrA0a0mm9zUf2x7sZ")
                .update("isConfirmed","yes")
                .addOnSuccessListener(aVoid -> Toast.makeText(PatientDetailActivity.this, "Request Approved!", Toast.LENGTH_SHORT).show()));
        btn_cancel_request.setOnClickListener(view ->
                db.collection("doctors")
                .document("xOG6JL8pnFvlUU1uSSBO")
                        .collection("requests")
                .document("98CMrA0a0mm9zUf2x7sZ")
                .update("isConfirmed","x")
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(PatientDetailActivity.this, "Request Canceled!", Toast.LENGTH_SHORT).show()));
    }
}