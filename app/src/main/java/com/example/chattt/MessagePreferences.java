package com.example.chattt;

import android.content.Context;
import android.content.SharedPreferences;

public class MessagePreferences {
    private static final String PREF_NAME = "ChatMessages";
    private static final String KEY_MESSAGES_PREFIX = "messages_";

    public static void saveMessages(Context context, String userName, String messages) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_MESSAGES_PREFIX + userName, messages);
        editor.apply();
    }

    public static String loadMessages(Context context, String userName) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(KEY_MESSAGES_PREFIX + userName, "");
    }
}
