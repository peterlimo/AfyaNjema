package com.example.telemedicine.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public Context toast(String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        return context;
    }
}
