package com.codefather.otsonthespot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;


public class DashboardActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "DashboardActivity: ";
    private Button chatButton;
    private Button bottomNavigationButton;
    private Button signoutButton;
    private User user;
    private TextView textview;

    private static final String SHARED_PREFS_FILE = "sharedPreferencesFile";
    private static final String USER = "user";
    private static final String CHAT = "chat";
    private User currentUser;

    TinyDB tinydb;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Log.e(TAG, "lol:--------------------------------------------");

        initialize();


        chatButton.setOnClickListener(this);
        signoutButton.setOnClickListener(this);

        bottomNavigationButton.setOnClickListener(this);



    }

    private void initialize()
    {
        chatButton = findViewById(R.id.chatButton);
        signoutButton = findViewById(R.id.signoutButton);
        textview = findViewById(R.id.textView);
        bottomNavigationButton = findViewById(R.id.bottomNavigationButton);

        Context context = getApplicationContext();
        tinydb = new TinyDB(context);

        try
        {
            user = tinydb.getObject(USER, User.class);
            Log.d(TAG, "lol: signedInUser is: " + user.getName() + " , ChatsID = " + user.getChatsID());
            textview.setText(user.getName());
        } catch (Exception e)
        {
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v == chatButton)
        {
            Intent i = new Intent(DashboardActivity.this, ChatHomeActivity.class);
            startActivity(i);
//            finish();
        }

        else if (v == signoutButton)
        {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(DashboardActivity.this, SignInActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            tinydb.clear();


            startActivity(i);
            finish();
        }

        else if (v == bottomNavigationButton)
        {
            Intent i = new Intent(DashboardActivity.this, BottomTabTestActivity.class);
            startActivity(i);
        }
    }

}
