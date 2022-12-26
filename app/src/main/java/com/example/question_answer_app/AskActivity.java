package com.example.question_answer_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AskActivity extends AppCompatActivity {

    String [] subjects = {"#NMHĐH", "#TCMT", "#LTW", "#LTUDDĐ", "#CSDL", "Khác"};

    private Button btnAskQuestion;
    private TextView tvQuestion, tvRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

        TextView tvNameP = findViewById(R.id.tvNameP);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        String UserID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("User").document(UserID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        tvNameP.setText(value.getString("FullName"));
                    }
    });

                Button btnAsk = findViewById(R.id.btnAskQuestion);
                btnAsk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OnClickAskQuestion();
                    }
                });

                FloatingActionButton fab = findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AskActivity.this, AskActivity.class);
                        startActivity(intent);
                    }
                });

                ImageView cimAvatar = findViewById(R.id.cimAvatarUser);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    return;
                }
                Uri photoUrl = user.getPhotoUrl();
                Glide.with(AskActivity.this).load(photoUrl).error(R.drawable.default_avatar).into(cimAvatar);

                cimAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(AskActivity.this, ProfileActivity.class));
                    }
                });

                ImageView imgBack = findViewById(R.id.imgBack);
                imgBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(AskActivity.this, HomeActivity.class));
                    }
                });

                //Select Subject
                AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoComplete);

                ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(this, R.layout.list_subject, subjects);

                autoCompleteTextView.setAdapter(subjectAdapter);

                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                        String subject = parent.getItemAtPosition(position).toString();
                        Toast.makeText(getApplicationContext(), "Subject " + subject, Toast.LENGTH_SHORT).show();
                    }
                });
                //Ratingbar
                RatingBar ratingBarDiff = findViewById(R.id.ratingbarDifficult);
                TextView tvNumStar = findViewById(R.id.tvNumStar);

                ratingBarDiff.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        tvNumStar.setText("" + v);
                    }
                });
                // Bottomnavigation
                BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

                bottomNavigationView.setSelectedItemId(R.id.questionAnswer);

                bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.account:
                                startActivity(new Intent(AskActivity.this, ProfileActivity.class));
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.home:
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                overridePendingTransition(0, 0);
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

            private void OnClickAskQuestion() {
                EditText question = findViewById(R.id.edQuestion);
                String strQuestion = question.getText().toString();

                AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoComplete);
                String strSubject = autoCompleteTextView.getText().toString();

                TextView numStar = findViewById(R.id.tvNumStar);
                String rating = numStar.getText().toString();

                TextView tvNameP = findViewById(R.id.tvNameP);
                String strNameP = tvNameP.getText().toString();

                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                Random generator = new Random();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                int UserId = generator.nextInt(10000);
                DocumentReference documentReference = firebaseFirestore.collection("Question").document(String.valueOf(UserId));

                Map<String, Object> user = new HashMap<>();
                user.put("nameP", strNameP);
                user.put("question", strQuestion);
                user.put("subject", strSubject);
                user.put("rating", rating);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AskActivity.this, "Question created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AskActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                });
            }
        }