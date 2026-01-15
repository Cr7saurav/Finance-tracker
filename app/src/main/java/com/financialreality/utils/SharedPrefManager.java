package com.financialreality.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    
    private static final String PREF_NAME = "FinancialRealityPref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_ACCESS_TOKEN = "accessToken";
    
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private SharedPreferences sharedPreferences;
    
    private SharedPrefManager(Context context) {
        mCtx = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    
    public void saveUserData(String userId, String email, String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_NAME, name);
        editor.apply();
    }
    
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    
    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }
    
    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }
    
    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }
    
    public void clearUserData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
