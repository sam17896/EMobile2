package com.example.ahsan.emobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    private static String TAG = SessionManager.class.getSimpleName();

    SharedPreferences pref;

    Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "E-Discussion";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_USERID = "userid";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_TOPIC_ID = "topic_id";


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setUserId(String userId){
        editor.putString(KEY_USERID,userId);
        editor.commit();

        Log.d(TAG, "User ID set");
    }

    public void setUsername(String username){
        editor.putString(KEY_USERNAME,username);
        editor.commit();

        Log.d(TAG, "User ID set");

    }

    public void setTopicID(String topicID){
        editor.putString(KEY_TOPIC_ID,topicID);
        editor.commit();

        Log.d(TAG, "Topic ID is set");

    }

    public String getTopicID(){
        return pref.getString(KEY_TOPIC_ID,"-1");
    }



    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getUserID(){
        return pref.getString(KEY_USERID,"-1");
    }

    public String getUsername(){
        return pref.getString(KEY_USERNAME,"Ediscussion");
    }
}