package com.example.telemedicine.fragments_home_screen;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telemedicine.R;
import com.example.telemedicine.admin.ChatActivity;
import com.example.telemedicine.data.Request;
import com.example.telemedicine.helpers.Base64Handler;
import com.example.telemedicine.models.Doctor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorDetailsFragment extends Fragment
{
    private Doctor doctor = new Doctor();
    private CircleImageView avatar;
    private TextView name, specialty, rating, about, address;
    private ArrayList<ImageView> stars;
String d_name, location,spec,desc,id;
Button button,btn_chat;
FirebaseFirestore db;
    public DoctorDetailsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null)
        {

           id = bundle.getString("id");
             d_name=bundle.getString("name");
            location=bundle.getString("location");
            desc=bundle.getString("desc");
            spec=bundle.getString("spec");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_details, container, false);
        stars = new ArrayList<>();

        avatar = view.findViewById(R.id.civ_avatar);
        name = view.findViewById(R.id.tv_name);
        specialty = view.findViewById(R.id.tv_specialty);
        db=FirebaseFirestore.getInstance();
        stars.add(view.findViewById(R.id.iv_star0));
        stars.add(view.findViewById(R.id.iv_star1));
        stars.add(view.findViewById(R.id.iv_star2));
        stars.add(view.findViewById(R.id.iv_star3));
        stars.add(view.findViewById(R.id.iv_star4));

        rating = view.findViewById(R.id.tv_rating);
        about = view.findViewById(R.id.tv_description_output);
        address = view.findViewById(R.id.tv_location_output);
        button=view.findViewById(R.id.btn_confirm);
        btn_chat=view.findViewById(R.id.btn_chat);
        btn_chat.setOnClickListener(view1 -> {
            Intent intent=new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("docId",id);
            intent.putExtra("docName","");
            intent.putExtra("key","patient");
            startActivity(intent);
        });
        button.setOnClickListener(view12 -> {
            Bundle bundle=new Bundle();

            bundle.putString("id",id);
            bundle.putString("d_name",d_name);
            RequestFragment nextFrag= new RequestFragment();
            nextFrag.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
//                Request request=new Request("peter Limo");
//               db.collection("doctors").document(id)
//                       .collection("requests")
//                       .document()
//                       .set(request)
//                       .addOnSuccessListener(new OnSuccessListener<Void>() {
//                           @Override
//                           public void onSuccess(Void aVoid) {
//                               Toast.makeText(getActivity(), "Request sent Successfully!", Toast.LENGTH_SHORT).show();
//                           }
//                       })
//                       .addOnFailureListener(new OnFailureListener() {
//                           @Override
//                           public void onFailure(@NonNull Exception e) {
//                               Toast.makeText(getActivity(), "Failed to deliver request!", Toast.LENGTH_SHORT).show();
//
//                           }
//                       });
        });
        setFields();

        return view;
    }

    private void setStars()
    {
//        float float_rating = doctor.getRating();
//        int rating = (int) Math.ceil(float_rating);
//        for (int i = 0; i < 5; i++){
//            stars.get(i).setVisibility(View.INVISIBLE);
//        }
//        for(int i = 0; i < rating; i++){
//            stars.get(i).setVisibility(View.VISIBLE);
//        }
    }

    private void setFields()
    {
       name.setText(d_name);
        specialty.setText(spec);
//        rating.setText(String.valueOf(doctor.getRating()));
        about.setText(desc);
        address.setText(location);
//        setStars();
//        Base64Handler base64Handler = new Base64Handler();
//        avatar.setImageBitmap(base64Handler.
//                base64ToBitmap(doctor.getBase64photo()));
    }
}
