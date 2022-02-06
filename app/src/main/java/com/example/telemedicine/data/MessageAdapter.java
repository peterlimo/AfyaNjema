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

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> UserList;
    private Context context;
//    OnItemClickListener listener;
    public MessageAdapter(List<Message>UserList,Context context){
        this.UserList=UserList;
        this.context=context;
//        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater=LayoutInflater.from(context);
        if (viewType==1) {
             view = inflater.inflate(R.layout.msg_item_send, parent, false);
        }
        else
        {
            view=inflater.inflate(R.layout.msg_item_receied, parent, false);
        }
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String message=UserList.get(position).getMessage();

        holder.setData(message);
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  tv_message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_message=itemView.findViewById(R.id.message);
            itemView.setOnClickListener(this);
        }

        public void setData(String message){
             tv_message.setText(message);
        }

        @Override
        public void onClick(View view) {
//            listener.onItemClick(getAdapterPosition(),view);
        }
//        public void SetImage(String image){
//            ImageView imageView=itemView.findViewById(R.id.imageview);
//            Picasso.get().load(image).into(imageView);
//        }
    }
//    public interface OnItemClickListener{
//        void onItemClick(int position, View v);
//    }

    @Override
    public int getItemViewType(int position) {
        if (UserList.get(position).getType() == 1)
            {
            return 1;
            }
        else
            {
            return 0;
            }
    }
}

