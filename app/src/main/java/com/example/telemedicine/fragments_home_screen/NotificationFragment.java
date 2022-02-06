package com.example.telemedicine.fragments_home_screen;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.telemedicine.Interfaces.OnGetDoctorListener;
import com.example.telemedicine.R;
import com.example.telemedicine.helpers.Base64Handler;
import com.example.telemedicine.helpers.HttpRequestSender;
import com.example.telemedicine.helpers.PreferenceHelper;
import com.example.telemedicine.models.Doctor;
import com.example.telemedicine.models.UserConsultationRequest;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationFragment
        extends Fragment
//        implements OnGetDoctorListener
{
    private TextView userName, disease,address, description,approvement;
    ImageView imageView;
    private CircleImageView avatar;
    private TextView doctorName, specialty, rating;
    private ArrayList<ImageView> stars = new ArrayList<>();
    private HttpRequestSender httpRequestSender;
    String d_name,des,app;
    PreferenceHelper helper;
    public NotificationFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=this.getArguments();
        if (bundle!=null)
        {
            d_name=bundle.getString("name");
            des=bundle.getString("des");
            app=bundle.getString("ap");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        helper=new PreferenceHelper(getContext());
        userName = view.findViewById(R.id.tv_name_output);
        disease = view.findViewById(R.id.tv_desease_output);
        address = view.findViewById(R.id.tv_location_output);
        description = view.findViewById(R.id.tv_description_output);
        avatar = view.findViewById(R.id.civ_avatar);
        doctorName = view.findViewById(R.id.tv_name);
        specialty = view.findViewById(R.id.tv_specialty);
        rating = view.findViewById(R.id.tv_rating);
        approvement=view.findViewById(R.id.tv_approvement);
        imageView=view.findViewById(R.id.iv_approve);
        stars.add(view.findViewById(R.id.iv_star0));
        stars.add(view.findViewById(R.id.iv_star1));
        stars.add( view.findViewById(R.id.iv_star2));
        stars.add(view.findViewById(R.id.iv_star3));
        stars.add(view.findViewById(R.id.iv_star4));

        httpRequestSender = new HttpRequestSender();
       // httpRequestSender.setOnGetDoctorListener(this);
if (app.equals("Request pending"))
{
    imageView.setImageResource(R.drawable.red_face_emoji);
    approvement.setText("Your request has not been approved!");
}
else
{
    imageView.setImageResource(R.drawable.ic_approve);
    approvement.setText("Your request has been approved!");
}
        setFields();

        return view;
    }

    private void setFields()
    {

        userName.setText(helper.getName());
        disease.setText(des);
        address.setText(helper.getLocation());
        description.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque sodales consectetur augue ac aliquet. Sed luctus libero vel augue commodo gravida. Mauris vestibulum diam a dignissim sodales. Fusce sodales dolor vel condimentum consectetur. Maecenas commodo congue mauris, ac blandit augue commodo ac. Maecenas rhoncus euismod est, in condimentum odio posuere in. Proin velit mauris, suscipit eu erat eget, egestas fermentum elit. Praesent molestie pulvinar sem, ac commodo risus malesuada a. Donec mattis orci et augue cursus, vitae convallis risus ornare. Nullam vel risus at dolor fermentum tincidunt et at leo. Nunc tristique, odio in hendrerit interdum, sapien nisi ullamcorper ante, eu maximus risus libero sit amet metus. Praesent vestibulum congue arcu id iaculis. Nam ac diam et metus ultricies tristique at sed massa. Integer nec massa nec tellus aliquet auctor");
    }

//    private void setStars(Doctor doctor)
//    {
//        float float_rating = doctor.getRating();
//        int rating = (int) Math.ceil(float_rating);
//        for (int i = 0; i < 5; i++)
//        {
//            stars.get(i).setVisibility(View.INVISIBLE);
//        }
//        for (int i = 0; i < rating; i++)
//        {
//            stars.get(i).setVisibility(View.VISIBLE);
//        }
//    }

//    @Override
//    public void onGetDoctorSuccess(Doctor doctor)
//    {
//        doctorName.setText(doctor.getName());
//        specialty.setText(doctor.getSpecialty());
//        rating.setText(String.valueOf(doctor.getRating()));
//        setStars(doctor);
//        Base64Handler base64Handler = new Base64Handler();
//        avatar.setImageBitmap(base64Handler.
//        base64ToBitmap(doctor.getBase64photo()));
//    }
}
