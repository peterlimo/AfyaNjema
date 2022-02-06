package com.example.telemedicine.helpers;

import android.content.Context;
import android.widget.Toast;
import com.example.telemedicine.data.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseHelper {
    Context context;
    FirebaseFirestore db;
    public FirebaseHelper(Context context) {
        this.context = context;
    }
    public void saveUser(User user,String email)
    {
        db=FirebaseFirestore.getInstance();
        db.collection("users")
                .document(email)
                .set(user)
                .addOnSuccessListener(aVoid -> Toast.makeText(context, "User registered Successfully!!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to register user!!", Toast.LENGTH_SHORT).show());
    }

   public void loginUser(String email,String password)
    {
        db=FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("email",email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot doc:queryDocumentSnapshots)
                        {
                            if (doc.exists())
                            {
                                String pass=doc.get("password")
                                        .toString();
                                if (password==pass)
                                {
                                    Toast.makeText(context, "User available!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });


//                .document(email).get()
//                .addOnSuccessListener(doc -> {
//
//                    if (doc.exists())
//                    {
//                        Toast.makeText(context, "Email Found", Toast.LENGTH_SHORT).show();
//                        String pass=doc.get("password").toString();
//                        if (password.equals(pass) || password==pass)
//                        {
//                            String name=doc.get("fullName").toString();
//                            PreferenceHelper helper=new PreferenceHelper(context);
//                            helper.saveValues(name,email);
//                            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show();
//                            Intent intent=new Intent(context, HomeScreen.class);
//                            context.startActivity(intent);
//                        }
//                        else{
//                            Toast.makeText(context, "Invalid password!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    else
//                    {
//                        Toast.makeText(context, "Email does not exist!", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(e -> {
//                    Toast.makeText(context, "Login request failed!", Toast.LENGTH_SHORT).show();
//                });
    }
    public  void getUser(String email){
        db=FirebaseFirestore.getInstance();
        db.collection("users")
                .document(email)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                    {

                    }
                });
    }
}
