package com.example.pharma_aid.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharma_aid.adapters.AdapterProductSeller;
import com.example.pharma_aid.Constants;
import com.example.pharma_aid.models.ModelProduct;
import com.example.pharma_aid.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class main_seller extends AppCompatActivity {

    private TextView nameTv,shopNameTv,emailTv,tabProductsTv,tabOrdersTv,filteredProductsTv;
    private EditText searchProductEt;
    private ImageButton logoutBtn, editProfileBtn, addProductBtn, filterProductBtn;
    private ImageView profileIv;
    private RelativeLayout productsRl,ordersRl;
    private RecyclerView productsRv;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private ArrayList<ModelProduct> productList;
    private AdapterProductSeller adapterProductSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seller);

        nameTv = findViewById(R.id.nameTv);
        shopNameTv = findViewById(R.id.shopNameTv);
        emailTv = findViewById(R.id.emailTv);
        tabProductsTv = findViewById(R.id.tabProductsTv);
        tabOrdersTv = findViewById(R.id.tabOrdersTv);
        filteredProductsTv=findViewById(R.id.filteredProductsTv);
        searchProductEt = findViewById(R.id.searchProductEt);
        logoutBtn = findViewById(R.id.logoutBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        addProductBtn = findViewById(R.id.addProductBtn);
        filterProductBtn=findViewById(R.id.filterProductBtn);
        profileIv=findViewById(R.id.profileIv);
        productsRl=findViewById(R.id.productsRl);
        ordersRl=findViewById(R.id.ordersRl);
        productsRv=findViewById(R.id.productsRv);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();
        checKUser();
        loadAllProducts();

        showProductsUI();

        //search
        searchProductEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterProductSeller.getFilter().filter(s);

                }
                catch (Exception e){
                    e.printStackTrace();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make offline
                // Sign out
                // go to login Activity
                makeMeOffline();
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit profile activity
                startActivity(new Intent(main_seller.this, Profile_Edit_Seller.class));
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit profile activity
                startActivity(new Intent(main_seller.this, Add_Product.class));

            }
        });
        tabProductsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //load products
                showProductsUI();
            }
        });

        tabOrdersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load orders
                showOrdersUI();


            }
        });

        filterProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(main_seller.this);
                builder.setTitle("Choose Category")
                        .setItems(Constants.productCategories1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //get selected item

                                String  selected=Constants.productCategories1[which];
                                filteredProductsTv.setText(selected);
                                if (selected.equals("All")){
                                    //load all
                                    loadAllProducts();
                                }

                                else {
                                    //load filtered
                                    loadFilteredProducts(selected);
                                }


                            }
                        })
                        .show();

            }
        });


    }

    private void loadFilteredProducts(final String selected) {
        productList=new ArrayList<>();

        //get all products

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        productList.clear();
                        for (DataSnapshot ds:snapshot.getChildren())
                        {
                            String productCategory=""+ds.child("productCategory").getValue();
                            //if selected category matches product category then add in list
                            if(selected.equals(productCategory)){
                                ModelProduct modelProduct=ds.getValue(ModelProduct.class);
                                productList.add(modelProduct);

                            }

                        }

                        //setup adapter
                        adapterProductSeller=new AdapterProductSeller(main_seller.this,productList);

                        //set adapter

                        productsRv.setAdapter(adapterProductSeller);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadAllProducts() {
        productList=new ArrayList<>();

        //get all products

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        productList.clear();
                        for (DataSnapshot ds:snapshot.getChildren())
                        {
                            ModelProduct modelProduct=ds.getValue(ModelProduct.class);
                            productList.add(modelProduct);
                        }

                        //setup adapter
                        adapterProductSeller=new AdapterProductSeller(main_seller.this,productList);

                        //set adapter

                        productsRv.setAdapter(adapterProductSeller);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    private void showProductsUI() {
        //show Products ui and hide orders ui
        productsRl.setVisibility(View.VISIBLE);
        ordersRl.setVisibility(View.GONE);

        tabProductsTv.setTextColor(getResources().getColor(R.color.colorBlack));
        tabProductsTv.setBackgroundResource(R.drawable.shape_rect04);


        tabOrdersTv.setTextColor(getResources().getColor(R.color.colorwhite));
        tabOrdersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }
    private void showOrdersUI() {
        //show orders ui and hide products ui
        productsRl.setVisibility(View.GONE);
        ordersRl.setVisibility(View.VISIBLE);

        tabProductsTv.setTextColor(getResources().getColor(R.color.colorwhite));
        tabProductsTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));


        tabOrdersTv.setTextColor(getResources().getColor(R.color.colorBlack));
        tabOrdersTv.setBackgroundResource(R.drawable.shape_rect04);

    }




    private void makeMeOffline() {

        // after loggin in, make user online
        progressDialog.setMessage( "Logging Out...." );
        HashMap<String,Object> hashMap = new HashMap<>(  );
        hashMap.put("online","false"  );

        // update value to db

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference( "Users" );
        ref.child(firebaseAuth.getUid()).updateChildren( hashMap )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                // update successful

                firebaseAuth.signOut();
                checKUser();


            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                // Tailed updating
                progressDialog.dismiss();
                Toast.makeText( main_seller.this ,""+e.getMessage() , Toast.LENGTH_SHORT ).show();

            }
        } );





    }

    private void checKUser() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user==null)
        {
            startActivity(new Intent( main_seller.this, login.class) );
            finish();

        }
        else{
            loadMyInfo();

        }


    }

    private void loadMyInfo() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild( "uid" ).equalTo( firebaseAuth.getUid() ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //get data from db
                    String name =""+ds.child( "name" ).getValue();
                    String accountType =""+ds.child( "accountType" ).getValue();
                    String email =""+ds.child( "email" ).getValue();
                    String shopName =""+ds.child( "shopName" ).getValue();
                    String profileImage =""+ds.child( "profileImage" ).getValue();

                    //set the data to ui
                    nameTv.setText( name);
                    shopNameTv.setText(shopName);
                    emailTv.setText(email);
                    try {
                        Picasso.get().load(profileImage).placeholder(R.drawable.ic_store_gray).into(profileIv);

                    }
                    catch (Exception e)
                    {
                        profileIv.setImageResource(R.drawable.ic_store_gray);

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );

    }
}