package com.codefather.otsonthespot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ChatHomeActivity extends AppCompatActivity implements View.OnClickListener
{
    ListView listView;
    Button newChatButton;
    EditText senderEditText;
    EditText receiverEditText;
    EditText messageEditText;

    ArrayList<Chat> chats;

    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_home);

        initialize();

        newChatButton.setOnClickListener(this);

        //assign adapter to listview
        listView.setAdapter(arrayAdapter);

    }

    private void initialize()
    {
        listView = findViewById(R.id.chatListview);
        newChatButton = findViewById(R.id.newChatButton);
        senderEditText = findViewById(R.id.senderEditText);
        receiverEditText = findViewById(R.id.receiverEditText);
        messageEditText = findViewById(R.id.messageEditText);

        chats = new ArrayList<>();

//        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, chats);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, chats);

    }

    @Override
    public void onClick(View v)
    {
        if (v == newChatButton)
        {
            String chatID = FirebaseDatabase.getInstance().getReference().push().getKey();
            ArrayList<Message> messages = new ArrayList<>();

            Chat newChat = new Chat(chatID, senderEditText.getText().toString(), receiverEditText.getText().toString(), messages);

            messages.add(new Message(senderEditText.getText().toString(), receiverEditText.getText().toString(), messageEditText.getText().toString()));
//            Chat newChat = new Chat("dummyID123", messages);

//            arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,newChat.getMessages());
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, chats);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        ChatHomeActivity.this.finish();
    }
}
