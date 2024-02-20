package com.example.chattt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private AdaptadorChat chatAdapter;
    private List<ChatMessage> chatMessages;
    private EditText chatInputEditText;
    private Button chatSendButton;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chtatecito);

        userName = getIntent().getStringExtra("nombreUsuario");

        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        chatInputEditText = findViewById(R.id.chat_input_edit_text);
        chatSendButton = findViewById(R.id.chat_send_button);

        chatMessages = new ArrayList<>();
        chatAdapter = new AdaptadorChat(this, chatMessages);
        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cargar mensajes previos
        String previousMessages = MessagePreferences.loadMessages(this, userName);
        if (!previousMessages.isEmpty()) {
            String[] messagesArray = previousMessages.split(",");
            for (String message : messagesArray) {
                chatMessages.add(new ChatMessage(message, true));
            }
            chatAdapter.notifyDataSetChanged();
            chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
        }

        chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatInputEditText.getText().toString();
                if (!message.isEmpty()) {
                    chatMessages.add(new ChatMessage(message, true));
                    chatAdapter.notifyDataSetChanged();
                    chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
                    chatInputEditText.setText("");

                    // Guardar mensajes al enviar uno nuevo
                    saveMessages();
                }
            }
        });
    }

    private void saveMessages() {
        StringBuilder messagesStringBuilder = new StringBuilder();
        for (ChatMessage chatMessage : chatMessages) {
            messagesStringBuilder.append(chatMessage.getText()).append(",");
        }

        MessagePreferences.saveMessages(this, userName, messagesStringBuilder.toString());
    }
}

/*
public class MainActivityChtatecito extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;
    private EditText chatInputEditText;
    private Button chatSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chtatecito);

        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");

        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        chatInputEditText = findViewById(R.id.chat_input_edit_text);
        chatSendButton = findViewById(R.id.chat_send_button);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatMessages);
        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatInputEditText.getText().toString();
                if (!message.isEmpty()) {
                    chatMessages.add(new ChatMessage(message, true));
                    chatAdapter.notifyDataSetChanged();
                    chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
                    chatInputEditText.setText("");
                }
            }
        });
    }
}
* */
