package com.pms;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pms.SessionManager.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;


public class Login extends AppCompatActivity implements View.OnClickListener{
    EditText txtusername;
    EditText password;
    String displayText;
    String pass;
    String test;
    TextView result;
    Button btnLogin;
    Button btnRegister;
    ProgressBar progressBar1;

    private ProgressDialog progressDialog = null;
    SharedPreferences sharedpreferences;

    SessionManager sessionManager;

    boolean doubleBackToExitPressedOnce = false;
    String username;
    String merchandiserId ;
    String UserFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        txtusername = (EditText) findViewById(R.id.txtUserName);
        password = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);

        sessionManager = new SessionManager(Login.this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLogin){
            username = txtusername.getText().toString();
            pass = password.getText().toString();

            if(txtusername.getText().toString().isEmpty() || txtusername.equals("")){
                Toast.makeText(Login.this, "Please Enter username.", Toast.LENGTH_SHORT).show();
            }else if(password.getText().toString().isEmpty() || password.equals("")){
                Toast.makeText(Login.this, "Please Enter Passworf", Toast.LENGTH_SHORT).show();
            }
            else {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait.");
                progressDialog.show();
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            }
        }

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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.CreateLogin(username,pass,"Login");
            progressDialog.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(displayText.equals("Invalid UserName or Password.") || displayText.equals("No Network Found")) {

                txtusername.setText("");
                password.setText("");
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Result");
                builder.setMessage(displayText);
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else
            {
                try {
                    JSONArray jArr = new JSONArray(displayText);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);

                        merchandiserId =  obj.getString("MerchandiserID");
                        UserFullName = obj.getString("NameOfEmployee");

                        sessionManager.createUserLoginSession(username);
                        sessionManager.userDetails(username,merchandiserId,UserFullName);

                        Intent gotoHomepage = new Intent(Login.this,Home.class);
                        startActivity(gotoHomepage);
                        txtusername.setText("");
                        password.setText("");
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}
