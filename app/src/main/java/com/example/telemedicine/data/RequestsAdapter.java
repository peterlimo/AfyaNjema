package com.example.telemedicine.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.telemedicine.R;

import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {
    private List<Consult> UserList;
    private Context context;
    OnItemClickListener listener;
    public RequestsAdapter(List<Consult>UserList,Context context,OnItemClickListener listener){

        this.UserList=UserList;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.list_item_doc_requests,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name=UserList.get(position).getName();
        String desease=UserList.get(position).getDisease();
        holder.setData(name,desease);
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name,  tv_desease;
        Button not_details;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.not_name);
            tv_desease = itemView.findViewById(R.id.not_decease);
            not_details = itemView.findViewById(R.id.not_details);
            not_details.setOnClickListener(view -> listener.onItemDetailsButtonClick(getAdapterPosition(),view));
        }

        public void setData(String name, String deaseas) {
            tv_name.setText(name);
            tv_desease.setText(deaseas);
        }
    }

    public interface OnItemClickListener{
        void onItemDetailsButtonClick(int position, View v);
    }
}
