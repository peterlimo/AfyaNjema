package com.example.telemedicine.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telemedicine.Interfaces.OnLoginListener;
import com.example.telemedicine.R;
import com.example.telemedicine.admin.AdminDash;
import com.example.telemedicine.helpers.FirebaseHelper;
import com.example.telemedicine.helpers.HttpRequestSender;
import com.example.telemedicine.helpers.PreferenceHelper;
import com.example.telemedicine.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginScreen extends AppCompatActivity
        implements View.OnClickListener
{
FirebaseHelper helper;
    private Button loginBtn, signUpBtn,btn_admin;
    private EditText email, password;
    private HttpRequestSender httpRequestSender;
FirebaseFirestore db;
ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    helper=new FirebaseHelper(this);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.btn_login);
        signUpBtn = findViewById(R.id.btn_sign_up);
        btn_admin=findViewById(R.id.btn_admin);
        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminDash.class));
            }
        });
db=FirebaseFirestore.getInstance();
        loginBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
dialog=new ProgressDialog(this);
//        httpRequestSender = new HttpRequestSender();
//        httpRequestSender.setOnLoginListener(this);
    }

    private void getFilledFields()
    {
        User.setEmail(email.getText().toString());
        User.setPassword(password.getText().toString());
//        User.setEmail("1test@mail.ru");
//        User.setPassword("1test");
    }

    private void goToHomeScreen()
    {
        Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finishAffinity();
    }

    private void goToSignupScreen()
    {
        Intent intent = new Intent(LoginScreen.this, SignupScreen.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case (R.id.btn_login):
                String em=email.getText().toString();
                String passs=password.getText().toString();
                dialog.show();
                dialog.setMessage("Verifying....");
                dialog.setTitle("PLEASE WAIT!");
                db.collection("users")
                        .document(em)
                        .get().addOnSuccessListener(documentSnapshot -> {
                          if (documentSnapshot.exists())
                          {
                              String Opassword=documentSnapshot.get("password").toString();
                              if (passs==Opassword||passs.equals(Opassword))
                              {
                                  Toast.makeText(LoginScreen.this, "Login successful!!", Toast.LENGTH_SHORT).show();
                                  String name=documentSnapshot.get("fullName").toString();
                                  String location=documentSnapshot.get("address").toString();
                                  String phone=documentSnapshot.get("phone").toString();
                                  String url=documentSnapshot.get("url").toString();
                                  dialog.dismiss();
                                  PreferenceHelper helper=new PreferenceHelper(this);
                                  helper.saveValues(name,em,location,phone,url);
                                  Intent intent=new Intent(this,HomeScreen.class);
                                  startActivity(intent);
                              }
                              else
                              {
                                  dialog.dismiss();
                                  Toast.makeText(LoginScreen.this, "wrong password!!", Toast.LENGTH_SHORT).show();
                              }

                          }
                      else
                          {
                              dialog.dismiss();
                              Toast.makeText(LoginScreen.this, "Doc does not exists!!", Toast.LENGTH_SHORT).show();
                          }
                        });
//                getFilledFields();
//                helper.loginUser("");
//                httpRequestSender.auth(this);
//                helper.loginUser(email.getText().toString().trim(),password.getText().toString());
//                startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                break;
            case (R.id.btn_sign_up):
                goToSignupScreen();
                break;
        }
    }

//    @Override
//    public void onLoginSuccess()
//    {
//        goToHomeScreen();
//    }
//
//    @Override
//    public void onLoginFailure()
//    {
//        Toast.makeText(this,
//                "An error occurred",
//                Toast.LENGTH_SHORT).show();
//    }

}
