package com.codefather.otsonthespot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ChatHomeActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "ChatHomeActivity: ";
    private static final String USER = "user";
    private static final String CHAT = "chat";

    private Button newChatButton;
    private ListView allChatsListview;
    private CustomAdapter customAdapter;

    ArrayList<Chat> userChats;

    TinyDB tinydb;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_home);
        Log.e(TAG, "lol:--------------------------------------------");
        initialize();

        newChatButton.setOnClickListener(this);

        Log.d(TAG, "lol: userChats size = " + userChats.size());

        allChatsListview.setAdapter(customAdapter);
    }

    private void initialize()
    {
        newChatButton = findViewById(R.id.startNewChatButton);
        allChatsListview = findViewById(R.id.allChatsListview);

        tinydb = new TinyDB(getApplicationContext());

        userChats = tinydb.getListChat(CHAT);

        customAdapter = new CustomAdapter();
    }



    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
        ChatHomeActivity.this.finish();
    }


    @Override
    public void onClick(View v)
    {
        if (v==newChatButton)
        {
            Intent i = new Intent(ChatHomeActivity.this, NewChatActivity.class);
            startActivity(i);
        }
    }

    private class CustomAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return userChats.size();
        }

        @Override
        public Object getItem(int position)
        {
            return userChats.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = getLayoutInflater().inflate(R.layout.chat_home_display, null);


            ImageView dpImageView;
            TextView chatNameTextView;
            TextView lastMessageTextView;

            chatNameTextView = view.findViewById(R.id.chatNameTextView);
            lastMessageTextView = view.findViewById(R.id.lastMessageTextView);


            chatNameTextView.getText();
            chatNameTextView.setText(userChats.get(position).getSecondParticipantID());
            int index = userChats.get(position).getMessages().size()-1;
            if (index > -1)
            lastMessageTextView.setText(userChats.get(position).getMessages().get(index).getMessage());


            return view;
        }
    }
}
//Log.d(TAG, "lol: ");