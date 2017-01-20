package com.pms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pms.Adapter.PerFormancAttendaceListAdapter;
import com.pms.Adapter.PerFormanceShopeListAdapter;
import com.pms.Connectivity.FetchMyPerformanceDetails;
import com.pms.Model.MyPerformanceListItem;
import com.pms.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TabAttendance  extends Fragment implements View.OnClickListener{

    ProgressDialog progressDialog;
    List<MyPerformanceListItem> myPerformanceListItem = new ArrayList<MyPerformanceListItem>();
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    String userId;
    LinearLayoutManager linearLayoutManager;

    int current_page = 1;
    int value;
    private View inflatedView;
    private View v;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_attendance, container, false);
        return v;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SessionManager sessionManager = new SessionManager(v.getContext().getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        userId = user.get(SessionManager.KEY_MERCHANDISERID);

        int idofUser = Integer.parseInt(userId);

        recyclerView = (RecyclerView) getView().findViewById(R.id.attendanceList);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new PerFormancAttendaceListAdapter(myPerformanceListItem);
        recyclerView.setAdapter(adapter);
        progressDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        try {
            FetchMyPerformanceDetails fetMyPerformanceDetails = new FetchMyPerformanceDetails(TabAttendance.this);
            fetMyPerformanceDetails.PerformanceDetails(myPerformanceListItem,recyclerView, adapter, idofUser, progressDialog);
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }


    @Override
    public void onClick(View v) {


    }
}
