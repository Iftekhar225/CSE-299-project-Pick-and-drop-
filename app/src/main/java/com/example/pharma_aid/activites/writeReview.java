package com.example.pharma_aid.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharma_aid.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class writeReview extends AppCompatActivity {
    //ui views
    private ImageButton backBtn;
    private ImageView profileIv;
    private TextView shopNameTv, labelTv;
    private RatingBar ratingBar;
    private EditText reviewEt;
    private FloatingActionButton submitBtn;


    private String shopUid;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        //intialize ui views
        backBtn=findViewById(R.id.backBtn);
        profileIv=findViewById(R.id.profileIv);
        shopNameTv=findViewById(R.id.shopNameTv);
        ratingBar=findViewById(R.id.ratingBar);
        reviewEt=findViewById(R.id.reviewEt);
        submitBtn=findViewById(R.id.submitBtn);

        //get the uid from intent
        shopUid= getIntent().getStringExtra("shopUid");
        firebaseAuth=FirebaseAuth.getInstance();
        //load shop info; shop name and image
        loadShopInfo();

        //if user has written review to the shop show it
        loadMyReview();
        //go to previous activity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
        });
    }

    private void loadShopInfo() {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.child(shopUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get shop info
                String shopName=""+dataSnapshot.child("shopName").getValue();
                String shopImage=""+dataSnapshot.child("profileImage").getValue();
                //set shop info
                shopNameTv.setText(shopName);
                try {
                    Picasso.get().load(shopImage).placeholder(R.drawable.ic_store_gray).into(profileIv);
                }
                catch (Exception e){
                    profileIv.setImageResource(R.drawable.ic_store_gray);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadMyReview() {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.child(shopUid).child("Ratings").child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            //review is available in this shop
                            //get review details
                            String uid=""+dataSnapshot.child("uid").getValue();
                            String ratings=""+dataSnapshot.child("ratings").getValue();
                            String review=""+dataSnapshot.child("review").getValue();
                            String timestamp=""+dataSnapshot.child("timestamp").getValue();
                            //set reviw details to our review
                            float myRating= Float.parseFloat(ratings);
                            ratingBar.setRating(myRating);
                            reviewEt.setText(review);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void inputData() {
        String ratings=""+ratingBar.getRating();
        String review=reviewEt.getText().toString().trim();

        //for time to review
        String timestamp=""+System.currentTimeMillis();
        //set data in hashmap
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("uid",""+firebaseAuth.getUid());
        hashMap.put("ratings",""+ratings);//4.8
        hashMap.put("review",""+review); //service good
        hashMap.put("timestamp",""+timestamp);
        //put to db
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.child(shopUid).child("Ratings").child(firebaseAuth.getUid()).updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //review added to the db
                        Toast.makeText(writeReview.this,"Review submitted successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(writeReview.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });


    }
}