package com.pms.Connectivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.pms.Home;
import com.pms.Model.CheckSelfieModel;
import com.pms.Model.StoreListItems;
import com.pms.SessionManager.SessionManager;
import com.pms.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckSelfie {


    private static String response;
    private static int merchantId;
    private static Context context;
    private static String checkselfieMethName = "CheckSelfe";


    public CheckSelfie(Home home) {
        context=home;
    }

    public void CheckSelfie(int saveMerchandiserId) {
        merchantId = saveMerchandiserId;
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            response = WebService.checkSelfie(merchantId, checkselfieMethName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            // if(!displayText.equals("Invalid Notification") || displayText.equals("No Network Found")) {
            if(response.equals("Invalid Notification") || response.equals("No Network Found")) {
             //   progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage(" unable to fetch Selfie Status");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                try {
                    //CheckSelfieModel checkSelfieModel = new CheckSelfieModel();
//                    checkSelfieModel.setSelfie(response);
                    SessionManager session = new SessionManager(context);
                    session.checkSelftSession(response);


                }

                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}
