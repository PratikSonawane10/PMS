package com.pms.Connectivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import com.pms.Home;
import com.pms.Model.StoreListItems;
import com.pms.Store_Form;
import com.pms.Store_Image_Form;
import com.pms.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddNewQuestion {

    private static String questionId;
    private static int UserId;
    private static boolean WindowMerchandising;
    private static int NoofDanglers;
    private static int NoofPosters;
    private static String Banners;
    private static String AnyOther;
    private static boolean WindowShelfSpaces;
    private static boolean FourfacingsIncategoryshelf;
    private static String STR125gx4;
    private static String STW;
    private static String SBS;
    private static String GLD;
    private static String WCFBottle;
    private static String SBL;
    private static String IssueToHighlight;
    private static String ExitWithoutExecnapprvBy;
    private static String ReasonNonCooperationofoutlet;
    private static boolean WorkedwithStarSalesman;
    private static String Images;
    private static String Longitude;
    private static String Latitude;
    private static int RetailerId;
    private static int beatId;
    private static boolean shopOpen;
    private static String addQuestioneMethodName = "AddNewQuestion";
    private static Context context;
    private static String responseResult;
    private static ProgressDialog progressDialog;

    public AddNewQuestion(Store_Form store_form) {
        context = store_form;
    }

    public void UploadAddNewQuestion(int saveUserId, boolean saveWindowMerchandising, int saveNoofDanglers, int saveNoofPosters,
                                     String saveBanners, String saveAnyOther, boolean saveWindowShelfSpaces,
                                     boolean saveFourfacingsIncategoryshelf, String saveSTR125gx4, String saveSTW,
                                     String saveSBS, String saveGLD, String saveWCFBottle, String saveSBL, String saveIssueToHighlight,
                                     String saveExitWithoutExecnapprvBy, String saveReasonNonCooperationofoutlet,
                                     boolean saveWorkedwithStarSalesman, String saveImages, String saveLongitude,
                                     String saveLatitude, int saveRetailerId, int saveBeatId, boolean storeOpen, ProgressDialog uploadStoreDialogBox) {

        UserId = saveUserId;
        WindowMerchandising = saveWindowMerchandising;
        NoofDanglers = saveNoofDanglers;
        NoofPosters = saveNoofPosters;
        Banners = saveBanners;
        AnyOther = saveAnyOther;
        WindowShelfSpaces = saveWindowShelfSpaces;
        FourfacingsIncategoryshelf = saveFourfacingsIncategoryshelf;
        STR125gx4 = saveSTR125gx4;
        STW = saveSTW;
        SBS = saveSBS;
        GLD = saveGLD;
        WCFBottle = saveWCFBottle;
        SBL = saveSBL;
        IssueToHighlight = saveIssueToHighlight;
        ExitWithoutExecnapprvBy = saveExitWithoutExecnapprvBy;
        ReasonNonCooperationofoutlet = saveReasonNonCooperationofoutlet;
        WorkedwithStarSalesman = saveWorkedwithStarSalesman;
        Images = saveImages;
        Longitude = saveLongitude;
        Latitude = saveLatitude;
        RetailerId = saveRetailerId;
        beatId = saveBeatId;
        shopOpen = storeOpen;
        progressDialog = uploadStoreDialogBox;
        AddQuestionAsyncCallWS task = new AddQuestionAsyncCallWS();
        task.execute();
    }

    private static class AddQuestionAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            responseResult = WebService.AddNewQuestion(addQuestioneMethodName, UserId, WindowMerchandising, NoofDanglers, NoofPosters, Banners,
                    AnyOther, WindowShelfSpaces, FourfacingsIncategoryshelf, STR125gx4, STW, SBS, GLD, WCFBottle, SBL, IssueToHighlight, ExitWithoutExecnapprvBy, ReasonNonCooperationofoutlet,
                    WorkedwithStarSalesman, Images, Longitude, Latitude, RetailerId, beatId, shopOpen);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (responseResult.equals("Err:Record Allready Inserted")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("All ready inserted Data");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else if (responseResult.equals("Error occured")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to upload details");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else if (responseResult.equals("shope_close")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Store details Added Sucessfully");
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
            else {
                try {
                    progressDialog.dismiss();
                    questionId = responseResult;

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Result");
                    builder.setMessage("Store Details Added Successfully");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface alert, int which) {
                            // TODO Auto-generated method stub
                            //Do something
                            alert.dismiss();
                            Intent gotoStoreImageForm = new Intent(context, Store_Image_Form.class);
                            gotoStoreImageForm.putExtra("id", questionId);
                            context.startActivity(gotoStoreImageForm);
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

