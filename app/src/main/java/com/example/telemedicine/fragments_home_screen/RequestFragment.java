package com.example.telemedicine.fragments_home_screen;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.telemedicine.Interfaces.IRequestFragment;
import com.example.telemedicine.Interfaces.OnUserConsultationRequestListener;
import com.example.telemedicine.R;
import com.example.telemedicine.data.Consult;
import com.example.telemedicine.helpers.HttpRequestSender;
import com.example.telemedicine.helpers.PreferenceHelper;
import com.example.telemedicine.models.UserConsultationRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class RequestFragment extends Fragment implements View.OnClickListener
{
    private EditText name, disease, address, description;
    private Button request;
    private HttpRequestSender httpRequestSender;
    private IRequestFragment iRequestFragment = null;
    String id,d_name;
    FirebaseFirestore db;
    PreferenceHelper helper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=this.getArguments();
        if (bundle!=null)
        {
            id=bundle.getString("id");
            d_name=bundle.getString("d_name");
        }

    }

    public RequestFragment()
    {
        // Required empty public constructor
    }

    public void setIRequestFragment(IRequestFragment iRequestFragment)
    {
        this.iRequestFragment = iRequestFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        helper=new PreferenceHelper(getContext());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        name = view.findViewById(R.id.et_name);
        disease = view.findViewById(R.id.et_desease);
        address = view.findViewById(R.id.et_location);
        description = view.findViewById(R.id.et_description);
        request = view.findViewById(R.id.btn_request);

        name.setText(helper.getName());
        address.setText(helper.getLocation());
        request.setOnClickListener(this);
        db=FirebaseFirestore.getInstance();
        httpRequestSender = new HttpRequestSender();
//        httpRequestSender.setConsultationRequestListener(this);

        return view;
    }
/*
    private void getFilledFields()
    {

        UserConsultationRequest.setConsId(1);
        UserConsultationRequest.setName("1test");
        UserConsultationRequest.setDisease("vederea");
        UserConsultationRequest.setAddress("1test");
        UserConsultationRequest.setDescription("1test");
        UserConsultationRequest.setName(name.getText().toString());
        UserConsultationRequest.setDisease(disease.getText().toString());
        UserConsultationRequest.setAddress(address.getText().toString());
        UserConsultationRequest.setDescription(description.getText().toString());
        UserConsultationRequest.setDocId(1);
        UserConsultationRequest.setIsConfirmed(false);

    }
*/
    @Override
    public void onClick(View v)
    {
/*
        getFilledFields();
        httpRequestSender.userRequestConsultation(getActivity());
*/
        PreferenceHelper helper=new PreferenceHelper(getContext());
        if (id!=null)
        {


        Consult con=new Consult(helper.getEmail(),name.getText().toString(),disease.getText().toString(),address.getText().toString(),description.getText().toString(),id,d_name,"no","");

               db.collection("doctors").document(id)
                       .collection("requests")
                       .document()
                       .set(con)
                       .addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "Request sent Successfully!", Toast.LENGTH_SHORT).show())
                       .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to deliver request!", Toast.LENGTH_SHORT).show());
    }
        else
        {
            Consult con=new Consult(helper.getEmail(),name.getText().toString(),disease.getText().toString(),address.getText().toString(),description.getText().toString(),"NULL","Any Available Doctor","no","");

            db.collection("requests")
                    .document()
                    .set(con)
                    .addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "Request sent Successfully!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to deliver request!", Toast.LENGTH_SHORT).show());
        }
    }
/*

    @Override
    public void onUserConsultationRequestSuccess()
    {
        Toast.makeText(getActivity(),
                "request successful",
                Toast.LENGTH_SHORT).show();
        if (iRequestFragment != null)
            iRequestFragment.onSuccessfulRequest();
    }

    @Override
    public void onUserConsultationRequestFailure()
    {
        Toast.makeText(getActivity(),
                "An error occurred",
                Toast.LENGTH_SHORT).show();
    }
*/
}
