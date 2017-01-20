package com.pms.Connectivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.pms.Home;
import com.pms.Model.StoreListItems;
import com.pms.SessionManager.SessionManager;
import com.pms.StoreList;
import com.pms.Store_Image_Form;
import com.pms.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class AddQuestionImages {
    private static Context context;
    private static final String url="";
    private static String displayText;
    private static int questionId;
    private static String images;
    private static String image2;
    private static String image3;
    private static String image4;
    private static String image5;
    private static int count;
    private static String storeImageUploadMethod = "AddNewQuestionImage";

    public AddQuestionImages(Store_Image_Form storeImageForm) {
        context = storeImageForm;
    }

    public static void addImages(int saveQuestionId, String saveImage, int uploadImageCount) {

        questionId = saveQuestionId;
        images = saveImage;
        count = uploadImageCount;

        AsyncCallWS task = new AsyncCallWS();
        task.execute();

    }
    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.SaveImages(questionId,images,storeImageUploadMethod,count);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            // if(!displayText.equals("Invalid Notification") || displayText.equals("No Network Found")) {
            if(displayText.equals("Invalid Notification") || displayText.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("There is some problem please try again.");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else if(displayText.equals("5")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Photo SuccessFully Uploaded.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                        Intent gotoHome = new Intent(context, Home.class);
                        context.startActivity(gotoHome);
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        }
    }
}
