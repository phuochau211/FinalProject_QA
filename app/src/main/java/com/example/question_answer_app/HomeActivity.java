package com.example.question_answer_app;

import static com.bumptech.glide.util.Util.getSnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rcvQuestion;
    FirestoreRecyclerAdapter adapter;
    private Context context;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvQuestion.setLayoutManager(linearLayoutManager);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Question");

        FirestoreRecyclerOptions<Question> options = new FirestoreRecyclerOptions.Builder<Question>()
                .setQuery(query, Question.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Question,QuestionViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull QuestionViewHolder holder, int position, @NonNull Question model) {
                holder.bind(model);
                holder.btnAnswer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(HomeActivity.this, AnswerActivity.class);
                                String namePerson = model.getNameP();
                                intent.putExtra("NameP", namePerson);
                                String subject = model.getSubject();
                                intent.putExtra("Subject", subject);
                                String question = model.getQuestion();
                                intent.putExtra("Question", question);
                                String rating = model.getRating();
                                intent.putExtra("Rating", rating);
                                startActivity(intent);
                            }
                        });
            }
            @Override
            public QuestionViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.item_qa, group, false);
                return new QuestionViewHolder(view);
            }
        };
        rcvQuestion.setAdapter(adapter);


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

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    class QuestionViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgAvatar;
        private TextView nameP, subject, question, rating;
        private Button btnAnswer;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.cimAvata);
            nameP = itemView.findViewById(R.id.tvNameP);
            subject = itemView.findViewById(R.id.tvSubject);
            question = itemView.findViewById(R.id.tvQuestion);
            rating = itemView.findViewById(R.id.tvNumStar);
            btnAnswer = itemView.findViewById(R.id.btnAnswer);
        }

        public void bind(Question model) {

            imgAvatar.setImageResource(R.drawable.default_avatar);
            question.setText(model.getQuestion());
            subject.setText(model.getSubject());
            nameP.setText(model.getNameP());
            rating.setText(model.getRating());
        }
    }


}