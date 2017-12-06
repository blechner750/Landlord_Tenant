package com.example.brett.landlord_tenant;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class messagesActivity extends AppCompatActivity {


    SharedPreferences mPrefs;
    String message;

    EditText message_prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        message_prompt = (EditText) findViewById(R.id.messages_prompt);

        mPrefs = getSharedPreferences("key", Context.MODE_PRIVATE);
        mPrefs.getString("message", message_prompt.getText().toString());

    }

    protected void onPause(){
        super.onPause();

        SharedPreferences.Editor ed = mPrefs.edit();
        message_prompt = (EditText) findViewById(R.id.messages_prompt);
        ed.putString("message", message_prompt.getText().toString());
        ed.commit();
    }
}
