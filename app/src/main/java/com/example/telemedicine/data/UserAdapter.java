package com.example.telemedicine.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.telemedicine.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<com.example.telemedicine.data.List> UserList;
    private Context context;
    OnItemClickListener listener;
    public UserAdapter(List<com.example.telemedicine.data.List>UserList, Context context, OnItemClickListener listener){

        this.UserList=UserList;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.list_item_user,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name=UserList.get(position).getName();

        holder.setData(name);

    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  tv_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_user);

            itemView.setOnClickListener(this);
        }

        public void setData(String name){
            tv_name.setText(name);


        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition(),view);
        }
//        public void SetImage(String image){
//            ImageView imageView=itemView.findViewById(R.id.imageview);
//            Picasso.get().load(image).into(imageView);
//        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position, View v);
    }
}
