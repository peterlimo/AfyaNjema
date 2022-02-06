package com.example.telemedicine.fragments_home_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemedicine.R;
import com.example.telemedicine.data.Consult;
import com.example.telemedicine.data.NotificationsAdapter;
import com.example.telemedicine.helpers.PreferenceHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class NotifiCationList extends Fragment implements NotificationsAdapter.OnItemClickListener {

RecyclerView recyclerView;
NotificationsAdapter adapter;
FirebaseFirestore db;
String confirm;
LinearLayoutManager linearLayoutManager;
List<Consult> list=new ArrayList<>();
    public NotifiCationList() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notifi_cation_list, container, false);
        db=FirebaseFirestore.getInstance();
        recyclerView=view.findViewById(R.id.not_recycler);
        adapter=new NotificationsAdapter(list,getContext(),this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        list.clear();
        getData();
        return view;
    }


    private void getData() {
        PreferenceHelper helper =new PreferenceHelper(getContext());
        db.collectionGroup("requests")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc:queryDocumentSnapshots) {

                        String email = doc.get("email").toString();
                        if (email == helper.getEmail() || email.equals(helper.getEmail())) {
                            String name = doc.get("docName").toString();
                            String isCon = doc.get("confirmed").toString();
                            String desease = doc.get("disease").toString();
                            String id=doc.get("reqId").toString();
                            String desc=doc.get("description").toString();
                            if (isCon == "no" || isCon.equals("no")) {
                                confirm = "Request pending";
                            } else if (isCon == "yes" || isCon.equals("yes")){
                                confirm = "Approved";
                            }
                            else {
                                confirm = "Rejected";
                            }
                            Consult doctor = new Consult("", name, desease, "", desc, "", name, confirm,id);

                            list.add(doctor);
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to load notifications!", Toast.LENGTH_SHORT).show());

//
//                .whereEqualTo(helper.getEmail(),"email")
//                .addSnapshotListener((value, error) -> {
//                    if (error!=null)
//                    {
//                        return;
//                    }
//                    if (value.isEmpty()){
//                        Toast.makeText(getContext(), "No Requests made!!", Toast.LENGTH_LONG).show();
//                    }
//
//                });


    }
    @Override
    public void onItemDetailsButtonClick(int position, View v) {
        Consult item=list.get(position);
        String name=item.getName();
        String des=item.getDisease();
        String isPP=item.isConfirmed();
        Bundle bundle=new Bundle();
        bundle.putString("name",name);
        bundle.putString("des",des);
        bundle.putString("ap",isPP);
        NotificationFragment nextFrag= new NotificationFragment();
        nextFrag.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }
}