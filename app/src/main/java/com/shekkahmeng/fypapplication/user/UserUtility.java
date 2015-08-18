package com.shekkahmeng.fypapplication.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by vc on 8/12/15.
 */
public class UserUtility {

    private static final String LOGIN_USER_ID_KEY = "LOGIN_USER_ID_KEY";

    public static String getUserId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(LOGIN_USER_ID_KEY, null);
    }

    public static void setUserId(Context context, String userId) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(LOGIN_USER_ID_KEY, userId);
        editor.commit();
    }
}
