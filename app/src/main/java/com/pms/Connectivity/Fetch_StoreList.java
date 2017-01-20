package com.pms.Connectivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.pms.Model.StoreListItems;
import com.pms.SessionManager.SessionManager;
import com.pms.StoreList;
import com.pms.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class Fetch_StoreList {
    private static Context context;
    private static final String url="";
    private static String displayText;
    private static ProgressDialog progressDialog;
    private static RecyclerView.Adapter adapterForAsyncTask;
    private static RecyclerView recyclerViewForAsyncTask;
    private static List<StoreListItems> storeListItemsArrayForAsyncTask;

    private static SessionManager sessionManager;
    private static int merchandiserId;
    private static String oldGiftId;
    private static String storeListMethName = "ShopList";

    public Fetch_StoreList(StoreList storeList) {
        context = storeList;
    }

    public static void fetchStoreList(List<StoreListItems> storeListItemsArray, RecyclerView recyclerView, RecyclerView.Adapter reviewAdapter, int saveMerchandiserId, ProgressDialog storeListDialogBox) {
        adapterForAsyncTask = reviewAdapter;
        recyclerViewForAsyncTask = recyclerView;
        storeListItemsArrayForAsyncTask = storeListItemsArray;
        merchandiserId  = saveMerchandiserId;
        progressDialog = storeListDialogBox;

        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.allStoreList(merchandiserId, storeListMethName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
           // if(!displayText.equals("Invalid Notification") || displayText.equals("No Network Found")) {
            if(displayText.equals("Invalid Notification") || displayText.equals("No Network Found")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage(" unable to fetch store list please try again.");
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

                            StoreListItems storeListItems = new StoreListItems();
                            storeListItems.setshopID(obj.getString("ShopID"));
                            storeListItems.setbeatID(obj.getString("BeatID"));
                            storeListItems.setshopName(obj.getString("ShopName"));
                            storeListItems.setAddress(obj.getString("Address"));
                            storeListItems.setcolor(obj.getString("color"));
                            storeListItems.setstatus(obj.getString("status"));

                            storeListItemsArrayForAsyncTask.add(storeListItems);
                          //  recyclerViewForAsyncTask.setAdapter(adapterForAsyncTask);
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
