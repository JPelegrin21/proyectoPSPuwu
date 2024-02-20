package com.example.chattt;

public class ChatMessage {

    private String text;
    private boolean isUser; // true si el mensaje es del usuario, false si es del asistente

    public ChatMessage(String text, boolean isUser) {
        this.text = text;
        this.isUser = isUser;
    }

    public String getText() {
        return text;
    }

    public boolean isUser() {
        return isUser;
    }
}