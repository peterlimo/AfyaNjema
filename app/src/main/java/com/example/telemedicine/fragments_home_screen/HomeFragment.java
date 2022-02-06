package com.example.telemedicine.fragments_home_screen;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemedicine.Interfaces.CardOnClickListener;
import com.example.telemedicine.Interfaces.IHomeFragment;
import com.example.telemedicine.Interfaces.OnGetDocListListener;
import com.example.telemedicine.R;
import com.example.telemedicine.data.Doctor;
import com.example.telemedicine.data.DoctorsAdapter;
import com.example.telemedicine.helpers.HomeRecyclerViewAdapter;
import com.example.telemedicine.helpers.HttpRequestSender;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firestore.bundle.BundledQueryOrBuilder;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment
        extends Fragment implements DoctorsAdapter.OnItemClickListener

{
FirebaseFirestore db;
DoctorsAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private HomeRecyclerViewAdapter recyclerViewAdapter;
    private IHomeFragment iHomeFragment;
    private HttpRequestSender httpRequestSender;
    List<com.example.telemedicine.data.Doctor> list=new ArrayList<>();
    public HomeFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d("log", "HomeFragment: onCreateView");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.rv_doctor_list);

        httpRequestSender = new HttpRequestSender();
       // httpRequestSender.setOnGetDocListListener(this);

        httpRequestSender.getDocList(getActivity());
        db= FirebaseFirestore.getInstance();
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

//    @Override
//    public void onClick(Doctor doctor)
//    {
//        if (iHomeFragment != null)
//        {
//            iHomeFragment.onCardClick(doctor);
//        }
//    }

    public void setIHomeFragment(IHomeFragment iHomeFragment)
    {
        this.iHomeFragment = iHomeFragment;
    }

    @Override
    public void onItemClick(int position, View v) {
        Doctor item=list.get(position);
        String name=item.getName();
        String id=item.getId();
        String location=item.getLocation();
        String spec=item.getSpecialization();
        String desc=item.getDescription();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);

        bundle.putString("name",name);
        bundle.putString("location",location);
        bundle.putString("desc",desc);
        bundle.putString("spec",spec);
        DoctorDetailsFragment nextFrag= new DoctorDetailsFragment();
        nextFrag.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();

    }

//    @Override
//    public void onGetDocListSuccess(ArrayList<Doctor> doctors)
//    {
//        if (recyclerViewAdapter == null)
//        {
//            Log.d("log", "initRecyclerView: init recyclerView.");
//            linearLayoutManager = new LinearLayoutManager(getActivity());
//            recyclerView.setLayoutManager(linearLayoutManager);
//            recyclerViewAdapter = new HomeRecyclerViewAdapter(getActivity(), doctors);
//            recyclerViewAdapter.setCardOnClickListener(this);
//            recyclerView.setAdapter(recyclerViewAdapter);
//        }
//    }
}
