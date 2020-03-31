package com.codefather.otsonthespot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ChatHomeActivity extends Fragment implements View.OnClickListener
{
    private static final String TAG = "ChatHomeActivity: ";
    private static final String USER = "user";
    private static final String CHAT = "chat";

    private Button newChatButton;
    private ListView allChatsListview;
    private CustomAdapter customAdapter;

    private View view;

    ArrayList<Chat> userChats;

    TinyDB tinydb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.activity_chat_home, container, false);

        initialize();

        newChatButton.setOnClickListener(this);

        Log.d(TAG, "lol: userChats size = " + userChats.size());

        allChatsListview.setAdapter(customAdapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat_home);
        Log.e(TAG, "lol:--------------------------------------------");
        /*initialize();

        newChatButton.setOnClickListener(this);

        Log.d(TAG, "lol: userChats size = " + userChats.size());

        allChatsListview.setAdapter(customAdapter);*/
    }

    private void initialize()
    {
        newChatButton = view.findViewById(R.id.startNewChatButton);
        allChatsListview = view.findViewById(R.id.allChatsListview);


        tinydb = new TinyDB(getActivity().getApplicationContext());

        userChats = tinydb.getListChat(CHAT);

        customAdapter = new CustomAdapter();
    }



//    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
//        ChatHomeActivity.this.finish();
    }


    @Override
    public void onClick(View v)
    {
        if (v==newChatButton)
        {
        /*    Intent i = new Intent(ChatHomeActivity.this, NewChatActivity.class);
            startActivity(i);

         */
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

//    @Override
    /*protected void onResume()
    {
        super.onResume();

        userChats = tinydb.getListChat(CHAT);
        allChatsListview.setAdapter(new CustomAdapter());
    }*/
}
//Log.d(TAG, "lol: ");