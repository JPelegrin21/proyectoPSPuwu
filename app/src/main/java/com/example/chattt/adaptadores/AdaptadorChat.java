package com.example.chattt.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattt.chat.DatosMensaje;
import com.example.chattt.R;

import java.util.List;

public class AdaptadorChat extends RecyclerView.Adapter<AdaptadorChat.ChatViewHolder> {

    private Context context;
    private List<DatosMensaje> chatMessages;

    public AdaptadorChat(Context context, List<DatosMensaje> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }

    //sin vergüenza´asdfhjklñ
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        DatosMensaje chatMessage = chatMessages.get(position);
        holder.chatTextView.setText(chatMessage.getText());
        if (chatMessage.isUser()) {
            // Si el mensaje es del usuario, alinea el texto a la derecha y cambia el color de fondo
            holder.chatTextView.setBackgroundResource(R.drawable.chat_user_bg);
            holder.chatTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else {
            // Si el mensaje es del asistente, alinea el texto a la izquierda y cambia el color de fondo
            holder.chatTextView.setBackgroundResource(R.drawable.chat_assistant_bg);
            holder.chatTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
    }


    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        private TextView chatTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            chatTextView = itemView.findViewById(R.id.chat_text_view);
        }
    }
}