package com.pms.Connectivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import com.pms.Home;
import com.pms.WebService;

public class AddSelfieImage {

    private static Context context;
    private static String responseResult;
    private static String webMethodName = "AddSelfie";
    private static int merchandiserId;
    private static String photo;
    private static ProgressDialog progressDialog;

    public AddSelfieImage(Home home) {
        context = home;
    }

    public void UplaodSelfieImage(String saveSelfieImage, int saveMerchandiserId, ProgressDialog photoUploadDialogBox) {

        photo = saveSelfieImage;
        merchandiserId = saveMerchandiserId;
        progressDialog = photoUploadDialogBox;
        AddImageAsyncCallWS task = new AddImageAsyncCallWS();
        task.execute();
    }

    private static class AddImageAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            responseResult = WebService.AddSelfie(merchandiserId, photo, webMethodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (responseResult.equals("Error occured")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage(responseResult);
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else if (responseResult.equals("Selfie added successfully.")) {
                try {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Result");
                    builder.setMessage("Selfie Added Successfully");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface alert, int which) {
                            // TODO Auto-generated method stub
                            //Do something
                            alert.dismiss();
                            Intent gotoHomepage = new Intent(context, Home.class);
                            context.startActivity(gotoHomepage);
                        }
                    });
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
