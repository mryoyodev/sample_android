package com.example.acer.projectfinaledit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "healthcaresharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_LASTNAME = "keylastname";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";
    private static final String KEY_BIRTHDAY = "keybirthday";
    private static final String KEY_WEIGHT = "keyweight";
    private static final String KEY_HEIGHT = "keyheight";
    private static final String KEY_EXLEVEL = "keyexlevel";
    private static final String KEY_DISEASES= "keydiseases";
    private static final String KEY_FOODALLERGY = "keyfoodallergy";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUserName());
        editor.putString(KEY_LASTNAME, user.getLastName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_BIRTHDAY, user.getBirthday());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putInt(KEY_WEIGHT, user.getWeight());
        editor.putInt(KEY_HEIGHT, user.getHeight());
        editor.putString(KEY_EXLEVEL, user.getExlevel());
        editor.putString(KEY_DISEASES, user.getDiseases());
        editor.putString(KEY_FOODALLERGY, user.getFoodallergy());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_LASTNAME,null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_BIRTHDAY,null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getInt(KEY_WEIGHT,0),
                sharedPreferences.getInt(KEY_HEIGHT,0),
                sharedPreferences.getString(KEY_EXLEVEL,null),
                sharedPreferences.getString(KEY_DISEASES,null),
                sharedPreferences.getString(KEY_FOODALLERGY,null)
        );
    }

    /*
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
    */

}
