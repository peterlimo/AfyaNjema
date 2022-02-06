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

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.ViewHolder> {
    private List<Doctor> UserList;
    private Context context;
    OnItemClickListener listener;
    public DoctorsAdapter(List<Doctor>UserList,Context context,OnItemClickListener listener){

        this.UserList=UserList;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.layout_doctor_card,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name=UserList.get(position).getName();
        String location=UserList.get(position).getLocation();

        holder.setData(name,location);

    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  tv_name,tv_location;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);

            tv_location=itemView.findViewById(R.id.tv_address);
itemView.setOnClickListener(this);
        }

        public void setData(String name,String location){
            tv_name.setText(name);
            tv_location.setText(location);

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
