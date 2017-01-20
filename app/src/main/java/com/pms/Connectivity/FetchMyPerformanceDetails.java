package com.pms.Connectivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pms.Model.MyPerformanceListItem;
import com.pms.Model.StoreListItems;
import com.pms.SessionManager.SessionManager;
import com.pms.StoreList;
import com.pms.TabAttendance;
import com.pms.TabIncentive;
import com.pms.TabShope;
import com.pms.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FetchMyPerformanceDetails {

    private static Context context;
    private static final String url="";
    private static String displayText;
    private static ProgressDialog progressDialog;
    private static RecyclerView.Adapter adapterForAsyncTask;
    private static RecyclerView recyclerViewForAsyncTask;
    private static List<MyPerformanceListItem> MyPerformanceListItemsArrayForAsyncTask;

    private static SessionManager sessionManager;
    private static int merchandiserId;
    private static String myPerformanceMethName = "MonthlyReport";

    public FetchMyPerformanceDetails(TabAttendance v) {
        context = v.getContext();
    }

//    public FetchMyPerformanceDetails(TabShope v) {
//        context = v.getContext();
//    }
//
//    public FetchMyPerformanceDetails(TabIncentive v) {
//        context = v.getContext();
//    }



    public static void PerformanceDetails (List<MyPerformanceListItem> MyPerformanceListItemsArray, RecyclerView recyclerView, RecyclerView.Adapter reviewAdapter, int saveMerchandiserId, ProgressDialog myPerformanceDialogBox) {
        adapterForAsyncTask = reviewAdapter;
        recyclerViewForAsyncTask = recyclerView;
        MyPerformanceListItemsArrayForAsyncTask = MyPerformanceListItemsArray;
        merchandiserId  = saveMerchandiserId;
        progressDialog = myPerformanceDialogBox;

        AsyncCallWS1 task1 = new AsyncCallWS1();
        task1.execute();
    }

    private static class AsyncCallWS1 extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.FetchPerformanceDetails(merchandiserId, myPerformanceMethName);
            progressDialog.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            progressDialog.dismiss();
            if(displayText.equals("Invalid Details") || displayText.equals("No Network Found")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage(" Unable To Fetch Details Please Try Again.");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                try {

                    JSONArray jsonArray = new JSONArray(displayText);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            MyPerformanceListItem myPerformanceListItems = new MyPerformanceListItem();
                            myPerformanceListItems.setMonth(obj.getString("Month"));
                            myPerformanceListItems.setNoOfWorkingDays(obj.getString("NoOfWorkingDays"));
                            myPerformanceListItems.setsActualNoOfWorkingDays(obj.getString("ActualNoOfWorkingDays"));
                            myPerformanceListItems.setNoOfShop(obj.getString("NoOfShops"));
                            myPerformanceListItems.setActualNoOfShops(obj.getString("ActualNoOfShops"));
                            myPerformanceListItems.setIncentiveAmount(obj.getString("IncentiveAmount"));
                            myPerformanceListItems.setPercentNoOfWorkingDays(obj.getString("PercentNoOfWorkingDays"));
                            myPerformanceListItems.setPercentNoOfShopes(obj.getString("PercentNoOfShopes"));

                            MyPerformanceListItemsArrayForAsyncTask.add(myPerformanceListItems);
                            adapterForAsyncTask.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}
