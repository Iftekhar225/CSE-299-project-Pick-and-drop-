package com.example.pharma_aid.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pharma_aid.R;
import com.example.pharma_aid.adapters.AdapterOrderedItems;
import com.example.pharma_aid.models.ModelOrderedItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OrderDetailsUsers extends AppCompatActivity {

    private String orderTo,orderId;

    //ui views
    private ImageButton backBtn,writeReviewButton;
    private TextView orderIdTv,dateTv,orderStatusTv,shopNameTv,totalItemsTv,amountTv,addressTv;
    private RecyclerView itemsRv;
    private FirebaseAuth firebaseAuth;

    private ArrayList<ModelOrderedItems> orderedItemsArrayList;
    private AdapterOrderedItems adapterOrderedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_users);

        //initialize ui views
        backBtn= findViewById(R.id.backBtn);
        orderIdTv= findViewById(R.id.orderIdTv);
        dateTv= findViewById(R.id.dateTv);
        orderStatusTv= findViewById(R.id.orderStatusTv);
        shopNameTv= findViewById(R.id.shopNameTv);
        totalItemsTv= findViewById(R.id.totalItemsTv);
        amountTv= findViewById(R.id.amountTv);
        addressTv= findViewById(R.id.addressTv);
        itemsRv= findViewById(R.id.itemsRv);
        writeReviewButton= findViewById(R.id.writeReviewButton);

        final Intent intent=getIntent();
        orderTo=intent.getStringExtra("orderTo");//orderTo contains uid of the shop where we placed order
        orderId=intent.getStringExtra("orderId");
        firebaseAuth = FirebaseAuth.getInstance();
        loadShopInfo();
        loadOrderDetails();
        loadOrderedItems();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        //handle review button
        writeReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(OrderDetailsUsers.this,writeReview.class);
                intent1.putExtra("shopUid",orderTo); //to write review need the shop uid
                startActivity(intent1);
            }
        });


    }

    private void loadOrderedItems() {
        //initalize list
        orderedItemsArrayList = new ArrayList<>();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
        ref.child(orderTo).child("Orders").child(orderId).child("Items")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        orderedItemsArrayList.clear();//before loading clear the lists
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            ModelOrderedItems modelOrderedItems=ds.getValue(ModelOrderedItems.class);
                            //add to lists
                            orderedItemsArrayList.add(modelOrderedItems);
                        }
                        //all items added to the list
                        //setup adapter
                        adapterOrderedItems= new AdapterOrderedItems(OrderDetailsUsers.this, orderedItemsArrayList);
                        //set the adapter
                        itemsRv.setAdapter(adapterOrderedItems);

                        //set items count
                        totalItemsTv.setText(""+dataSnapshot.getChildrenCount());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void loadOrderDetails() {
        //load order details

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.child(orderTo).child("Orders").child(orderId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String orderBy=""+dataSnapshot.child("orderBy").getValue();
                        String orderCost=""+dataSnapshot.child("orderCost").getValue();
                        String orderId=""+dataSnapshot.child("orderId").getValue();
                        String orderStatus=""+dataSnapshot.child("orderStatus").getValue();
                        String orderTime=""+dataSnapshot.child("orderTime").getValue();
                        String orderTo=""+dataSnapshot.child("orderTo").getValue();
                        String deliveryFee=""+dataSnapshot.child("deliveryFee").getValue();
                        String latitude=""+dataSnapshot.child("latitude").getValue();
                        String longitude=""+dataSnapshot.child("longitude").getValue();

                        //convert timestamp to proper format
                        Calendar calendar=Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(orderTime));
                        String formatedDate= DateFormat.format("DD/MM/YYYY HH:MM a",calendar).toString();
                        if(orderStatus.equals("In Progress")){
                            orderStatusTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else if (orderStatus.equals("Completed")){
                            orderStatusTv.setTextColor(getResources().getColor(R.color.colorGreen));
                        }
                        else if (orderStatus.equals("Cancelled")){
                            orderStatusTv.setTextColor(getResources().getColor(R.color.colorRed));
                        }

                        //set data
                        orderIdTv.setText(orderId);
                        orderStatusTv.setText(orderStatus);
                        amountTv.setText("৳"+orderCost+"[Including delivery fee ৳"+deliveryFee+"]");
                        dateTv.setText(formatedDate);

                        findAddress(latitude,longitude);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


    private void loadShopInfo() {
        //get shop info

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
        ref.child(orderTo)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String shopName=""+dataSnapshot.child("shopName").getValue();
                        shopNameTv.setText(shopName);

                    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void findAddress(String latitude, String longitude) {
        double lat=Double.parseDouble(latitude);
        double lon=Double.parseDouble(longitude);

        //find address,country,state,city
        Geocoder geocoder;
        List<Address> addresses;

        geocoder= new Geocoder(this, Locale.getDefault());

        try {
            addresses=geocoder.getFromLocation(lat,lon,1);
            String address=addresses.get(0).getAddressLine(0); //complete address
            addressTv.setText(address);
        }
        catch (Exception e){

        }
    }
}