package com.codefather.otsonthespot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class BottomTabTestActivity extends AppCompatActivity
{
    private BottomNavigationView bottomNavigation;
    private TextView testTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab_test);

        initialize();

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.profile:
                        testTextView.setText("PROFILE");
                        return true;

                    case R.id.dashboard:
                        testTextView.setText("DASHBOARD");
                        return true;

                    case R.id.search:
                        testTextView.setText("SEARCH");
                        return true;

                    case R.id.group:
                        testTextView.setText("GROUP");
                        return true;

                    case R.id.inbox:
                        testTextView.setText("INBOX");
                        return true;

                    default:
                        return false;


                }
            }
        });


    }

    private void initialize()
    {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.search);

        testTextView = findViewById(R.id.testTextView);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(BottomTabTestActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
    }
}
