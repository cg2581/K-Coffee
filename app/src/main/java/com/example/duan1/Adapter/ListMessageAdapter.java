package com.example.duan1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Activity.MessageChatActivity;
import com.example.duan1.Model.MessageChatModel;
import com.example.duan1.R;

import java.util.ArrayList;

public class ListMessageAdapter extends RecyclerView.Adapter<ListMessageAdapter.ViewHolder> {

    Context context;
    ArrayList<MessageChatModel> listMessage;
    public static String ID = null;

    public ListMessageAdapter(Context context, ArrayList<MessageChatModel> listMessage) {
        this.context = context;
        this.listMessage = listMessage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageChatModel messageChatModel = listMessage.get(position);
        int i = 0;
        if(listMessage != null){
            while (i<listMessage.size()){
                holder.imgPerson.setBackgroundResource(R.drawable.ic_person1);
                holder.txtFullNameMessage.setText("Tin nhắn hỗ trợ số #"+i+"KF");
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPerson;
        TextView txtFullNameMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPerson = itemView.findViewById(R.id.imgPerson);
            txtFullNameMessage = itemView.findViewById(R.id.txtFullNameMessage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            startProductDetailActivity(listMessage.get(getAdapterPosition()));
        }
    }
    private void startProductDetailActivity(MessageChatModel messageChatModel) {
        Intent intent = new Intent(context, MessageChatActivity.class);
        intent.putExtra("id", messageChatModel.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
