package com.example.chattt.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattt.R;
import com.example.chattt.adaptadores.AdaptadorChat;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private AdaptadorChat chatAdapter;
    private List<DatosMensaje> chatMessages;
    private EditText chatInputEditText;
    private Button chatSendButton;
    private String userName;
    private int pos;
    DatosMensaje ultimoMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chtatecito);

        userName = getIntent().getStringExtra("nombreUsuario");
        getSupportActionBar().setTitle(userName);
        pos = getIntent().getIntExtra("posicion_contenedor", 0);

        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        chatInputEditText = findViewById(R.id.chat_input_edit_text);
        chatSendButton = findViewById(R.id.chat_send_button);

        chatMessages = new ArrayList<>();
        chatAdapter = new AdaptadorChat(this, chatMessages);
        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        // Cargar mensajes previos
        String previousMessages = MensajePreferences.loadMessages(this, userName);
        if (!previousMessages.isEmpty()) {
            String[] messagesArray = previousMessages.split(",");
            for (String message : messagesArray) {
                ultimoMensaje = new DatosMensaje(message, true);
                chatMessages.add(ultimoMensaje);
            }
            chatAdapter.notifyDataSetChanged();
            chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
        }

        chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatInputEditText.getText().toString();
                if (!message.isEmpty()) {
                    ultimoMensaje = new DatosMensaje(message, true);
                    chatMessages.add(ultimoMensaje);
                    chatAdapter.notifyDataSetChanged();
                    chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
                    chatInputEditText.setText("");

                    // Guardar mensajes al enviar uno nuevo
                    saveMessages();
                }
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent i = new Intent();
                i.putExtra("MENSAJE", ultimoMensaje.getText().toString());
                i.putExtra("POS", pos);
                setResult(RESULT_OK, i);
                finish();
            }
        };

        // Agregar el Callback al Dispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
    }




    private void saveMessages() {
        StringBuilder messagesStringBuilder = new StringBuilder();
        for (DatosMensaje chatMessage : chatMessages) {
            messagesStringBuilder.append(chatMessage.getText()).append(",");
        }

        MensajePreferences.saveMessages(this, userName, messagesStringBuilder.toString());
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
