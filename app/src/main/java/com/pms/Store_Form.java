package com.pms;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.pms.Connectivity.AddNewQuestion;
import com.pms.SessionManager.SessionManager;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

public class Store_Form extends BaseActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    boolean doubleBackToExitPressedOnce = false;

    RelativeLayout allFieldRelativeLayout;

    String merchandiserId;
    CardView marchandizingeRelativeLayout;
    CardView stockRelativeLayout;
    CardView feedbackRelativeLayout;

    //TextView txtShopOpen;
    //Switch switchShopOpen;
    RelativeLayout formPart1RelativeLayout;
    RelativeLayout formPart2RelativeLayout;
    RelativeLayout formPart3RelativeLayout;

    Button submit;
    //field of form 1
    EditText txtDanglers;
    EditText txtPosters;
    EditText txtBanners;
    EditText txtAnyOther;
    Switch switchWindowMarchandizing;
    Switch switchWindowShelfSpace;
    Switch switchFacingsInCategory;

    //fields of form 2
    EditText txtStr;
    EditText txtStw;
    EditText txtStg;
    EditText txtShw;
    EditText txtChandrika;
    EditText txtLed;

    //feilds of form 3
    EditText txtIssueOfHighLight;
    EditText txtExitWithoutExecution;
    EditText txtReasonForNonCooperation;
    Switch switchWorkedWithStarSalesman;
    ProgressDialog uploadStoreDialogBox;

    String Store_Id;
    String Store_Name;
    String Store_Address;
    String Beat_Id;

    int countForm1 = 0;
    int countForm2 = 0;
    int countForm3 = 0;

    int saveUserId;
    boolean saveWindowMerchandising;
    int saveNoofDanglers;
    int saveNoofPosters;

    String saveBanners;
    String saveAnyOther;
    boolean saveWindowShelfSpaces;
    boolean saveFourfacingsIncategoryshelf;
    String saveSTR125gx4;
    String saveSTW;
    String saveSBS;
    String saveGLD;
    String saveWCFBottle;
    String saveSBL;
    String saveIssueToHighlight;
    String saveExitWithoutExecnapprvBy;
    String saveReasonNonCooperationofoutlet;

    boolean saveWorkedwithStarSalesman;
    String saveImages;
    public String saveLongitude;
    public String saveLatitude;
    int saveRetailerId;
    int saveBeatId;
    boolean storeOpen = true;

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private GoogleApiClient googleApiClient;
    private LocationManager locManageer;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    double lat;
    double lon;

    protected static final String TAG = "Store_Form";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_form);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }

