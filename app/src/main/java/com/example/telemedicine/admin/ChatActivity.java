package com.example.telemedicine.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.telemedicine.R;
import com.example.telemedicine.data.Det;
import com.example.telemedicine.data.Message;
import com.example.telemedicine.data.MessageAdapter;
import com.example.telemedicine.helpers.PreferenceHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
RecyclerView recyclerView;
MessageAdapter adapter;
List<Message> list=new ArrayList<>();
   public String docId="xOG6JL8pnFvlUU1uSSBO";
   public EditText message;
    Button send;
    FirebaseFirestore db;
    String sender;
    String receiver;
    String key,id;
    String receiverId,receiverName,senderId,senderName;
    PreferenceHelper helper;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        helper=new PreferenceHelper(this);
        key=getIntent().getStringExtra("key");

        if (key=="admin"||key.equals("admin"))
        {
            receiverId=getIntent().getStringExtra("patientId");
            receiverName=getIntent().getStringExtra("patientName");
            sender="admin";
            receiver=helper.getEmail();
        }
        else
        {
            receiverId=getIntent().getStringExtra("docId");
            receiverName=getIntent().getStringExtra("docName");
            sender=helper.getEmail();
            receiver="admin";

        }

        adapter=new MessageAdapter(list,this);
        recyclerView=findViewById(R.id.message_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        send=findViewById(R.id.send_message);
        message=findViewById(R.id.message_txt);
//        send.setOnClickListener(view -> sendMessage());

        if (key=="admin"||key.equals("admin"))
        {
            getMessagesFromPatient();
        }
        else if (key=="patient"||key.equals("patient"))
        {
            getMessagesFromDoctor();
        }
        else {
            Toast.makeText(this, "KEY DOES NOT EXIST, PLEASE RELAUNCH THE APP!", Toast.LENGTH_SHORT).show();
        }
        send.setOnClickListener(view -> {
            if (key=="admin"||key.equals("admin"))
            {
                sendToPatient();
            }
            else  if (key=="patient"||key.equals("patient"))
            {
                sendToDoctor();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "KEY DOES NOT EXIST, PLEASE RELAUNCH THE APP!", Toast.LENGTH_SHORT).show();
            }
        });
   

    }

    private void getMessagesFromDoctor() {
        db.collection("doctors").document(receiverId)
                .collection("inbox")
                .document(helper.getEmail())
                .collection("messages")
                .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener((value, error) -> {
            if (error!=null)
            {
                return;
            }
            list.clear();
            for (DocumentSnapshot doc:value)
            {
                if (doc.exists())
                {
                    String message=doc.get("message").toString();
                    String sen=doc.get("sender").toString();
                    String receiver=doc.get("receiver").toString();
                    if (receiver.equals("admin")||receiver=="admin")
                    {
                        type=1;
                    }
                    else {
                        type=2;
                    }

                    Message m=new Message(message,sen,null,type,receiver);
                    list.add(m);
                    adapter.notifyDataSetChanged();
                    adapter.notifyItemInserted(-1);
                }
            }
        });
    }

    private void getMessagesFromPatient() {
        db.collection("doctors").document(docId)
                .collection("inbox")
                .document(receiverId)
                .collection("messages")
                .orderBy("time", Query.Direction.ASCENDING)
               .addSnapshotListener((value, error) -> {
            if (error!=null)
            {
                return;
            }
            list.clear();
            for (DocumentSnapshot doc:value)
            {
                if (doc.exists())
                {
                    String message=doc.get("message").toString();
                    String sen=doc.get("sender").toString();
                    String receiver=doc.get("receiver").toString();
                    if (receiver.equals("admin")||receiver=="admin")
                    {
                        type=2;
                    }
                    else if (receiver.equals("patient")||receiver=="patient")
                    {
                        type=1;
                    }
                    Message m=new Message(message,sender,null,type,receiver);
                    list.add(m);
                    adapter.notifyDataSetChanged();
                    adapter.notifyItemInserted(-1);
                }
            }
        });
    }

    private void sendToPatient() {
        String messag=message.getText().toString();
        Message mess=new Message(messag,sender,null,1,"patient");
        db.collection("doctors").document(docId)
                .collection("inbox")
                .document(receiverId)
                .collection("messages")
                .document()
                .set(mess)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ChatActivity.this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void sendToDoctor() {
        com.example.telemedicine.data.List desc=new com.example.telemedicine.data.List(helper.getName(),helper.getEmail());
        String messag=message.getText().toString();
        Message mess=new Message(messag,sender,null,1,"admin");
        db.collection("doctors").document(receiverId)
                .collection("inbox")
                .document(helper.getEmail())
                .set(desc).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                db.collection("doctors").document(receiverId)
                        .collection("inbox")
                        .document(helper.getEmail())
                        .collection("messages")
                        .document()
                        .set(mess)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChatActivity.this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChatActivity.this, "Message sending request failed!", Toast.LENGTH_LONG).show();  
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatActivity.this, "Message sending request failed!", Toast.LENGTH_LONG).show();   
            }
        });
  }

   /* private void getMessages() {
        db.collection("doctors").document(id)
                .collection("chats")
                .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener((value, error) -> {
                   if (error!=null)
                   {
                       return;
                   }
                   list.clear();
                   for (DocumentSnapshot doc:value)
                   {
                       if (doc.exists())
                       {
                           String message=doc.get("message").toString();
                           String sen=doc.get("sender").toString();
                           if (key!=helper.getEmail())
                           {
                               type=1;
                           }
                           else
                           {
                               type=2;
                           }
                           Message m=new Message(message,"",null,type);
                           list.add(m);
                           adapter.notifyDataSetChanged();
                           adapter.notifyItemInserted(-1);
                       }
                   }
                });
    }
    
    */
}