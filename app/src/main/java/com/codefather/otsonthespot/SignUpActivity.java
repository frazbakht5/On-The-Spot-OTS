package com.codefather.otsonthespot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener
{
    private final String TAG = "SIGNUP ACTIVITY: ";

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private static String userIDKey;
    private DatabaseReference userRef;

    private User appUser;

    private EditText nameEditText;
    private EditText institutionEditText;
    private EditText emialEditText;
    private EditText passwordEditText;
    private EditText confimPasswordEditText;

    private CheckBox termsAndConditionsCheckBox;

    private Button signupButton;

    private String name;
    private String institution;
    private String email;
    private String password;
    private String confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialize();

        signupButton.setOnClickListener(this);


    }

    private void initialize()
    {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("User");
        myRef = database.getReference("message");

        nameEditText = findViewById(R.id.nameEditText);
        institutionEditText = findViewById(R.id.institutionEditText);
        emialEditText = findViewById(R.id.emialEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confimPasswordEditText = findViewById(R.id.ConfimPasswordEditText);

        termsAndConditionsCheckBox = findViewById(R.id.termsAndConditionsCheckBox);

        signupButton = findViewById(R.id.signupButton);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
      /*  Intent i = new Intent(SignupActivity1.this, LogInActivity.class);
        startActivity(i);
        finish();
        */

    }

    @Override
    public void onClick(View v)
    {
        if (v == signupButton)
        {
            name = nameEditText.getText().toString();
            institution = institutionEditText.getText().toString();
            email = emialEditText.getText().toString();
            password = passwordEditText.getText().toString();
            confirmPassword = confimPasswordEditText.getText().toString();

            if (!termsAndConditionsCheckBox.isChecked())
            {
                Toast.makeText(SignUpActivity.this, "Please agree to terms and conditions", Toast.LENGTH_SHORT).show();
                return;
            }

            if (name.equals("") || institution.equals("") || email.equals("") || password.equals("") || confirmPassword.equals(""))
            {
                Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword))
            {
                Toast.makeText(SignUpActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "lol: createUserWithEmail:success");
                                updateUI();
                                FirebaseUser user = mAuth.getCurrentUser();
                                userIDKey = user.getUid();
                                appUser = new User(userIDKey, name, institution, email);
                                Log.d(TAG, "lol: userIDkey = " + userIDKey);
                                userRef.child(userIDKey).setValue(appUser);
                            }
                            else
                            {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "lol : createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


        }
    }

    private void updateUI()
    {
        Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(i);
        finish();
    }
}