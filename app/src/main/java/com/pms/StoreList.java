package com.pms;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.pms.Adapter.StoreListAdapter;
import com.pms.Connectivity.Fetch_StoreList;
import com.pms.InternetConnectivity.NetworkChangeReceiver;
import com.pms.Model.StoreListItems;
import com.pms.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StoreList extends BaseActivity {

    public List<StoreListItems> storeListItems = new ArrayList<StoreListItems>();
    RecyclerView recyclerView;
    RecyclerView.Adapter reviewAdapter;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog storeListDialogBox;

    int saveMerchandiserId;
    boolean doubleBackToExitPressedOnce = false;
    private int current_page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        String merchandiserId= user.get(SessionManager.KEY_MERCHANDISERID);
        saveMerchandiserId = Integer.parseInt(merchandiserId);

        recyclerView = (RecyclerView) findViewById(R.id.storeListRecyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.smoothScrollToPosition(0);
        reviewAdapter = new StoreListAdapter(storeListItems);
        recyclerView.setAdapter(reviewAdapter);

        try {
            storeListDialogBox = new ProgressDialog(this);
            storeListDialogBox.setMessage("Fetching storeList please wait...");
            storeListDialogBox.show();

            Fetch_StoreList fetch_Notification = new Fetch_StoreList(StoreList.this);
            fetch_Notification.fetchStoreList(storeListItems,recyclerView, reviewAdapter,saveMerchandiserId, storeListDialogBox);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        StoreList.this.finish();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = StoreList.this.getPackageManager();
        ComponentName component = new ComponentName(StoreList.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = StoreList.this.getPackageManager();
        ComponentName component = new ComponentName(StoreList.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
