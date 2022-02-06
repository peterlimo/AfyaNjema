package com.example.telemedicine.fragments_home_screen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.telemedicine.R;
import com.example.telemedicine.activities.LoginScreen;
import com.example.telemedicine.helpers.PreferenceHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class profile_fragment extends Fragment {
PreferenceHelper helper;
FirebaseFirestore db;
String name;
TextView tv_email,tv_name,tv_number,tv_location;
Button button;
CircleImageView imageView;
    public profile_fragment() {
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
       View view= inflater.inflate(R.layout.fragment_profile_fragment, container, false);
tv_email=view.findViewById(R.id.prof_email);
tv_name=view.findViewById(R.id.prof_name);
tv_location=view.findViewById(R.id.prof_location);
tv_number=view.findViewById(R.id.prof_number);
button=view.findViewById(R.id.logout_btn);
imageView=view.findViewById(R.id.prof_image);
button.setOnClickListener(view1 -> {
        SharedPreferences pref=getActivity().getSharedPreferences("person", Context.MODE_PRIVATE);
        pref.edit().clear().commit();
        Intent intent=new Intent(getActivity(), LoginScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    Toast.makeText(getActivity(), "Logged out successfully!!", Toast.LENGTH_SHORT).show();
});
       helper=new PreferenceHelper(getActivity());
       tv_name.setText(helper.getName());
       tv_location.setText(helper.getLocation());
       tv_number.setText(helper.getPhone());
       tv_email.setText(helper.getEmail());
        Picasso.get().load(helper.getUrl()).into(imageView);
//        db=FirebaseFirestore.getInstance();
//        db.collection("users")
//                .document(helper.getEmail())
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists())
//                        {
//name=documentSnapshot.get("fullName").toString();
//tv_name.setText(name);
//tv_email.setText(helper.getEmail());
////tv_number.setText(documentSnapshot.get("phone").toString());
////tv_location.setText(documentSnapshot.get("address").toString());
//                        }
//                        else{
//                            Toast.makeText(getActivity(), "No User is logged in", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to get User", Toast.LENGTH_SHORT).show());
       return view;
    }
}