package com.pms;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pms.Connectivity.AddSelfieImage;
import com.pms.Connectivity.CheckSelfie;
import com.pms.CropImage.CropImage;
import com.pms.InternetConnectivity.NetworkChangeReceiver;
import com.pms.Model.CheckSelfieModel;
import com.pms.SessionManager.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Home extends BaseActivity implements View.OnClickListener  {

    boolean doubleBackToExitPressedOnce = false;
    ImageButton selfieImageButton;
    ImageButton storeListImageButton;
    ImageButton taskCompleteImageButton;
    ImageButton performanceImageButton;

    TextView txtAddSelfie;
    TextView txtStoreList;
    TextView txtDoneStoreList;
    TextView txtPerformance;

    public int saveMerchandiserId;
    private  String dateResponseResult;
    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;
    private static final int PIC_CAMERA_CROP = 3;
    private static final int PIC_GALLERY_CROP = 4;
    private static final int CAMERA_PERMISSION_REQUEST = 5;
    private static final int READ_STORAGE_PERMISSION_REQUEST = 6;
    private static final int WRITE_STORAGE_PERMISSION_REQUEST = 7;

    String saveSelfieStatus;
    String checkSelfieResponse;
    String currentImagePath;
    Bitmap imageToShow;
    Bitmap imageToShow2;
    String timeStamp;
    File storageDir;
    File originalFile;
    File originalFile2;
    String imageBase64String2;
    File cropFile;
    String imageBase64String;
    String selfieImagePath = "";
    public String selfieImageName = "";
    String saveSelfieImage = "";
    ProgressDialog photoUploadDialogBox;
    private  String checkSelfieMethName = "CheckSelfe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CheckSelfieAsyncCallWS checkSelfieAsyncCallWS = new CheckSelfieAsyncCallWS();
        checkSelfieAsyncCallWS.execute();

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        String merchandiserId= user.get(SessionManager.KEY_MERCHANDISERID);
        saveMerchandiserId = Integer.parseInt(merchandiserId);

        selfieImageButton = (ImageButton) findViewById(R.id.addSelfieImageView);
        storeListImageButton = (ImageButton) findViewById(R.id.storeListImageView);
        taskCompleteImageButton = (ImageButton) findViewById(R.id.taskImageView);
        performanceImageButton = (ImageButton) findViewById(R.id.performanceImageView);
        txtAddSelfie = (TextView) findViewById(R.id.txtAddSelfie);
        txtStoreList = (TextView) findViewById(R.id.txtStoreList);
        txtDoneStoreList = (TextView) findViewById(R.id.txtDoneStoreList);
        txtPerformance = (TextView) findViewById(R.id.txtPerformance);

        selfieImageButton.setOnClickListener(this);
        storeListImageButton.setOnClickListener(this);
        taskCompleteImageButton.setOnClickListener(this);
        performanceImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.addSelfieImageView){
            if(saveSelfieStatus == null){
                Toast.makeText(this,"Fetching selfie status please wait...",Toast.LENGTH_LONG).show();
            }
            else{
                if(saveSelfieStatus.equals("False")){
                    openCamera();
                }
                else{
                    Toast.makeText(this,"Selfie Already Added",Toast.LENGTH_LONG).show();
                }
            }
        }
        else if(v.getId() == R.id.storeListImageView){
            Intent gotoStoreList  = new Intent(this,StoreList.class);
            startActivity(gotoStoreList);
        }
        else if(v.getId() == R.id.taskImageView){
            Intent gotoDoneStoreList  = new Intent(this,DoneStoreList.class);
            startActivity(gotoDoneStoreList);
        }
        else if(v.getId() == R.id.performanceImageView){
            Intent gotoDoneStoreList  = new Intent(this,MyPerformanceReport.class);
            startActivity(gotoDoneStoreList);
        }
    }

    private void openCamera(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestWriteStoragePermission();
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestReadStoragePermission();
            } else {
                createImageFromSelectImageDialogChooser();
            }
        }
    }

    private void createImageFromSelectImageDialogChooser() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else {
            new SelectCameraImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        }
    }

    @TargetApi(23)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            super.onActivityResult(requestCode, resultCode, intent);
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == CAMERA_REQUEST) {

                    originalFile = saveBitmapToFile(new File(currentImagePath));
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    this.imageToShow = BitmapFactory.decodeFile(originalFile.getAbsolutePath(), bmOptions);

                    doCropping(originalFile, PIC_CAMERA_CROP);
                }
                else if (requestCode == PIC_CAMERA_CROP) {
                    Bundle extras = intent.getExtras();
                    this.imageToShow = extras.getParcelable("data");
                    originalFile = saveBitmapToFile(new File(currentImagePath));
                    String filename = currentImagePath.substring(currentImagePath.lastIndexOf("/") + 1);
                    imageBase64String = createBase64StringFromImageFile(originalFile);
                    this.imageToShow = saveCameraCropBitmap(filename, imageToShow);
                    setBitmapToImage(imageToShow, imageBase64String);
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Did not taken any image!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(e.getMessage(), "Error");
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    private String createBase64StringFromImageFile(File originalFile) {
        Bitmap bitmap = BitmapFactory.decodeFile(originalFile.getAbsolutePath());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encodedImage;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setBitmapToImage(Bitmap imageToShow, String imageBase64String) {
       // selfieImageButton.setImageBitmap(imageToShow);
        selfieImagePath = imageBase64String;
        selfieImageName = splitImageName(currentImagePath);
        saveSelfieImage = selfieImagePath;
        submitDetails();
    }

    private String splitImageName(String currentImagePath) {
        String imageName = currentImagePath.substring(currentImagePath.lastIndexOf("/") + 1);
        return imageName;
    }

    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 100;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private void doCropping(File image, int request_code) {

        Intent cropIntent = new Intent(this, CropImage.class);

        cropIntent.putExtra("image-path", currentImagePath);
        cropIntent.putExtra("crop", true);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        cropIntent.putExtra("return-data", true);

        try {
            startActivityForResult(cropIntent, request_code);
        } catch (Exception e) {
            Toast.makeText(this, "Crop Error", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap saveCameraCropBitmap(String filename, Bitmap imageToShow) {
        FileOutputStream outStream = null;

        cropFile = new File(currentImagePath);
        if (cropFile.exists()) {
            cropFile.delete();
            cropFile = new File(storageDir + ".png");
        }
        try {
            outStream = new FileOutputStream(cropFile);
            imageToShow.compress(Bitmap.CompressFormat.PNG, 50, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageToShow;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);

        File image = new File(storageDir + ".png");
        try {
            currentImagePath = image.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    // All permmission
    private void requestReadStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST);
        }
    }

    private void requestWriteStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_STORAGE_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_STORAGE_PERMISSION_REQUEST);
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        }
    }


    public class SelectCameraImage extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                }
            }
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new SelectCameraImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            } else {
                Toast.makeText(this, "CAMERA permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == WRITE_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestReadStoragePermission();
            } else {
                Toast.makeText(this, "Write storage permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == READ_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createImageFromSelectImageDialogChooser();
            } else {
                Toast.makeText(this, "Read storage permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private  class CheckSelfieAsyncCallWS extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            checkSelfieResponse = WebService.checkSelfie(saveMerchandiserId, checkSelfieMethName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            // if(!displayText.equals("Invalid Notification") || displayText.equals("No Network Found")) {
            if(checkSelfieResponse.equals("Invalid Notification") || checkSelfieResponse.equals("No Network Found")) {
                //   progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setTitle("Result");
                builder.setMessage(" unable to fetch Selfie Status");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                try {
                    saveSelfieStatus = checkSelfieResponse;
                }

                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void submitDetails() {
        if (saveMerchandiserId == 0 || saveSelfieImage == null) {
            Toast.makeText(this, "Unable to upload photo please try again", Toast.LENGTH_LONG).show();
        } else {
            photoUploadDialogBox = new ProgressDialog(this);
            photoUploadDialogBox.setMessage("Uploading photo please wait...");
            photoUploadDialogBox.show();
            AddSelfieImage addSelfieImage = new AddSelfieImage(this);
            addSelfieImage.UplaodSelfieImage(saveSelfieImage, saveMerchandiserId,photoUploadDialogBox);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = this.getPackageManager();
        ComponentName component = new ComponentName(this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = this.getPackageManager();
        ComponentName component = new ComponentName(this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
