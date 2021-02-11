package com.example.pharma_aid.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharma_aid.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class register_user extends AppCompatActivity implements LocationListener {

    private ImageView profileIv;
    private EditText nameEt, phoneEt, countryEt, stateEt, cityEt, addressEt, emailEt, passwordEt, cPasswordEt;

    // Permission constants
    private static final int LOCATION_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;


    // IMAGE PICK CONSTANT
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;


    // permission arrays
    private String[] locationPermissions;
    private String[] cameraPermissions;
    private String[] storagePermissions;


    // Image Picked Uri

    private Uri image_uri;


    private double latitude = 0.0, longitude = 0.0;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    // IMAGE PICK CONSTANT


    // permission arrays


    // Image Picked Uri


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        ImageButton backBtn = findViewById(R.id.backBtn);
        ImageButton gpsBtn = findViewById(R.id.gpsBtn);
        profileIv = findViewById(R.id.profileIv);
        nameEt = findViewById(R.id.nameEt);
        phoneEt = findViewById(R.id.phoneEt);
        countryEt = findViewById(R.id.countryEt);
        stateEt = findViewById(R.id.stateEt);
        cityEt = findViewById(R.id.cityEt);
        addressEt = findViewById(R.id.addressEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        cPasswordEt = findViewById(R.id.cPasswordEt);
        Button registerBtn = findViewById(R.id.registerBtn);
        TextView registerSellerTv = findViewById(R.id.registerSellerTv);

        //init permission arrays
        locationPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Detect curremt location
                if (checkLocationPermission()) {
                    // Already Allowed
                    detectLocation();
                } else {
                    // not allowed, request
                    requestLocationPermission();
                }


            }
        });

        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pick image
                showImagePicDialog();
            }

        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // registered user
                inputData();
            }
        });

        registerSellerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(register_user.this, register_seller.class));

            }
        });

    }


    private String fullName, phoneNumber, address, country, city, state, email, password, confirmPassword;

    private void inputData() {
        //input data

        fullName = nameEt.getText().toString().trim();
        phoneNumber = phoneEt.getText().toString().trim();
        country = countryEt.getText().toString().trim();
        state = stateEt.getText().toString().trim();
        city = cityEt.getText().toString().trim();
        address = addressEt.getText().toString().trim();
        email = emailEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();
        confirmPassword = cPasswordEt.getText().toString().trim();

        //validate data

        if (TextUtils.isEmpty(fullName)) {
            Toast.makeText(this, "Enter Name ...", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "Enter Phone Number ...", Toast.LENGTH_SHORT).show();
            return;
        }


        if (latitude == 0.0 || longitude == 0.0) {
            Toast.makeText(this, "Please click The Gps button to detect location ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email pattern ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, " This passswod must be atleast 6 characters long  ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, " This passswod  doesn't match  ", Toast.LENGTH_SHORT).show();
            return;
        }

        createAccount();

    }

    private void createAccount() {
        progressDialog.setMessage("Creating Account....");
        progressDialog.show();


        //Create account
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(AuthResult authResult) {

                // account created
                saveFirebaseData();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Failed creating account

                progressDialog.dismiss();

                Toast.makeText(register_user.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveFirebaseData() {

        progressDialog.setMessage("Saving Account Info...");

        final String timestamp = "" + System.currentTimeMillis();
        if (image_uri == null) {
            // setup data to save
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("uid", "" + firebaseAuth.getUid());
            hashMap.put("email", "" + email);
            hashMap.put("name", "" + fullName);
            hashMap.put("phone", "" + phoneNumber);
            hashMap.put("country", "" + country);
            hashMap.put("state", "" + state);
            hashMap.put("city", "" + city);
            hashMap.put("address", "" + address);
            hashMap.put("latitude", "" + latitude);
            hashMap.put("longitude", "" + longitude);
            hashMap.put("timestamp", "" + timestamp);
            hashMap.put("accountType", "User");
            hashMap.put("online", "true");
            hashMap.put("profileImage", "");


            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(Objects.requireNonNull(firebaseAuth.getUid())).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    // db updated

                    progressDialog.dismiss();
                    startActivity(new Intent(register_user.this, main_user.class));
                    finish();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    // Failed updationg db
                    progressDialog.dismiss();
                    startActivity(new Intent(register_user.this, main_user.class));
                    finish();

                }
            });
        } else {
            // save info with image
            // name and path of image

            String filePathAndName = "profile_image/" + "" + firebaseAuth.getUid();

            // upload image
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadImageUri = uriTask.getResult();

                    if (uriTask.isSuccessful()) {

                        // setup data to save
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("uid", "" + firebaseAuth.getUid());
                        hashMap.put("email", "" + email);
                        hashMap.put("name", "" + fullName);
                        hashMap.put("phone", "" + phoneNumber);
                        hashMap.put("country", "" + country);
                        hashMap.put("state", "" + state);
                        hashMap.put("city", "" + city);
                        hashMap.put("address", "" + address);
                        hashMap.put("latitude", "" + latitude);
                        hashMap.put("longitude", "" + longitude);
                        hashMap.put("timestamp", "" + timestamp);
                        hashMap.put("accountType", "User");
                        hashMap.put("online", "true");
                        //hashMap.put("shopOpen", "true");
                        hashMap.put("profileImage", "" + downloadImageUri); // url of uploaded image

                        // Save to db

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                        ref.child(firebaseAuth.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                // db updated

                                progressDialog.dismiss();
                                startActivity(new Intent(register_user.this, main_user.class));
                                finish();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                // Failed updationg db
                                progressDialog.dismiss();
                                startActivity(new Intent(register_user.this, main_user.class));
                                finish();

                            }
                        });


                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    progressDialog.dismiss();
                    Toast.makeText(register_user.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        }


    }


    private void showImagePicDialog() {
        // options to display in dialog

        String[] options = {"Camera", "Gallery"};

        // dialog


        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setTitle("Pick Image").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                // handle clicks
                if (which == 0) {
                    // Camera Clicked
                    if (checkCameraPermission()) {
                        //camera permission allowed
                        pickFromCamera();
                    } else {
                        // camera permisssion not allowed
                        requestCameraPermission();
                    }

                } else {
                    // Gallery clicked
                    if (checkStoragePermission()) {
                        // storage permission allowed
                        pickFromGallery();
                    } else {
                        // storage permisssion not allowed
                        requestStoragePermission();
                    }


                }

            }
        }).show();

    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);

    }

    private void pickFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image Description");


        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);


    }


    private void detectLocation() {
        Toast.makeText(this, "please wait..", Toast.LENGTH_LONG).show();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }


    //check location permission
    private boolean checkLocationPermission()
    {
        boolean result= ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestLocationPermission()
    {
        ActivityCompat.requestPermissions(this,locationPermissions,LOCATION_REQUEST_CODE);
    }


    private boolean checkStoragePermission()
    {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE )==(PackageManager.PERMISSION_GRANTED);

        return result;

    }

    private void requestStoragePermission()
    {
        ActivityCompat.requestPermissions( this ,storagePermissions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission()
    {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);

        return result && result1;

    }

    private void requestCameraPermission()
    {

        ActivityCompat.requestPermissions( this ,cameraPermissions,CAMERA_REQUEST_CODE);

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        //location detected
        latitude=location.getLatitude();
        longitude=location.getLongitude();

        findAddress();


    }

    private void findAddress() {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try{
            addresses =geocoder.getFromLocation(latitude,longitude, 1);
            String address =addresses.get(0).getAddressLine(0);  // complete address
            String city = addresses.get(0).getLocality();
            String state= addresses.get(0).getAdminArea();
            String country=addresses.get(0).getCountryName();

            // Set Addresses

            countryEt.setText(country);
            stateEt.setText(state);
            cityEt.setText(city);
            addressEt.setText(address);

        }
        catch (Exception e)
        {
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        //gps location disabled

        Toast.makeText(this,"Please turn on location",Toast.LENGTH_LONG).show();

    }



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case LOCATION_REQUEST_CODE:{
                if(grantResults.length>0)
                {
                    boolean locationAcccepted =grantResults[0] ==PackageManager.PERMISSION_GRANTED;
                    if(locationAcccepted)
                    {
                        // Permission Allowed
                        detectLocation();

                    }
                    else
                    {
                        // Permission Not Allowed
                        Toast.makeText(this,"Location Permission is necessary ...." , Toast.LENGTH_SHORT).show();

                    }
                }
            }
            break;

            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0)
                {
                    boolean cameraAccepted =grantResults[0] ==PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted =grantResults[0] ==PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted &&  storageAccepted)
                    {
                        // Permission Allowed
                        pickFromCamera();

                    }
                    else
                    {
                        // Permission Not Allowed
                        Toast.makeText(this,"Camera  Permission is necessary ...." , Toast.LENGTH_SHORT).show();

                    }
                }
            }
            break;

            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>0)
                {

                    boolean storageAccepted =grantResults[0] ==PackageManager.PERMISSION_GRANTED;
                    if( storageAccepted)
                    {
                        // Permission Allowed
                        pickFromGallery();

                    }
                    else
                    {
                        // Permission Not Allowed
                        Toast.makeText(this,"Storage permission is   necessary ...." , Toast.LENGTH_SHORT).show();

                    }
                }
            }
            break;
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==RESULT_OK)
        {
            if(requestCode == IMAGE_PICK_GALLERY_CODE)
            {
                // get picked image
                image_uri= data.getData();
                // set to imageview

                profileIv.setImageURI( image_uri );
            }

            else if(requestCode== IMAGE_PICK_CAMERA_CODE)
            {


                // set to imageview
                profileIv.setImageURI( image_uri );
            }
        }
        super.onActivityResult( requestCode, resultCode, data );
    }







































}