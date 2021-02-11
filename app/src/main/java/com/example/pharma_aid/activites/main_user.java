package com.example.pharma_aid.activites;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.view.View;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.pharma_aid.R;
        import com.example.pharma_aid.adapters.AdapterOrderUser;
        import com.example.pharma_aid.adapters.AdapterShop;
        import com.example.pharma_aid.models.ModelOrderUser;
        import com.example.pharma_aid.models.ModelShop;
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

public class main_user extends AppCompatActivity {

    private TextView nameTv,emailTv,phoneTv,tabShopsTv,tabOrdersTv;
    private RelativeLayout shopsRl,ordersRl;
    private ImageButton logoutBtn,editProfileBtn;
    private ImageView profileIv;
    private RecyclerView shopsRv, ordersRv;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ArrayList<ModelShop> shopsList;
    private AdapterShop adapterShop;

    private ArrayList<ModelOrderUser> ordersList;
    private AdapterOrderUser adapterOrderUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_user );


        nameTv = findViewById( R.id.nameTv);
        emailTv=findViewById(R.id.emailTv);
        phoneTv=findViewById(R.id.phoneTv);
        tabShopsTv=findViewById(R.id.tabShopsTv);
        tabOrdersTv=findViewById(R.id.tabOrdersTv);
        logoutBtn = findViewById( R.id.logoutBtn);
        editProfileBtn=findViewById(R.id.editProfileBtn);
        profileIv=findViewById(R.id.profileIv);
        shopsRl=findViewById(R.id.shopsRl);
        ordersRl=findViewById(R.id.ordersRl);
        shopsRv=findViewById(R.id.shopsRv);
        ordersRv=findViewById(R.id.ordersRv);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth =FirebaseAuth.getInstance();
        checKUser();
        //at start show shops UI
        showShopsUI();



        logoutBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Make offline
                // Sign out
                // go to login Activity
                makeMeOffline();
            }
        } );

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open edit profile activity
                startActivity(new Intent(main_user.this, Profile_Edit_User.class));
            }
        });

        tabShopsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show shops
                showShopsUI();
            }
        });

        tabOrdersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show orders
                showOrdersUI();
            }
        });


    }

    private void showShopsUI() {
        //show shops UI,hide orders ui
        shopsRl.setVisibility(View.VISIBLE);
        ordersRl.setVisibility(View.GONE);
        tabShopsTv.setTextColor(getResources().getColor(R.color.colorBlack));
        tabShopsTv.setBackgroundResource(R.drawable.shape_rect04);

        tabOrdersTv.setTextColor(getResources().getColor(R.color.colorwhite));
        tabOrdersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void showOrdersUI() {
        //show orders UI,hide shops ui
        shopsRl.setVisibility(View.GONE);
        ordersRl.setVisibility(View.VISIBLE);
        tabShopsTv.setTextColor(getResources().getColor(R.color.colorwhite));
        tabShopsTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        tabOrdersTv.setTextColor(getResources().getColor(R.color.colorBlack));
        tabOrdersTv.setBackgroundResource(R.drawable.shape_rect04);

    }

    private void makeMeOffline() {
        progressDialog.setMessage("Logging Out....");
        HashMap<String,Object>hashMap=new HashMap<>();
        hashMap.put("online","false");

        //update value to db
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // update successful

                        firebaseAuth.signOut();
                        checKUser();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Tailed updating
                progressDialog.dismiss();
                Toast.makeText( main_user.this ,""+e.getMessage() , Toast.LENGTH_SHORT ).show();
            }
        });
    }

    private void checKUser() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user==null)
        {
            startActivity(new Intent( main_user.this, login.class) );
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
                    //get user data
                    String name =""+ds.child( "name" ).getValue();
                    String email =""+ds.child( "email" ).getValue();
                    String phone =""+ds.child( "phone" ).getValue();
                    String profileImage =""+ds.child( "profileImage" ).getValue();
                    String accountType =""+ds.child( "accountType" ).getValue();
                    String city=""+ds.child("city").getValue();
                    //set user data
                    nameTv.setText(name);
                    emailTv.setText(email);
                    phoneTv.setText(phone);
                    try {
                        Picasso.get().load(profileImage).placeholder(R.drawable.ic_person_gray).into(profileIv);

                    }
                    catch (Exception e){
                        profileIv.setImageResource(R.drawable.ic_person_gray);

                    }
                    //load only those shops that are in the city of user
                    loadShops(city);
                    loadOrders();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }

    private void loadOrders() {
        //intial order list
        ordersList=new ArrayList<>();

        //get orders
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordersList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String uid=""+ds.getRef().getKey();

                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Orders");
                    ref.orderByChild("orderBy").equalTo(firebaseAuth.getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                                            ModelOrderUser modelOrderUser=ds.getValue(ModelOrderUser.class);

                                            //add to list
                                            ordersList.add(modelOrderUser);
                                        }
                                        //setup adapter
                                        adapterOrderUser=new AdapterOrderUser(main_user.this,ordersList);
                                        //set to recycler
                                        ordersRv.setAdapter(adapterOrderUser);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadShops(final String myCity) {
        //initial list
        shopsList=new ArrayList<>();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("accountType").equalTo("Seller")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //clear the list before adding
                        shopsList.clear();
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            ModelShop modelShop=ds.getValue(ModelShop.class);

                            String shopCity=""+ds.child("city").getValue();
                            //show only user city shops
                            if(shopCity.equals(myCity)){
                                shopsList.add(modelShop);
                            }

                            //if you want to display all the shops,skip the if statement
                            // shopsList.add(modelShop);
                        }
                        //setup adapter
                        adapterShop=new AdapterShop(main_user.this, shopsList);
                        //set the adapter for recycle
                        shopsRv.setAdapter(adapterShop);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}