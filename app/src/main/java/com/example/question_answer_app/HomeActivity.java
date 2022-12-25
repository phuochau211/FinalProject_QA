package com.example.question_answer_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rcvQuestion;
    private QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AskActivity.class);
                startActivity(intent);
            }
        });

        //Avata
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
                if (user == null) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                }
            }
        });

        //RecycleView
        rcvQuestion = findViewById(R.id.rcvQuestion);
        questionAdapter = new QuestionAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvQuestion.setLayoutManager(linearLayoutManager);
        
        questionAdapter.setData(getListQuestion());
        rcvQuestion.setAdapter(questionAdapter);

        //Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.account:
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.notification:
                        return true;
                    case R.id.questionAnswer:
                        startActivity(new Intent(HomeActivity.this, AskActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    private List<Question> getListQuestion() {
        List<Question> list = new ArrayList<>();
        list.add(new Question(R.drawable.avata, "Question 1", "Subject 1", "What is your name??", 4));
        list.add(new Question(R.drawable.avata2, "Question 2", "Subject 2", "What is your name??",3));
        list.add(new Question(R.drawable.avatar3, "Question 3", "Subject 3", "What is your name??",5));
        list.add(new Question(R.drawable.avata4, "Question 4", "Subject 4", "What is your name??",4.5));
        list.add(new Question(R.drawable.avata, "Question 1", "Subject 1", "What is your name??",4));
        list.add(new Question(R.drawable.avata2, "Question 2", "Subject 2", "What is your name??",3));
        list.add(new Question(R.drawable.avatar3, "Question 3", "Subject 3", "What is your name??",5));
        list.add(new Question(R.drawable.avata4, "Question 4", "Subject 4", "What is your name??",4.5));
        return list;
    }
}