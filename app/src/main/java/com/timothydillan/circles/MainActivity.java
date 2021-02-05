package com.timothydillan.circles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.timothydillan.circles.Utils.FirebaseUtil;
import com.timothydillan.circles.Utils.UserUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUtil.initializeCurrentFirebaseUser();
        UserUtil userUtil = new UserUtil();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        userUtil.addEventListener(new UserUtil.UsersListener() {
            @Override
            public void onUserReady() {
                bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MapsFragment()).commit();
            }

            @Override
            public void onUsersChange(@NonNull DataSnapshot snapshot) {

            }
        });
        userUtil.initializeCurrentUser();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment currentFragment = new MapsFragment();
                int itemId = item.getItemId();
                if (itemId == R.id.location) {
                    currentFragment = new MapsFragment();
                } else if (itemId == R.id.mood) {
                    currentFragment = new MoodsFragment();
                } else if (itemId == R.id.safety) {
                    currentFragment = new SafetyFragment();
                } else if (itemId == R.id.settings) {
                    currentFragment = new SettingsFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, currentFragment).commit();
                return true;
            };

    public void onSignOutClick(View v) {
        FirebaseUtil.signOut();
        Intent signUpActivity = new Intent(this, SignUpActivity.class);
        startActivity(signUpActivity);
        finish();
    }

    public void onJoinCircleClick(View v) {
        Intent joinCircleActivity = new Intent(this, JoinCircleActivity.class);
        startActivity(joinCircleActivity);
        finish();
    }

}