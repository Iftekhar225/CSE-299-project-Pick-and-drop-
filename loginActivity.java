package com.example.pickanddrop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class loginActivity extends AppCompatActivity {
    EditText emailId,password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth=FirebaseAuth.getInstance();
        emailId=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);
        btnSignIn=findViewById(R.id.button2);
        tvSignUp=findViewById(R.id.textView);
       mAuthStateListener=new FirebaseAuth.AuthStateListener() {

       



           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

           }
       }
    }
}