//        locManageer =(LocationManager)this.getSystemService(this.LOCATION_SERVICE);
//        try {
//            gps_enabled = locManageer.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        } catch(Exception ex) {}
//
//        try {
//            network_enabled = locManageer.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        } catch(Exception ex) {}
//        if(!gps_enabled && !network_enabled) {
//            showSettingsAlert();
//        }
        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        merchandiserId = user.get(SessionManager.KEY_MERCHANDISERID);

        Intent intent = getIntent();
        if (null != intent) {
            Beat_Id = intent.getStringExtra("beatID");
            Store_Id = intent.getStringExtra("shopID");
            Store_Name = intent.getStringExtra("shopName");
            Store_Address = intent.getStringExtra("Address");
        }

        getAllControls();
        checkDefaultsValues();
        checkSwitchChangeListiner();
    }

    public void showSettingsAlert() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(Store_Form.class.getSimpleName(), "Connected to Google Play Services!");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locManageer =(LocationManager)this.getSystemService(this.LOCATION_SERVICE);
            try {
                gps_enabled = locManageer.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch(Exception ex) {}

            try {
                network_enabled = locManageer.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch(Exception ex) {}
            if(!gps_enabled && !network_enabled) {
                showSettingsAlert();
            }

            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            if(lastLocation == null){
                Toast.makeText(this, "Cant Find Your Location.", Toast.LENGTH_SHORT).show();
            }else{
                lat = lastLocation.getLatitude();
                lon = lastLocation.getLongitude();

                saveLatitude = String.valueOf(lat);
                saveLongitude = String.valueOf(lon);
                //Toast.makeText(this, "location:  "+lat + "and  "+lon, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(Store_Form.class.getSimpleName(), "Can't connect to Google Play Services!");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.formPart1RelativeLayout) {

            if (countForm1 == 0) {
                marchandizingeRelativeLayout.setVisibility(View.VISIBLE);
                countForm1 = 1;
            } else {
                marchandizingeRelativeLayout.setVisibility(View.GONE);
                countForm1 = 0;
            }
        } else if (v.getId() == R.id.formPart2RelativeLayout) {

            if (countForm2 == 0) {
                stockRelativeLayout.setVisibility(View.VISIBLE);
                countForm2 = 1;
            } else {
                stockRelativeLayout.setVisibility(View.GONE);
                countForm2 = 0;
            }
        } else if (v.getId() == R.id.formPart3RelativeLayout) {

            if (countForm3 == 0) {
                feedbackRelativeLayout.setVisibility(View.VISIBLE);
                countForm3 = 1;
            } else {
                feedbackRelativeLayout.setVisibility(View.GONE);
                countForm3 = 0;
            }
        } else if (v.getId() == R.id.submit) {
//            if(storeOpen == true){
                if (txtDanglers.getText().toString().isEmpty() || txtPosters.getText().toString().isEmpty()
                        || txtBanners.getText().toString().isEmpty() || txtAnyOther.getText().toString().isEmpty()
                        || txtStr.getText().toString().isEmpty() || txtStw.getText().toString().isEmpty()
                        || txtStg.getText().toString().isEmpty() || txtShw.getText().toString().isEmpty()
                        || txtChandrika.getText().toString().isEmpty() || txtLed.getText().toString().isEmpty()
                        || txtIssueOfHighLight.getText().toString().isEmpty() || txtExitWithoutExecution.getText().toString().isEmpty()
                        || txtReasonForNonCooperation.getText().toString().isEmpty()) {
                    Toast.makeText(Store_Form.this, "All Details are neccessory", Toast.LENGTH_LONG).show();
                }
                else {
                    collectAllData();
                    uploadStoreDialogBox = new ProgressDialog(this);
                    uploadStoreDialogBox.setMessage("Upoading all details please wait...");
                    uploadStoreDialogBox.show();
                    AddNewQuestion addNewQuestion = new AddNewQuestion(this);
                    addNewQuestion.UploadAddNewQuestion(
                            saveUserId, saveWindowMerchandising, saveNoofDanglers, saveNoofPosters,
                            saveBanners, saveAnyOther, saveWindowShelfSpaces, saveFourfacingsIncategoryshelf,
                            saveSTR125gx4, saveSTW, saveSBS, saveGLD, saveWCFBottle, saveSBL, saveIssueToHighlight,
                            saveExitWithoutExecnapprvBy, saveReasonNonCooperationofoutlet,saveWorkedwithStarSalesman,
                            saveImages, saveLongitude,saveLatitude,saveRetailerId,saveBeatId,storeOpen, uploadStoreDialogBox);
                }
//            }
//            else if(storeOpen == false) {
//                collectStoreCloseData();
//                uploadStoreDialogBox = new ProgressDialog(this);
//                uploadStoreDialogBox.setMessage("Upoading all details please wait...");
//                uploadStoreDialogBox.show();
//                AddNewQuestion addNewQuestion = new AddNewQuestion(this);
//                addNewQuestion.UploadAddNewQuestion(
//                        saveUserId, saveWindowMerchandising, saveNoofDanglers, saveNoofPosters,
//                        saveBanners, saveAnyOther, saveWindowShelfSpaces, saveFourfacingsIncategoryshelf,
//                        saveSTR125gx4, saveSTW, saveSBS, saveGLD, saveWCFBottle, saveSBL, saveIssueToHighlight,
//                        saveExitWithoutExecnapprvBy, saveReasonNonCooperationofoutlet,saveWorkedwithStarSalesman,
//                        saveImages, saveLongitude,saveLatitude,saveRetailerId,saveBeatId,storeOpen, uploadStoreDialogBox);
//
//            }
        }
    }

    private void getAllControls() {

        marchandizingeRelativeLayout = (CardView) findViewById(R.id.marchandizingeRelativeLayout);
        stockRelativeLayout = (CardView) findViewById(R.id.stockRelativeLayout);
        feedbackRelativeLayout = (CardView) findViewById(R.id.feedbackRelativeLayout);
        allFieldRelativeLayout = (RelativeLayout) findViewById(R.id.allfieldsRelativeLayout);
        formPart1RelativeLayout = (RelativeLayout) findViewById(R.id.formPart1RelativeLayout);
        formPart2RelativeLayout = (RelativeLayout) findViewById(R.id.formPart2RelativeLayout);
        formPart3RelativeLayout = (RelativeLayout) findViewById(R.id.formPart3RelativeLayout);

        // txtStoreAddress=(TextView) findViewById(R.id.storeAddress);
        // txtStoreName=(TextView) findViewById(R.id.storeName);
        //txtShopOpen = (TextView) findViewById(R.id.txtShopOpen);
        //switchShopOpen = (Switch) findViewById(R.id.switchShopOpen);

        txtDanglers = (EditText) findViewById(R.id.txtDanglers);
        txtPosters = (EditText) findViewById(R.id.txtPosters);
        txtBanners = (EditText) findViewById(R.id.txtBanners);
        txtAnyOther = (EditText) findViewById(R.id.txtAnyOther);

        switchWindowMarchandizing = (Switch) findViewById(R.id.switchWindowMarchandizing);
        switchWindowShelfSpace = (Switch) findViewById(R.id.switchWindowShelfSpace);
        switchFacingsInCategory = (Switch) findViewById(R.id.switchFacingsInCategory);

        txtStr = (EditText) findViewById(R.id.txtStr);
        txtStw = (EditText) findViewById(R.id.txtStw);
        txtStg = (EditText) findViewById(R.id.txtStg);
        txtShw = (EditText) findViewById(R.id.txtShw);
        txtChandrika = (EditText) findViewById(R.id.txtChandrika);
        txtLed = (EditText) findViewById(R.id.txtLed);

        txtIssueOfHighLight = (EditText) findViewById(R.id.txtIssueOfHighLight);
        txtExitWithoutExecution = (EditText) findViewById(R.id.txtExitWithoutExecution);
        txtReasonForNonCooperation = (EditText) findViewById(R.id.txtReasonForNonCooperation);
        switchWorkedWithStarSalesman = (Switch) findViewById(R.id.switchWorkedWithStarSalesman);
        submit = (Button) findViewById(R.id.submit);

        formPart1RelativeLayout.setOnClickListener(this);
        formPart2RelativeLayout.setOnClickListener(this);
        formPart3RelativeLayout.setOnClickListener(this);

        //switchShopOpen.setChecked(true);
        marchandizingeRelativeLayout.setVisibility(View.GONE);
        stockRelativeLayout.setVisibility(View.GONE);
        feedbackRelativeLayout.setVisibility(View.GONE);
        submit.setOnClickListener(this);
    }

    private void checkDefaultsValues() {

//        if(switchShopOpen.isChecked()){
//            switchShopOpen.setText("YES");
//            allFieldRelativeLayout.setVisibility(View.VISIBLE);
//            storeOpen = true;
//        }
//        else{
//            switchShopOpen.setText("NO");
//            allFieldRelativeLayout.setVisibility(View.GONE);
//            storeOpen = false;
//        }

        if(switchWindowMarchandizing.isChecked()){
            switchWindowMarchandizing.setText("YES");
            saveWindowMerchandising = true;
        }
        else{
            switchWindowMarchandizing.setText("NO");
            saveWindowMerchandising = false;
        }

        if(switchWindowShelfSpace.isChecked()){
            switchWindowShelfSpace.setText("YES");
            saveWindowShelfSpaces = true;
        }
        else{
            switchWindowShelfSpace.setText("NO");
            saveWindowShelfSpaces = false;
        }

        if(switchFacingsInCategory.isChecked()){
            switchFacingsInCategory.setText("YES");
            saveFourfacingsIncategoryshelf = true;
        }
        else{
            switchFacingsInCategory.setText("NO");
            saveFourfacingsIncategoryshelf = false;
        }

        if(switchWorkedWithStarSalesman.isChecked()){
            switchWorkedWithStarSalesman.setText("YES");
            saveWorkedwithStarSalesman = true;
        }
        else{
            switchWorkedWithStarSalesman.setText("NO");
            saveWorkedwithStarSalesman = false;
        }
    }

    private void checkSwitchChangeListiner() {

//        switchShopOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,
//                                         boolean isChecked) {
//                if(isChecked){
//                    switchShopOpen.setText("YES");
//                    allFieldRelativeLayout.setVisibility(View.VISIBLE);
//                    storeOpen = true;
//                }
//                else{
//                    switchShopOpen.setText("NO");
//                    allFieldRelativeLayout.setVisibility(View.GONE);
//                    storeOpen = false;
//                }
//            }
//        });
        switchWindowMarchandizing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    switchWindowMarchandizing.setText("YES");
                    saveWindowMerchandising = true;
                }
                else{
                    switchWindowMarchandizing.setText("NO");
                    saveWindowMerchandising = false;
                }
            }
        });
        switchWindowShelfSpace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    switchWindowShelfSpace.setText("YES");
                    saveWindowShelfSpaces = true;
                }
                else{
                    switchWindowShelfSpace.setText("NO");
                    saveWindowShelfSpaces = false;
                }
            }
        });
        switchFacingsInCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    switchFacingsInCategory.setText("YES");
                    saveFourfacingsIncategoryshelf = true;
                }
                else{
                    switchFacingsInCategory.setText("NO");
                    saveFourfacingsIncategoryshelf = false;
                }
            }
        });
        switchWorkedWithStarSalesman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    switchWorkedWithStarSalesman.setText("YES");
                    saveWorkedwithStarSalesman = true;
                }
                else{
                    switchWorkedWithStarSalesman.setText("NO");
                    saveWorkedwithStarSalesman = false;
                }
            }
        });
    }

    private void collectAllData() {
        saveUserId = Integer.parseInt(merchandiserId);
        saveNoofDanglers = Integer.parseInt(txtDanglers.getText().toString());
        saveNoofPosters = Integer.parseInt(txtPosters.getText().toString());
        saveBanners = txtBanners.getText().toString();
        saveAnyOther = txtAnyOther.getText().toString();
        saveSTR125gx4 = txtStr.getText().toString();
        saveSTW = txtStw.getText().toString();
        saveSBS = txtStg.getText().toString();
        saveGLD = txtShw.getText().toString();
        saveWCFBottle = txtChandrika.getText().toString();
        saveSBL = txtLed.getText().toString();
        saveIssueToHighlight = txtIssueOfHighLight.getText().toString();
        saveExitWithoutExecnapprvBy = txtExitWithoutExecution.getText().toString();
        saveReasonNonCooperationofoutlet = txtReasonForNonCooperation.getText().toString();
        saveImages = "";
        saveRetailerId = Integer.parseInt(Store_Id);
        saveBeatId = Integer.parseInt(Beat_Id);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
