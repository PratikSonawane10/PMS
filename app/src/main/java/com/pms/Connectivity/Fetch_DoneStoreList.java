package com.pms.Connectivity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.pms.Adapter.DoneStoreListAdapter;
import com.pms.DoneStoreList;
import com.pms.Model.DoneStoreListItems;
import com.pms.Model.StoreListItems;
import com.pms.SessionManager.SessionManager;
import com.pms.StoreList;
import com.pms.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Fetch_DoneStoreList {
    private static Context context;
    private static final String url="";
    private static String displayText;
    private static RecyclerView.Adapter adapterForAsyncTask;
    private static RecyclerView recyclerViewForAsyncTask;
    private static List<DoneStoreListItems> doneStoreListItems;

    private static SessionManager sessionManager;
    private static int merchandiserId;
    private static String oldGiftId;
    private static String webMethName = "TodayWork";
    private static ProgressDialog progressDialog;

    public Fetch_DoneStoreList(DoneStoreList doneStoreList) {
        context = doneStoreList;
    }

    public static void fetchDoneStoreList(List<DoneStoreListItems> doneStoreList, RecyclerView recyclerView, RecyclerView.Adapter reviewAdapter, int saveMerchandiserId, ProgressDialog doneStoreListDialog) {
        adapterForAsyncTask = reviewAdapter;
        recyclerViewForAsyncTask= recyclerView;
        doneStoreListItems = doneStoreList;
        merchandiserId = saveMerchandiserId;
        progressDialog = doneStoreListDialog;
        Fetch_DoneStoreList.AsyncCallWS task = new Fetch_DoneStoreList.AsyncCallWS();
        task.execute();

    }
    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.doneStoreList(merchandiserId,webMethName);
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            // if(!displayText.equals("Invalid Notification") || displayText.equals("No Network Found")) {
            if(displayText.equals("Invalid Notification") || displayText.equals("No Network Found")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("There is some problem please try again.");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }else if(displayText.equals("No Shop Cover Today")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("There is some problem please try again.");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                try {
                    progressDialog.dismiss();
                    JSONArray jsonArray = new JSONArray(displayText);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            DoneStoreListItems listItems = new DoneStoreListItems();
                            //doneStoreListItems.setshopID(obj.getString("ShopID"));
                            //doneStoreListItems.setbeatID(obj.getString("BeatID"));
                            listItems.setdoneShopName(obj.getString("ShopName"));
                            listItems.setdoneAddress(obj.getString("Address"));
                            listItems.setdoneColor(obj.getString("color"));

                            doneStoreListItems.add(listItems);
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
