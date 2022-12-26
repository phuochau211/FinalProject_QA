package com.example.question_answer_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        TextView tvNameP = findViewById(R.id.tvNameP);
        ImageView cimAvatarP = findViewById(R.id.cimAvatarQA);
        TextView tvSubject = findViewById(R.id.tvSubject);
        TextView tvQuestion = findViewById(R.id.tvQuestion);
        TextView tvRating = findViewById(R.id.tvRating);
        TextView tvNumStar = findViewById(R.id.tvNumStar);

        cimAvatarP.setImageResource(R.drawable.default_avatar);
        tvNameP.setText(getIntent().getStringExtra("NameP"));
        tvSubject.setText(getIntent().getStringExtra("Subject"));
        tvQuestion.setText(getIntent().getStringExtra("Question"));
        tvNumStar.setText(getIntent().getStringExtra("Rating"));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnswerActivity.this, AskActivity.class);
                startActivity(intent);
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        ImageView cimAvatar = findViewById(R.id.cimAvatarUser);

        Uri photoUrl = user.getPhotoUrl();
        Glide.with(this).load(photoUrl).error(R.drawable.default_avatar).into(cimAvatar);

        cimAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnswerActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnswerActivity.this, HomeActivity.class ));
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.questionAnswer);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.account:
                        startActivity(new Intent(AnswerActivity.this, ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.questionAnswer:
                        return true;
                    case R.id.notification:
                        return true;
                }
                return false;
            }
        });
    }
}