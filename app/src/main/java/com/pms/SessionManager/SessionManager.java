package com.pms.SessionManager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.pms.Home;
import com.pms.Login;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;      // Shared pref mode
    SessionManager sessionManager;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String KEY_USERNAME = "username";

    public static final String KEY_MERCHANDISERID = "merchandiserId";
    public static final String KEY_USERFULLNAME = "userFullName";

    public static final String KEY_SELFY = "KEY_SELFY";

    public SessionManager(Context c) {
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String username) {
            // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
        editor.putString(KEY_USERNAME, username);
// commit changes
        editor.commit();
    }
    public void checkSelftSession(String response) {
        // Storing login value as TRUE

        editor.putString(KEY_SELFY, response);
// commit changes
        editor.commit();
    }

    public HashMap<String, String> getCheckSelftSession() {
        HashMap<String, String> user = new HashMap<String, String>();


        user.put(KEY_SELFY, pref.getString(KEY_SELFY, null));


        return user;
    }
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();


        user.put(KEY_MERCHANDISERID, pref.getString(KEY_MERCHANDISERID, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_USERFULLNAME, pref.getString(KEY_USERFULLNAME, null));

        return user;
    }

    public void userDetails(String username, String merchandiserId, String userFullName) {
        // Storing login value as TRUE
        editor.commit();

        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_MERCHANDISERID, merchandiserId);
        editor.putString(KEY_USERFULLNAME, userFullName);


// commit changes
        editor.commit();
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, Login.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        // Staring Login Activity
        context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);

    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
        else{
            Intent intent = new Intent(context, Home.class);
            context.startActivity(intent);
        }
    }


}
