package com.pms;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import android.Manifest;

import com.pms.Connectivity.AddQuestionImages;
import com.pms.Connectivity.Fetch_StoreList;
import com.pms.CropImage.CropImage;
import com.pms.InternetConnectivity.NetworkChangeReceiver;
import com.pms.SessionManager.SessionManager;


public class Store_Image_Form extends BaseActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;
    private static final int PIC_CAMERA_CROP = 3;
    private static final int PIC_GALLERY_CROP = 4;
    private static final int CAMERA_PERMISSION_REQUEST = 5;
    private static final int READ_STORAGE_PERMISSION_REQUEST = 6;
    private static final int WRITE_STORAGE_PERMISSION_REQUEST = 7;

    private ProgressDialog progressDialog = null;

    public AlertDialog alertDialog;
    public ArrayAdapter<String> dialogAdapter;

    RelativeLayout image1Layout;
    RelativeLayout image2Layout;
    RelativeLayout image3Layout;
    RelativeLayout image4Layout;
    RelativeLayout image5Layout;

    RelativeLayout fieldLayout1;
    RelativeLayout fieldLayout2;
    RelativeLayout fieldLayout3;
    RelativeLayout fieldLayout4;
    RelativeLayout fieldLayout5;

    Button selectImageButton1;
    Button selectImageButton2;
    Button selectImageButton3;
    Button selectImageButton4;
    Button selectImageButton5;
    Button btnSubmit;

    ImageView firstImage;
    ImageView secondImage;
    ImageView thirdImage;
    ImageView fourthImage;
    ImageView fifthImage;

    String questionId;
    int saveQuestionId = 1;
    String currentImagePath;
    String currentImagePath2;

    Bitmap imageToShow;
    String timeStamp;
    File cropFile;
    File storageDir;

    File originalFile;
    File originalFile2;
    File originalFile3;
    File originalFile4;
    File originalFile5;

    String imageBase64String;
    String imageBase64String2;
    String imageBase64String3;
    String imageBase64String4;
    String imageBase64String5;

    String firstImagePath = "";
    String secondImagePath = "";
    String thirdImagePath = "";
    String fourthImagePath = "";
    String fifthImagePath = "";

    String firstImageName = "";
    String secondImageName = "";
    String thirdImageName = "";
    String fourthImageName = "";
    String fifthImageName = "";
    String displayText;
    LinearLayout imageViewLinearLayout;

    String saveImage1 = "";
    String saveImage2 = "";
    String saveImage3 = "";
    String saveImage4 = "";
    String saveImage5 = "";

    boolean doubleBackToExitPressedOnce = false;

    String userId;
    public int imageCounter;
    int uploadImageCount ;
    private long TIME = 5000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_image_form);


        Intent intent = getIntent();
        if (null != intent) {
            questionId = intent.getStringExtra("id");
        }

        SessionManager sessionManager = new SessionManager(Store_Image_Form.this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        userId = user.get(SessionManager.KEY_MERCHANDISERID);

        fieldLayout1 = (RelativeLayout) findViewById(R.id.fieldLayout1);
        fieldLayout2 = (RelativeLayout) findViewById(R.id.fieldLayout2);
        fieldLayout3 = (RelativeLayout) findViewById(R.id.fieldLayout3);
        fieldLayout4 = (RelativeLayout) findViewById(R.id.fieldLayout4);
        fieldLayout5 = (RelativeLayout) findViewById(R.id.fieldLayout5);

        image1Layout = (RelativeLayout) findViewById(R.id.image1Layout);
        image2Layout = (RelativeLayout) findViewById(R.id.image2Layout);
        image3Layout = (RelativeLayout) findViewById(R.id.image3Layout);
        image4Layout = (RelativeLayout) findViewById(R.id.image4Layout);
        image5Layout = (RelativeLayout) findViewById(R.id.image5Layout);

        btnSubmit = (Button) this.findViewById(R.id.btnSubmitStore_Image_Form);
        selectImageButton1 = (Button) this.findViewById(R.id.btnImage1);
        selectImageButton2 = (Button) this.findViewById(R.id.btnImage2);
        selectImageButton3 = (Button) this.findViewById(R.id.btnImage3);
        selectImageButton4 = (Button) this.findViewById(R.id.btnImage4);
        selectImageButton5 = (Button) this.findViewById(R.id.btnImage5);

        firstImage = (ImageView) this.findViewById(R.id.firstImage);
        secondImage = (ImageView) this.findViewById(R.id.secondImage);
        thirdImage = (ImageView) this.findViewById(R.id.thirdImage);
        fourthImage = (ImageView) this.findViewById(R.id.fourthImage);
        fifthImage = (ImageView) this.findViewById(R.id.fifthImage);

        selectImageButton1.setOnClickListener(this);
        selectImageButton2.setOnClickListener(this);
        selectImageButton3.setOnClickListener(this);
        selectImageButton4.setOnClickListener(this);
        selectImageButton5.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);


        fieldLayout2.setVisibility(View.GONE);
        fieldLayout3.setVisibility(View.GONE);
        fieldLayout4.setVisibility(View.GONE);
        fieldLayout5.setVisibility(View.GONE);

        image1Layout.setVisibility(View.GONE);
        image2Layout.setVisibility(View.GONE);
        image3Layout.setVisibility(View.GONE);
        image4Layout.setVisibility(View.GONE);
        image5Layout.setVisibility(View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.btnImage1) {
            imageCounter = 1;
            openCamera();
            fieldLayout2.setVisibility(View.VISIBLE);
        }
        else if (v.getId() == R.id.btnImage2) {
            imageCounter = 2;
            if (saveImage1.isEmpty()) {
                Toast.makeText(Store_Image_Form.this, "Please Add First Photo", Toast.LENGTH_LONG).show();
            } else {
                uploadImageCount = 1;
                saveQuestionId = Integer.parseInt(questionId);
                AddQuestionImages addQuestionImages = new AddQuestionImages(Store_Image_Form.this);
                addQuestionImages.addImages(saveQuestionId, saveImage1, uploadImageCount);
                openCamera();
                fieldLayout3.setVisibility(View.VISIBLE);
            }
        }
        else if (v.getId() == R.id.btnImage3) {
            imageCounter = 3;
            if (saveImage1.isEmpty() || saveImage2.isEmpty()) {
                Toast.makeText(Store_Image_Form.this, "Please Add First and Second Photo", Toast.LENGTH_LONG).show();
            } else {
                uploadImageCount = 2;
                saveQuestionId = Integer.parseInt(questionId);
                AddQuestionImages addQuestionImages = new AddQuestionImages(Store_Image_Form.this);
                addQuestionImages.addImages(saveQuestionId, saveImage2, uploadImageCount);
                openCamera();
                fieldLayout4.setVisibility(View.VISIBLE);
            }
        }
        else if (v.getId() == R.id.btnImage4) {
            imageCounter = 4;
            if (saveImage1.isEmpty() || saveImage2.isEmpty() || saveImage3.isEmpty()) {
                Toast.makeText(Store_Image_Form.this, "Please Add First,Second and Third Photo", Toast.LENGTH_LONG).show();
            } else {
                uploadImageCount = 3;
                saveQuestionId = Integer.parseInt(questionId);
                AddQuestionImages addQuestionImages = new AddQuestionImages(Store_Image_Form.this);
                addQuestionImages.addImages(saveQuestionId, saveImage3 , uploadImageCount);
                openCamera();
                fieldLayout5.setVisibility(View.VISIBLE);
            }
        }
        else if (v.getId() == R.id.btnImage5) {
            imageCounter = 5;
            if (saveImage1.isEmpty() || saveImage2.isEmpty() || saveImage3.isEmpty() || saveImage4.isEmpty()) {
                Toast.makeText(Store_Image_Form.this, "Please Add All Photo", Toast.LENGTH_LONG).show();
            } else {
                uploadImageCount = 4;
                saveQuestionId = Integer.parseInt(questionId);
                AddQuestionImages addQuestionImages = new AddQuestionImages(Store_Image_Form.this);
                addQuestionImages.addImages(saveQuestionId, saveImage4, uploadImageCount);
                openCamera();
            }
        }
       /* if(v.getId() == R.id.btnImage1 || v.getId() == R.id.btnImage2 || v.getId() == R.id.btnImage3 || v.getId() == R.id.btnImage4 || v.getId() == R.id.btnImage5) {
            if(ActivityCompat.checkSelfPermission(Store_Image_Form.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestWriteStoragePermission();
            }
            else {
                if(ActivityCompat.checkSelfPermission(Store_Image_Form.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestReadStoragePermission();
                }
                else {
                    createImageFormSelectImageDialogChooser();
                }
            }
        }*/
        else if (v.getId() == R.id.btnSubmitStore_Image_Form) {
            if (saveImage1.isEmpty() || saveImage2.isEmpty() || saveImage3.isEmpty() || saveImage4.isEmpty() || saveImage5.isEmpty()) {
                Toast.makeText(Store_Image_Form.this, "Please Add All 5 Photo", Toast.LENGTH_LONG).show();
            } else {
                try {
                    uploadImageCount = 5;
                    saveQuestionId = Integer.parseInt(questionId);
                    AddQuestionImages addQuestionImages = new AddQuestionImages(Store_Image_Form.this);
                    addQuestionImages.addImages(saveQuestionId, saveImage5, uploadImageCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openCamera() {
        if (ActivityCompat.checkSelfPermission(Store_Image_Form.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestWriteStoragePermission();
        } else {
            if (ActivityCompat.checkSelfPermission(Store_Image_Form.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestReadStoragePermission();
            } else {
                createImageFormSelectImageDialogChooser();
            }
        }
    }


    private void createImageFormSelectImageDialogChooser() {

        if (ActivityCompat.checkSelfPermission(Store_Image_Form.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else {
            new SelectCameraImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        }

//        dialogAdapter = new ArrayAdapter<String>(Store_Image_Form.this, android.R.layout.select_dialog_item) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                TextView text = (TextView) view.findViewById(android.R.id.text1);
//                text.setTextColor(Color.BLACK);
//                text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                        getResources().getDimension(R.dimen.alertDialogListNames));
//                return view;
//            }
//        };
//        dialogAdapter.add("Take from Camera");
//        dialogAdapter.add("Select from Gallery");
//        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(Store_Image_Form.this, R.style.AlertDialogCustom));
//        builder.setTitle("Select Image");
//        builder.setAdapter(dialogAdapter, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                if (which == 0) {
//                    alertDialog.dismiss();
//                    if(ActivityCompat.checkSelfPermission(Store_Image_Form.this, Manifest.permission.CAMERA)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        requestCameraPermission();
//                    }
//                    else {
//                        new SelectCameraImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
//                    }
//                } else if (which == 1) {
//                    alertDialog.dismiss();
//                    new SelectGalleryImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
//                }
//            }
//        });
//        alertDialog = builder.create();
//
//        alertDialog.show();
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
//                else if(requestCode == GALLERY_REQUEST) {
//                    Uri uri = intent.getData();
//                    String[] projection = { MediaStore.Images.Media.DATA };
//                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(projection[0]);
//                    currentImagePath = cursor.getString(columnIndex);
//                    cursor.close();
//                    originalFile2 = saveBitmapToFile(new File(currentImagePath));
//                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//                    this.imageToShow = BitmapFactory.decodeFile(originalFile2.getAbsolutePath(), bmOptions);
//
//                    doCropping(originalFile2, PIC_GALLERY_CROP);
//                }
                else if (requestCode == PIC_CAMERA_CROP) {
                    Bundle extras = intent.getExtras();
                    this.imageToShow = extras.getParcelable("data");
                    originalFile = saveBitmapToFile(new File(currentImagePath));
                    String filename = currentImagePath.substring(currentImagePath.lastIndexOf("/") + 1);
                    imageBase64String = createBase64StringFromImageFile(originalFile);
                    this.imageToShow = saveCameraCropBitmap(filename, imageToShow);
                    setBitmapToImage(imageToShow, imageBase64String);
                }
//                else if(requestCode == PIC_GALLERY_CROP) {
//                    Bundle extras = intent.getExtras();
//                    this.imageToShow = extras.getParcelable("data");
//                    originalFile2 = saveBitmapToFile(new File(currentImagePath));
//                    //String filename=currentImagePath.substring(currentImagePath.lastIndexOf("/")+1);
//                    imageBase64String2 = createBase64StringFromImageFile(originalFile2);
//                    //this.imageToShow = saveCameraCropBitmap(filename, imageToShow);
//                    this.imageToShow = saveGalleryCropBitmap(imageToShow);
//                    setBitmapToImage(imageToShow, imageBase64String2);
//
//                    //setBitmapToImage(this.imageToShow);
//                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(Store_Image_Form.this, "Did not taken any image!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(e.getMessage(), "Error");
            Toast.makeText(Store_Image_Form.this, "Error", Toast.LENGTH_LONG).show();
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
        //if(Objects.equals(firstImagePath, "")) {
        if (imageCounter == 1) {
            image1Layout.setVisibility(View.VISIBLE);
            firstImage.setImageBitmap(imageToShow);
            //firstImagePath = cropFile.getAbsolutePath();
            //firstImagePath = originalFile.getAbsolutePath();
            //firstImagePath = "data:image/png;base64,"+imageBase64String;
            saveImage1 = imageBase64String;
            firstImageName = splitImageName(currentImagePath);
        }
        //else if(!Objects.equals(firstImagePath, "")) {
        if (imageCounter == 2) {
            image2Layout.setVisibility(View.VISIBLE);
            secondImage.setImageBitmap(imageToShow);
            //secondImagePath = cropFile.getAbsolutePath();
            //secondImagePath = originalFile.getAbsolutePath();
            //secondImagePath = "data:image/png;base64," + imageBase64String;
            saveImage2 = imageBase64String;
            secondImageName = splitImageName(currentImagePath);
        }
        if (imageCounter == 3) {
            image3Layout.setVisibility(View.VISIBLE);
            thirdImage.setImageBitmap(imageToShow);
            //secondImagePath = cropFile.getAbsolutePath();
            //secondImagePath = originalFile.getAbsolutePath();
            //secondImagePath = "data:image/png;base64," + imageBase64String;
            saveImage3 = imageBase64String;
            thirdImageName = splitImageName(currentImagePath);
        }
        if (imageCounter == 4) {
            image4Layout.setVisibility(View.VISIBLE);
            fourthImage.setImageBitmap(imageToShow);
            //secondImagePath = cropFile.getAbsolutePath();
            //secondImagePath = originalFile.getAbsolutePath();
            //secondImagePath = "data:image/png;base64," + imageBase64String;
            saveImage4 = imageBase64String;
            fourthImageName = splitImageName(currentImagePath);
        }
        if (imageCounter == 5) {
            image5Layout.setVisibility(View.VISIBLE);
            fifthImage.setImageBitmap(imageToShow);
            //secondImagePath = cropFile.getAbsolutePath();
            //secondImagePath = originalFile.getAbsolutePath();
            //secondImagePath = "data:image/png;base64," + imageBase64String;
            saveImage5 = imageBase64String;
            fifthImageName = splitImageName(currentImagePath);
        }
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
            e.printStackTrace();
            Toast.makeText(Store_Image_Form.this, "Crop Error", Toast.LENGTH_LONG).show();
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
            imageToShow.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageToShow;
    }

//    private Bitmap saveGalleryCropBitmap(Bitmap imageToShow) {
//        FileOutputStream outStream = null;
//
//        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DCIM);
//
//        cropFile = new File(storageDir + ".png");
//        try {
//            outStream = new FileOutputStream(cropFile);
//            imageToShow.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//            outStream.flush();
//            outStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return imageToShow;
//    }

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

    private void requestReadStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(Store_Image_Form.this,
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
            ActivityCompat.requestPermissions(Store_Image_Form.this,
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
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(Store_Image_Form.this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
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

//    public class SelectGalleryImage extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            // Create intent to Open Image applications like Gallery, Google Photos
//            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(galleryIntent, GALLERY_REQUEST);
//            return null;
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new SelectCameraImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            } else {
                Toast.makeText(Store_Image_Form.this, "CAMERA permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == WRITE_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestReadStoragePermission();
            } else {
                Toast.makeText(Store_Image_Form.this, "Write storage permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == READ_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createImageFormSelectImageDialogChooser();
            } else {
                Toast.makeText(Store_Image_Form.this, "Read storage permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = Store_Image_Form.this.getPackageManager();
        ComponentName component = new ComponentName(Store_Image_Form.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = Store_Image_Form.this.getPackageManager();
        ComponentName component = new ComponentName(Store_Image_Form.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Store_Image_Form.this);
        builder.setTitle("Result");
        builder.setMessage("Please Upload Store Images");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface alert, int which) {
                // TODO Auto-generated method stub
                //Do something
                alert.dismiss();
            }
        });
        android.app.AlertDialog alert1 = builder.create();
        alert1.show();

    }
}
