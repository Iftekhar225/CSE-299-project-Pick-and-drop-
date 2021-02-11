package com.example.pharma_aid.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharma_aid.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class login extends AppCompatActivity {


    //UI views
    private EditText emailEt,passwordEt;
    private TextView forgotTv,noAccountTv;
    private Button loginBtn;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // int UI views

        emailEt= findViewById(R.id.emailEt);
        passwordEt= findViewById(R.id.passwordEt);
        forgotTv= findViewById(R.id.forgotTv);
        loginBtn = findViewById(R.id.loginBtn);
        noAccountTv=findViewById(R.id.noAccountTv);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog( this );
        progressDialog.setTitle( "Please Wait" );
        progressDialog.setCanceledOnTouchOutside( false );






        noAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, register_user.class));

            }
        });
        forgotTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, forget_password.class));

            }
        });

        loginBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();

            }

            private  String email, password;

            private void loginUser() {

                email=emailEt.getText().toString().trim();
                password=passwordEt.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher( email ).matches())
                {
                    Toast.makeText( login.this, "Invalid email pattern ..." , Toast.LENGTH_SHORT ).show();
                    return;
                }
                if(TextUtils.isEmpty( password ))
                {

                    Toast.makeText( login.this, "Enter Password..." , Toast.LENGTH_SHORT ).show();

                }


                progressDialog.setMessage( "Loging In" );
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword( email,password ).addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        // Logged in successfully

                        makeMeOnline();

                    }
                } ).addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        // Tailed Loggin in

                        progressDialog.dismiss();
                        Toast.makeText( login.this, ""+e.getMessage() , Toast.LENGTH_SHORT ).show();

                    }
                } );




            }
        } );


    }

    private  void  makeMeOnline()
    {
        // after loggin in, make user online
        progressDialog.setMessage( "Checking User" );
        HashMap<String,Object> hashMap = new HashMap<>(  );
        hashMap.put("online","ture"  );

        // update value to db

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference( "Users" );
        ref.child(firebaseAuth.getUid()).updateChildren( hashMap ).addOnSuccessListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                // update successful

                checkUserType();


            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                // Tailed updating
                progressDialog.dismiss();
                Toast.makeText( login.this ,""+e.getMessage() , Toast.LENGTH_SHORT ).show();

            }
        } );



    }



    private void  checkUserType()
    {
        // if user is a seller, start seller main screen
        // if user is buyer, start user main screen

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(  "Users");
        ref.orderByChild( "uid" ).equalTo( firebaseAuth.getUid() ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datassnapshot) {
                for (DataSnapshot ds: datassnapshot.getChildren())
                {
                    String accountType= " "+ds.child( "accountType" ).getValue();
                    if(accountType.equals( "Seller" ))
                    {
                        progressDialog.dismiss();
                        // user is seller
                        startActivity(new Intent( login.this, main_seller.class));

                        finish();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        // user is buyer

                        startActivity(new Intent( login.this, main_user.class));

                        finish();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );

    }

}