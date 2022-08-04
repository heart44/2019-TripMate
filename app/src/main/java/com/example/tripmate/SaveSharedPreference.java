package com.example.tripmate;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

//로그인 정보 저장 및 가져오는 클래스

public class SaveSharedPreference {

    static final String PREF_USER_NAME = "username";
    static final String PREF_NICK_NAME = "nickname";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        Log.i("aaaaaad",userName);
        editor.commit();
    }

    public static void setNickName(Context ctx, String nickName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_NICK_NAME, nickName);
        Log.i("aaaaaad",nickName);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getNickName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_NICK_NAME, "");
    }

    // 로그아웃
    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }

}
