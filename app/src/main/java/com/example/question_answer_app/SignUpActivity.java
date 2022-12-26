package com.example.question_answer_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.annotation.Documented;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new ProgressDialog(this);

        TextView tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
        Button btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickSignUp();
            }
        });

    }

    private void OnClickSignUp() {
        EditText userName = findViewById(R.id.edUser);
        EditText edtPass = findViewById(R.id.edPass);
        EditText edtMail = findViewById(R.id.edMail);
        EditText yearBirth = findViewById(R.id.edYear);
        EditText phoneNum = findViewById(R.id.edPhone);
        EditText province = findViewById(R.id.edProvince);

        String strEmail = edtMail.getText().toString().trim();
        String strPass = edtPass.getText().toString().trim();

        String strUserName = userName.getText().toString();
        String strYearBirth = yearBirth.getText().toString();
        String strPhoneNum = phoneNum.getText().toString();
        String strProvince = province.getText().toString();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.createUserWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {

                            String UserId = auth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("User").document(UserId);

                            Map<String, Object> user = new HashMap<>();
                            user.put("FullName",strUserName);
                            user.put("Year of Birth",strYearBirth);
                            user.put("Phone Number",strPhoneNum);
                            user.put("Province", strProvince);
                            user.put("Email", strEmail);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(SignUpActivity.this, "Profile created", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(SignUpActivity.this, "SignUp failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }



