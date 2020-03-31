package com.codefather.otsonthespot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class BottomTabTestActivity extends AppCompatActivity
{
    private BottomNavigationView bottomNavigation;
    private TextView testTextView;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab_test);

        initialize();

        bottomNavigation.setOnNavigationItemSelectedListener(bnv);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SearchPartnerFragment()).commit();


    }

    private void initialize()
    {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.search);
        frameLayout = findViewById(R.id.frameLayout);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(BottomTabTestActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
    }

    BottomNavigationView.OnNavigationItemSelectedListener bnv = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
        {

            Fragment selectedFragment = null;
            switch (menuItem.getItemId())
            {
                case R.id.profile:

                    break;

                case R.id.notes:
                    selectedFragment = new NotesFragment();
                    break;

                case R.id.search:
                    selectedFragment = new SearchPartnerFragment();
                    break;

                case R.id.group:

                    break;

                case R.id.inbox:
                    selectedFragment = new ChatHomeFragment();
                    break;

                default:
                    return false;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();

            return true;
        }
    };
}
