package com.example.telemedicine.data;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Message {
    String message,sender,receiver;
    @ServerTimestamp
    Timestamp time=null;
    int type;

    public Message(String message, String sender, Timestamp time, int type,String receiver) {
        this.message = message;
        this.sender = sender;
        this.time = time;
        this.type = type;
        this.receiver=receiver;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
