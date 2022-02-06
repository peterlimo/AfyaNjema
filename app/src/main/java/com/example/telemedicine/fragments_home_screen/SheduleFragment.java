package com.example.telemedicine.fragments_home_screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.telemedicine.Interfaces.IHomeFragment;
import com.example.telemedicine.R;
import com.example.telemedicine.admin.ChatActivity;
import com.example.telemedicine.data.Doctor;
import com.example.telemedicine.data.DoctorsAdapter;
import com.example.telemedicine.helpers.HomeRecyclerViewAdapter;
import com.example.telemedicine.helpers.HttpRequestSender;
import com.example.telemedicine.helpers.PreferenceHelper;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class SheduleFragment extends Fragment implements DoctorsAdapter.OnItemClickListener{
PreferenceHelper helper;
FirebaseFirestore db;
    DoctorsAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private HomeRecyclerViewAdapter recyclerViewAdapter;
    private IHomeFragment iHomeFragment;
    private HttpRequestSender httpRequestSender;
    List<Doctor> list=new ArrayList<>();

    public SheduleFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_shedule, container, false);
        db=FirebaseFirestore.getInstance();
        helper=new PreferenceHelper(getActivity());
        recyclerView = view.findViewById(R.id.rv_doctor_list);
        adapter=new DoctorsAdapter(list,getContext(),this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        list.clear();
        getData();
        return view;
    }
    private void getData() {

        db.collection("doctors").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc:queryDocumentSnapshots)
                    {
                        String name=doc.get("name").toString();
                        String location=doc.get("location").toString();
                        String desc=doc.get("description").toString();
                        String spec=doc.get("specialization").toString();
                        String email=doc.get("email").toString();
                        String id=doc.getId();
                        com.example.telemedicine.data.Doctor doctor=new com.example.telemedicine.data.Doctor(id,name,location,desc,spec,email);

                        list.add(doctor);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to get doctors", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onItemClick(int position, View v) {
        Doctor item=list.get(position);
        String id=item.getId();
        String na=item.getName();
        Intent intent=new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("docId",id);
        intent.putExtra("docName",na);
        intent.putExtra("key","patient");
        startActivity(intent);
    }
}