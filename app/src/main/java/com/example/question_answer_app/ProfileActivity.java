package com.example.question_answer_app;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        ImageView cimAvatar = findViewById(R.id.cimAvatar);

        Uri photoUrl = user.getPhotoUrl();
        Glide.with(this).load(photoUrl).error(R.drawable.default_avatar).into(cimAvatar);

        TextView tvNameP = findViewById(R.id.tvNameProfile);
        TextView tvName = findViewById(R.id.tvName);
        TextView tvMail = findViewById(R.id.tvEmail);

        TextView tvGender = findViewById(R.id.tvGender);
        TextView tvYear = findViewById(R.id.tvYear);
        TextView tvPhone = findViewById(R.id.tvPhoneNum);
        TextView tvProvine = findViewById(R.id.tvProvince);

        String email = user.getEmail();
        String provine = user.getProviderId();
        String phone = user.getPhoneNumber();

        tvMail.setText(email);
        tvName.setText(email);
        tvNameP.setText(email);
        tvPhone.setText(phone);

        if(tvProvine == null) {
            tvProvine.setVisibility(View.GONE);
        } else {
            tvProvine.setVisibility(View.VISIBLE);
            tvProvine.setText(provine);
        }
        TextView tvChangePass = findViewById(R.id.tv_changePassWord);
        tvChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        TextView tvLogOut = findViewById(R.id.tv_LogOut);
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, AskActivity.class);
                startActivity(intent);
            }
        });

        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, HomeActivity.class ));
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.account);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.account:
                        return true;
                    case R.id.notification:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.questionAnswer:
                        startActivity(new Intent(ProfileActivity.this, AskActivity.class));
                        overridePendingTransition(0,0);
                    return true;
                }
                return false;
            }
        });

    }
}
