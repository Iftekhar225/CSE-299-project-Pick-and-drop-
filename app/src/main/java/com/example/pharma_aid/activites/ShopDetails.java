package com.example.pharma_aid.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharma_aid.Constants;
import com.example.pharma_aid.R;
import com.example.pharma_aid.adapters.AdapterCartItem;
import com.example.pharma_aid.adapters.AdapterProductUser;
import com.example.pharma_aid.models.ModelCartItem;
import com.example.pharma_aid.models.ModelProduct;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class ShopDetails extends AppCompatActivity {

    //declare ui views
    private ImageView shopIv;
    private TextView shopNameTv, phoneTv,emailTv,openCloseTv,deliveryFeeTv,addressTv,filteredProductsTv,cartCountTv;
    private ImageButton callBtn,mapBtn,cartBtn,backBtn,filterProductBtn,reviewsBtn;
    private RelativeLayout shopRl,toolbarRl,productsRl;
    private EditText searchProductEt;
    private RecyclerView productsRv;

    private String shopUid;
    private String myLatitude,myLongitude,myPhone;
    private String shopName, shopEmail, shopPhone, shopAddress, shopLatitude, shopLongitude;
    public String deliveryFee;

    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;
    private ArrayList<ModelProduct> productsList;
    private AdapterProductUser adapterProductUser;

    //cart
    private ArrayList<ModelCartItem> cartItemList;
    private AdapterCartItem adapterCartItem;

    private EasyDB easyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        //intial ui views
        shopIv= findViewById(R.id.shopIv);
        shopNameTv= findViewById(R.id.shopNameTv);
        phoneTv= findViewById(R.id.phoneTv);
        emailTv= findViewById(R.id.emailTv);
        openCloseTv= findViewById(R.id.openCloseTv);
        deliveryFeeTv= findViewById(R.id.deliveryFeeTv);
        addressTv= findViewById(R.id.addressTv);
        filteredProductsTv= findViewById(R.id.filteredProductsTv);
        callBtn= findViewById(R.id.callBtn);
        mapBtn= findViewById(R.id.mapBtn);
        cartBtn= findViewById(R.id.cartBtn);
        backBtn= findViewById(R.id.backBtn);
        filterProductBtn= findViewById(R.id.filterProductBtn);
        searchProductEt= findViewById(R.id.searchProductEt);
        productsRv= findViewById(R.id.productsRv);
        cartCountTv= findViewById(R.id.cartCountTv);
        reviewsBtn= findViewById(R.id.reviewsBtn);

        // initialize progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        //get uid of the shop from intent
        shopUid=getIntent().getStringExtra("shopUid");
        firebaseAuth=FirebaseAuth.getInstance();
        loadMyInfo();
        loadShopDetails();
        loadShopProducts();

        //declare it to class level in onCreate
        easyDB= EasyDB.init(this,"ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("Item_Id", new String[]{"text", "unique"}))
                .addColumn(new Column("Item_PID", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Name", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price_Each", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Quantity", new String[]{"text", "not null"}))
                .doneTableColumn();

        //each shop have its own products and orders so if user add items to cart and go back and open cart in different shop then cart should be different
        //so delete cart data whenever user open this activity
        deleteCartData();
        cartCount();
        //search
        searchProductEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterProductUser.getFilter().filter(s);

                }
                catch (Exception e){
                    e.printStackTrace();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go previous acitivity
                onBackPressed();
            }
        });
        //shop reviews show below

        reviewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass uid to show shop reviews
                Intent intent=new Intent(ShopDetails.this,ShopReviews.class);
                intent.putExtra("shopUid",shopUid);
                startActivity(intent);

            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show cart dialog
                showCartDialog();


            }
        });
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhone();

            }
        });
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });

        filterProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ShopDetails.this);
                builder.setTitle("Choose Category")
                        .setItems(Constants.productCategories1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //get selected item

                                String  selected=Constants.productCategories1[which];
                                filteredProductsTv.setText(selected);
                                if (selected.equals("All")){
                                    //load all
                                    loadShopProducts();
                                }

                                else {
                                    //load filtered
                                   adapterProductUser.getFilter().filter(selected);
                                }


                            }
                        })
                        .show();

            }
        });


    }

    private void deleteCartData() {

        easyDB.deleteAllDataFromTable();//delete all records from cart

    }
    public void cartCount(){
        //keep it public so we can access in adapter
        //get cart count
        int count=easyDB.getAllData().getCount();
        if (count<=0){
            //no item in cart,hide cart count textview
            cartCountTv.setVisibility(View.GONE);
        }

        else {
            //have items in cart,show cart count textview and set count
            cartCountTv.setVisibility(View.VISIBLE);
            cartCountTv.setText(""+count); //concatenate with string, becuase we can set integer in textview
        }
    }


    public double allTotalPrice=0.00;
    //need to access these views in adapter so making public
    public TextView sTotalTv,dFeeTv, allTotalPriceTv;

    private void showCartDialog() {
        //initialize list
        cartItemList=new ArrayList<>();
        //inflate cart layout
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_cart, null);
        //intialize views
        TextView shopNameTv=view.findViewById(R.id.shopNameTv);
        RecyclerView cartItemsRv=view.findViewById(R.id.cartItemsRv);
        sTotalTv=view.findViewById(R.id.sTotalTv);
        dFeeTv=view.findViewById(R.id.dFeeTv);
        allTotalPriceTv=view.findViewById(R.id.totalTv);
        RadioButton paymentBtn=view.findViewById(R.id.paymentBtn);
        Button checkoutBtn=view.findViewById(R.id.checkoutBtn);

        //dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        //set views to the dialog
        builder.setView(view);
        shopNameTv.setText(shopName);

        EasyDB easyDB= EasyDB.init(this,"ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("Item_Id", new String[]{"text", "unique"}))
                .addColumn(new Column("Item_PID", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Name", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price_Each", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Quantity", new String[]{"text", "not null"}))
                .doneTableColumn();

        //get all the data from db
        Cursor res= easyDB.getAllData();
        while (res.moveToNext()){
            String id=res.getString(1);
            String pId=res.getString(2);
            String name=res.getString(3);
            String price=res.getString(4);
            String cost=res.getString(5);
            String quantity=res.getString(6);

            allTotalPrice= allTotalPrice + Double.parseDouble(cost);
            ModelCartItem modelCartItem= new ModelCartItem(
                    ""+id,
                    ""+pId,
                    ""+name,
                    ""+price,
                    ""+cost,
                    ""+quantity
            );

            cartItemList.add(modelCartItem);

        }
        //setup adapter
        adapterCartItem=new AdapterCartItem(this,cartItemList);
        //set to recycle view
        cartItemsRv.setAdapter(adapterCartItem);
        dFeeTv.setText("৳"+deliveryFee);
        sTotalTv.setText("৳"+String.format("%.2f",allTotalPrice));
        allTotalPriceTv.setText("৳"+(allTotalPrice + Double.parseDouble(deliveryFee.replace("৳", ""))));

        //show dialog
        AlertDialog dialog= builder.create();
        dialog.show();

        //reset total price on dialog dismiss
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                allTotalPrice=0.00;
            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        ///place order
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first validate delivery address
                if (myLatitude.equals("") || myLatitude.equals("null") || myLongitude.equals("") || myLongitude.equals("null")) {
                    //user didn't enter address in profile
                    Toast.makeText(ShopDetails.this, "Please enter address before placing order...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (myPhone.equals("") || myPhone.equals("null")) {
                    //user didn't enter phone number in profile
                    Toast.makeText(ShopDetails.this, "Please enter phone number before placing order...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cartItemList.size()==0){
                    //cart list is empty
                    Toast.makeText(ShopDetails.this, "No Item Is On The Cart", Toast.LENGTH_SHORT).show();
                    return;
                }

                submitOrder();
            }
        });
    }

    private void submitOrder() {
        //show progress dialog
        progressDialog.setMessage("Placing Order...");
        progressDialog.show();

        //for order id and order name
        final String timestamp=""+System.currentTimeMillis();
        String cost=allTotalPriceTv.getText().toString().trim().replace("৳","");//remove ৳ if contains

        //setup order data
        final HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("orderId", ""+timestamp);
        hashMap.put("orderTime", ""+timestamp);
        hashMap.put("orderStatus", "In Progress");
        hashMap.put("orderCost", ""+cost);
        hashMap.put("orderBy", ""+firebaseAuth.getUid());
        hashMap.put("orderTo", ""+shopUid);
        hashMap.put("latitude", ""+myLatitude);
        hashMap.put("longitude", ""+myLongitude);

        //add to db
        final DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users").child(shopUid).child("Orders");
        ref.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //order info added now add order items
                        for(int i=0; i<cartItemList.size();i++){
                            String pId=cartItemList.get(i).getpId();
                            String id=cartItemList.get(i).getId();
                            String cost=cartItemList.get(i).getCost();
                            String name=cartItemList.get(i).getName();
                            String price=cartItemList.get(i).getPrice();
                            String quantity=cartItemList.get(i).getQuantity();

                            HashMap<String, String> hashMap1=new HashMap<>();
                            hashMap1.put("pId",pId);
                            hashMap1.put("name",name);
                            hashMap1.put("cost",cost);
                            hashMap1.put("price",price);
                            hashMap1.put("quantity",quantity);

                            ref.child(timestamp).child("Items").child(pId).setValue(hashMap1);
                        }
                        progressDialog.dismiss();
                        Toast.makeText(ShopDetails.this, "Order Placed Successfully...", Toast.LENGTH_SHORT).show();

                        //after placing order open order details page
                        //open order details,we need two keys there,orderId,orderTo
                        Intent intent=new Intent(ShopDetails.this, OrderDetailsUsers.class);
                        intent.putExtra("orderTo",shopUid);
                        intent.putExtra("orderId",timestamp);
                        startActivity(intent);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ShopDetails.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });

    }


    private void openMap() {
        String address="https//:maps.google.com/maps?saddr=" +myLatitude+","+myLongitude+"&daddr="+shopLatitude+ "," +shopLongitude;
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(address));
        startActivity(intent);
    }

    private void dialPhone() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("Tel:"+Uri.encode(shopName))));
        Toast.makeText(this, ""+shopPhone, Toast.LENGTH_SHORT).show();
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
                     myPhone =""+ds.child( "phone" ).getValue();
                    String profileImage =""+ds.child( "profileImage" ).getValue();
                    String accountType =""+ds.child( "accountType" ).getValue();
                    String city=""+ds.child("city").getValue();
                    myLatitude=""+ds.child("latitude").getValue();
                    myLongitude=""+ds.child("longitude").getValue();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }

    private void loadShopDetails() {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.child(shopUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get shop data
                String name=""+dataSnapshot.child("name").getValue();
                shopName=""+dataSnapshot.child("shopName").getValue();
                shopEmail=""+dataSnapshot.child("email").getValue();
                shopPhone=""+dataSnapshot.child("phone").getValue();
                shopAddress=""+dataSnapshot.child("address").getValue();
                shopLatitude=""+dataSnapshot.child("latitude").getValue();
                shopLongitude=""+dataSnapshot.child("longtitude").getValue();
                deliveryFee=""+dataSnapshot.child("deliveryFee").getValue();
                String profileImage=""+dataSnapshot.child("profileImage").getValue();
                String shopOpen=""+dataSnapshot.child("shopOpen").getValue();

                //set data
                shopNameTv.setText(shopName);
                emailTv.setText(shopEmail);
                phoneTv.setText(shopPhone);
                addressTv.setText(shopAddress);
                deliveryFeeTv.setText("Delivery Fee: ৳"+deliveryFee);
                if(shopOpen.equals("true")){
                    openCloseTv.setText("Open");
                }
                else{

                    openCloseTv.setText("Closed");
                }
                try {
                    Picasso.get().load(profileImage).into(shopIv);
                }
                catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadShopProducts() {
        //intial list
        productsList=new ArrayList<>();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.child(shopUid).child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //clear list before addong item
                        productsList.clear();
                        for (DataSnapshot ds:dataSnapshot.getChildren()){
                            ModelProduct modelProduct=ds.getValue(ModelProduct.class);
                            productsList.add(modelProduct);
                        }
                        //set up adapter
                        adapterProductUser=new AdapterProductUser(ShopDetails.this,productsList);
                        //set adapter
                        productsRv.setAdapter(adapterProductUser);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


}