package com.codefather.otsonthespot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "SIGNIN ACTIVITY: ";
    private static final String SHARED_PREFS_FILE = "sharedPreferencesFile";
    private static final String USER = "user";
    private static final String CHAT = "chat";

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    private DatabaseReference userRef;
    Serializable signedInUser;


    private EditText emailEditText;
    private EditText passwordEditText;

    private Button signupButton;
    private Button signinButton;

    private ProgressBar signInProgressBar;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    ArrayList<Chat> userChats;

    TinyDB tinydb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Log.e(TAG, "lol:--------------------------------------------");

        initialize();

        signInProgressBar.setVisibility(View.GONE);

        signupButton.setOnClickListener(this);
        signinButton.setOnClickListener(this);
    }

    private void initialize()
    {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("User");


        emailEditText = findViewById(R.id.emailEditTextsignin);
        passwordEditText = findViewById(R.id.passwordEditText);

        signupButton = findViewById(R.id.signupButton);
        signinButton = findViewById(R.id.signinButton);

        signInProgressBar = findViewById(R.id.signInProgressBar);

        prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        editor = prefs.edit();

        Context context = getApplicationContext();
        tinydb = new TinyDB(context);
    }

    @Override
    public void onClick(View v)
    {
        if (v == signinButton)
        {
            signInProgressBar.setVisibility(View.VISIBLE);
            String password = passwordEditText.getText().toString();

            String email = emailEditText.getText().toString();


            Log.d(TAG, "lol: login button clicked");

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "lol: signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI();
                            }
                            else
                            {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                signInProgressBar.setVisibility(View.GONE);
                            }

                            // ...
                        }
                    });
        }

        if (v == signupButton)
        {
            Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void updateUI()
    {
//        Log.d(TAG, "lol: in method updateUI");

        loadUserInfo();


    }

    private void loadUserInfo()
    {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference requiredEntryRef = userRef.child(id);

//        Log.e(TAG, "lol : ref =" + userRef.toString());

        // Read from the database
        requiredEntryRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                Log.d(TAG, "lol: in method onDataChange");

                User signedInUser = dataSnapshot.getValue(User.class);
//                Log.d(TAG, "lol: signedInUser is: " + signedInUser);

                tinydb.putObject(USER, signedInUser);
//                Log.d(TAG, "lol: putObject Complete. Now gonna retrieve to check.");

                User temp2 = tinydb.getObject(USER, User.class);

                Log.d(TAG, "lol: User in tinydb = " + temp2);

                loadChats();
            }

            @Override
            public void onCancelled(DatabaseError error)
            {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }


        });
    }

    private void loadChats()
    {
        User user = tinydb.getObject(USER, User.class);

        ArrayList<String> userChatsIDs = user.getChatsID();
        userChats = new ArrayList<>();

        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("Chat");

        for (int i = 0; i < user.getChatsID().size(); i++)
        {
            chatRef.child(userChatsIDs.get(i)).addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
//                    Log.d(TAG, "lol: in method onDataChange");
                    Chat temp = dataSnapshot.getValue(Chat.class);

//                    Log.d(TAG, "lol: chat received is: " + temp);

                    userChats.add(temp);
                    String s = userChats.get(userChats.size() - 1).getChatID();
//                    Log.d(TAG, "lol: added in userChat = " + s);
//                    Log.d(TAG, "lol: userchat size = " + userChats.size());
                    tinydb.putChatListObject(CHAT, userChats);
                }

                @Override
                public void onCancelled(DatabaseError error)
                {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

/*        if(userChats.size() > 0)
        {
            tinydb.putChatListObject(CHAT, userChats);
            Log.d(TAG, "lol: Putting this chat list in SP: " + userChats);
            ArrayList<Chat> tempChat = tinydb.getListChat(CHAT);
            Log.d(TAG, "lol: retrieved chat list = " + tempChat);

        }
        */

        finalDelayTimer(2500);
    }


    private void finalDelayTimer(int time)
    {
        new Handler().postDelayed(new Runnable()
        {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run()
            {

                Intent i = new Intent(SignInActivity.this, DashboardActivity.class);

                signInProgressBar.setVisibility(View.GONE);
                startActivity(i);
                finish();
            }
        }, time);
    }

    private void delayTimer(int time)
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
            }
        }, time);
    }


}
