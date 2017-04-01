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
    private static final String KEY_TOPIC_ADMIN = "topic_admin";
    private static final String KEY_TOPIC_DESCRIPTION = "topic_description";
    private static final String KEY_TOPIC_TITLE = "topic_titile";
    private static final String KEY_PROFILE = "profile";
    private static final String KEY_PIC = "pic";



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

    public void setTopicName(String name){
        editor.putString(KEY_TOPIC_TITLE,name);
        editor.commit();

        Log.d(TAG, "Topic name set");
    }


    public void setTopicDescription(String desc){
        editor.putString(KEY_TOPIC_DESCRIPTION,desc);
        editor.commit();

        Log.d(TAG, "Topic Description set");
    }

    public void setProfile(String pr){
        editor.putString(KEY_PROFILE, pr);
        editor.commit();

        Log.d(TAG, "Profile id set");

    }

    public void setTopicAdmin(String admin){
        editor.putString(KEY_TOPIC_ADMIN,admin);
        editor.commit();

        Log.d(TAG, "Topic admin set");
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
    public void setPpic(String pic){
        editor.putString(KEY_PIC,pic);
        editor.commit();

        Log.d(TAG, "Picture is set");

    }

    public String getPpic(){
        return pref.getString(KEY_PIC,"-1");
    }

    public String getTopicID(){
        return pref.getString(KEY_TOPIC_ID,"-1");
    }
    public String getProfile() { return pref.getString(KEY_PROFILE,"-1");}
    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
    public String getUserID(){
        return pref.getString(KEY_USERID,"-1");
    }
    public String getTopicAdmin(){
        return pref.getString(KEY_TOPIC_ADMIN, "-1");
    }
    public String getTopicTitle(){
        return pref.getString(KEY_TOPIC_TITLE,"-1");
    }
    public String getTopicDescription(){
        return pref.getString(KEY_TOPIC_DESCRIPTION,"-1");
    }
    public String getUsername(){
        return pref.getString(KEY_USERNAME,"Ediscussion");
    }
}