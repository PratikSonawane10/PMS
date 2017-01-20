package com.pms;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.pms.Adapter.DoneStoreListAdapter;
import com.pms.Adapter.StoreListAdapter;
import com.pms.Connectivity.Fetch_DoneStoreList;
import com.pms.InternetConnectivity.NetworkChangeReceiver;
import com.pms.Model.DoneStoreListItems;
import com.pms.Model.StoreListItems;
import com.pms.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoneStoreList extends BaseActivity {

    public List<DoneStoreListItems> notificationItems = new ArrayList<DoneStoreListItems>();
    RecyclerView recyclerView;
    RecyclerView.Adapter reviewAdapter;
    LinearLayoutManager linearLayoutManager;
    String merchandiserId;
    int saveMerchandiserId;
    ProgressDialog doneStoreListDialog;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_store_list);

        SessionManager sessionManager = new SessionManager(DoneStoreList.this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        merchandiserId = user.get(SessionManager.KEY_MERCHANDISERID);
        saveMerchandiserId = Integer.parseInt(merchandiserId);

        recyclerView = (RecyclerView) findViewById(R.id.doneStoreListRecyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.smoothScrollToPosition(0);
        reviewAdapter = new DoneStoreListAdapter(notificationItems);
        recyclerView.setAdapter(reviewAdapter);

        try {
            doneStoreListDialog = new ProgressDialog(this);
            doneStoreListDialog.setMessage("Fetching storeList please wait...");
            doneStoreListDialog.show();
            Fetch_DoneStoreList fetch_DoneStoreList = new Fetch_DoneStoreList(DoneStoreList.this);
            fetch_DoneStoreList.fetchDoneStoreList(notificationItems,recyclerView, reviewAdapter, saveMerchandiserId ,doneStoreListDialog);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        DoneStoreList.this.finish();
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
        PackageManager pm = DoneStoreList.this.getPackageManager();
        ComponentName component = new ComponentName(DoneStoreList.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = DoneStoreList.this.getPackageManager();
        ComponentName component = new ComponentName(DoneStoreList.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



}
