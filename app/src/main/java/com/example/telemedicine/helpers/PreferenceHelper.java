package com.example.telemedicine.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceHelper {
    Context context;
    SharedPreferences pref;
    public PreferenceHelper(Context context) {
        this.context = context;
    }

    public void saveValues(String name,String email,String location,String phone,String url)
    {
      pref=context.getSharedPreferences("person",MODE_PRIVATE);
      SharedPreferences.Editor editor=pref.edit();
      editor.putString("name",name);
      editor.putString("email",email);
      editor.putString("phone",phone);
      editor.putString("location",location);
      editor.putString("url",url);
      editor.commit();
    }
    public String getName(){
        pref=context.getSharedPreferences("person", MODE_PRIVATE);
String name=pref.getString("name","");
return  name;
    }
    public String getEmail(){

        pref=context.getSharedPreferences("person", MODE_PRIVATE);
        String email=pref.getString("email","");
        return  email;
    }
    public String getLocation(){
        pref=context.getSharedPreferences("person", MODE_PRIVATE);
        String location=pref.getString("location","");
        return  location;
    }
    public String getPhone(){
        pref=context.getSharedPreferences("person", MODE_PRIVATE);
        String phone=pref.getString("phone","");
        return  phone;
    }
    public String getUrl(){
        pref=context.getSharedPreferences("person", MODE_PRIVATE);
        String url=pref.getString("url","");
        return  url;
    }
}
