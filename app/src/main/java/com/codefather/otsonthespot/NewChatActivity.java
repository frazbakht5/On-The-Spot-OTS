package com.codefather.otsonthespot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class NewChatActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "NewChatActivity: ";
    private static final String USER = "user";
    private static final String CHAT = "chat";

    Button startNewChatButton;
    EditText senderEditText;
    EditText receiverEditText;
    EditText messageEditText;

    TinyDB tinydb;

    User user;
    ArrayList<Chat> chats;
    ArrayList<String> userChatIDs;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        Log.e(TAG, "lol:--------------------------------------------");

        initialize();

        startNewChatButton.setOnClickListener(this);


    }

    private void initialize()
    {
        startNewChatButton = findViewById(R.id.startNewChatButton);
        senderEditText = findViewById(R.id.senderEditText);
        receiverEditText = findViewById(R.id.receiverEditText);
        messageEditText = findViewById(R.id.messageEditText);

        tinydb = new TinyDB(getApplicationContext());

        user = tinydb.getObject(USER, User.class);
        userChatIDs = user.getChatsID();
        if (userChatIDs == null)
            userChatIDs = new ArrayList<>();

        chats = tinydb.getListChat(CHAT);
        if (chats == null)
            chats = new ArrayList<>();

        Log.d(TAG, "lol: At the Beginning, \n\tTinyDB userChatIDs =  " + userChatIDs + "\n\tTinyDB Chats = " + chats);
    }

    @Override
    public void onClick(View v)
    {
        if (v==startNewChatButton)
        {
            String newChatID = FirebaseDatabase.getInstance().getReference().push().getKey();

            String firstID = senderEditText.getText().toString();
            String secondID = receiverEditText.getText().toString();
            String msgText = messageEditText.getText().toString();

            Message message = new Message(firstID, secondID, msgText);
            ArrayList<Message> messageList = new ArrayList<>();
            messageList.add(message);
            Chat newChat = new Chat(newChatID, firstID, secondID, messageList);

            chats.add(newChat);
            userChatIDs.add(newChatID);

            user.setChatsID(userChatIDs);

            tinydb.putObject(USER, user);
            tinydb.putChatListObject(CHAT, chats);


            DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("Chat");
            chatRef.child(newChatID).setValue(newChat);

            DatabaseReference userChatIDsRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUserID()).child("chatsID");
            userChatIDsRef.setValue(userChatIDs);

            Log.d(TAG, "lol: At the End, \n\tTinyDB userChatIDs =  " + userChatIDs + "\n\tTinyDB Chats = " + chats);


            Intent i = new Intent(NewChatActivity.this, ChatHomeActivity.class);
            startActivity(i);
            finish();



        }
    }
}
