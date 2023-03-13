package com.call.calllog.blocklist.object;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by jksol3 on 19/3/16.
 */
public class PrefUtils {

    public static void setBlocking(Context context, String key,
                                   boolean value) {

        Log.e("Utils", "Saving:" + key + ":" + value);
        SharedPreferences preferences = context.getSharedPreferences(
                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    public static boolean getBlocking(Context context, String key) {
        Log.e("Utils", "Get:" + key);
        SharedPreferences preferences = context.getSharedPreferences(
                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, true);
    }

    public static void setStatusIcon(Context context, String key,
                                   boolean value) {

        Log.e("Utils", "Saving:" + key + ":" + value);
        SharedPreferences preferences = context.getSharedPreferences(
                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    public static boolean getStatusIcon(Context context, String key) {
        Log.e("Utils", "Get:" + key);
        SharedPreferences preferences = context.getSharedPreferences(
                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void setNotification(Context context, String key,
                                   boolean value) {

        Log.e("Utils", "Saving:" + key + ":" + value);
        SharedPreferences preferences = context.getSharedPreferences(
                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    public static boolean getNotification(Context context, String key) {
        Log.e("Utils", "Get:" + key);
        SharedPreferences preferences = context.getSharedPreferences(
                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, true);
    }


    public static void setPrivteNumbers(Context context, String key,
                                         boolean value) {

        Log.e("Utils", "Saving:" + key + ":" + value);
        SharedPreferences preferences = context.getSharedPreferences(
                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    public static boolean getPrivteNumbers(Context context, String key) {
        Log.e("Utils", "Get:" + key);
        SharedPreferences preferences = context.getSharedPreferences(
                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void setUnknownNumbers(Context context, String key,

                                        boolean value) {

        Log.e("Utils", "Saving:" + key + ":" + value);
        SharedPreferences preferences = context.getSharedPreferences(
                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    public static boolean getUnknownNumbers(Context context, String key) {
        Log.e("Utils", "Get:" + key);
        SharedPreferences preferences = context.getSharedPreferences(
                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void setAllCalls(Context context, String key,

                                        boolean value) {

        Log.e("Utils", "Saving:" + key + ":" + value);
        SharedPreferences preferences = context.getSharedPreferences(
                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    public static boolean getAllCalls(Context context, String key) {
        Log.e("Utils", "Get:" + key);
        SharedPreferences preferences = context.getSharedPreferences(
                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void setInputList(Context context, String paths) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(Constant.INPUT, paths).commit();
    }

    public static String getInputList(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(Constant.INPUT, "");
    }

    public static void setContactBlackList(Context context, String paths) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(Constant.CONTACT, paths).commit();
    }

    public static String getContactBlackList(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(Constant.CONTACT, "");
    }

    public static void setLogList(Context context, String paths) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(Constant.LOG, paths).commit();
    }

    public static String getLogList(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(Constant.LOG, "");
    }

    public static void setWhiteList(Context context, String paths) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(Constant.WHITELIST, paths).commit();
    }

    public static String getWhiteList(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(Constant.WHITELIST, "");
    }

//    public static void saveMultiSelectedIndex(Context context, String key, String value) {
//
//        Log.e("Utils", "Saving:" + key + ":" + value);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(key, value);
//        editor.commit();
//
//    }
//
//    public static String getMultiSelectedIndex(Context context, String key) {
//        Log.e("Utils", "Get:" + key);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        return preferences.getString(key, "0");
//    }
//
//    public static void saveThumbnail(Context context, String key,
//
//                                     int value) {
//
//        Log.e("Utils", "Saving:" + key + ":" + value);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(key, value);
//        editor.commit();
//
//    }
//
//    public static int getThumbnail(Context context, String key) {
//        Log.e("Utils", "Get:" + key);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        return preferences.getInt(key, 0);
//    }
//
//    public static void saveLength(Context context, String key,
//
//                                  int value) {
//
//        Log.e("Utils", "Saving:" + key + ":" + value);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(key, value);
//        editor.commit();
//
//    }
//
//    public static int getLength(Context context, String key) {
//        Log.e("Utils", "Get:" + key);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        return preferences.getInt(key, 20);
//    }
//
//    public static void saveFileExtention(Context context, String key,
//
//                                         int value) {
//
//        Log.e("Utils", "Saving:" + key + ":" + value);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(key, value);
//        editor.commit();
//
//    }
//
//    public static int getFileExtention(Context context, String key) {
//        Log.e("Utils", "Get:" + key);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        return preferences.getInt(key, 30);
//    }
//
//    public static void saveResolution(Context context, String key,
//
//                                      int value) {
//
//        Log.e("Utils", "Saving:" + key + ":" + value);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(key, value);
//        editor.commit();
//
//    }
//
//    public static int getResolution(Context context, String key) {
//        Log.e("Utils", "Get:" + key);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        return preferences.getInt(key, 40);
//    }
//
//    public static void saveLocation(Context context, String key,
//
//                                    int value) {
//
//        Log.e("Utils", "Saving:" + key + ":" + value);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(key, value);
//        editor.commit();
//
//    }
//
//    public static int getLocation(Context context, String key) {
//        Log.e("Utils", "Get:" + key);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        return preferences.getInt(key, 50);
//    }
//
//    public static void saveSize(Context context, String key,
//
//                                int value) {
//
//        Log.e("Utils", "Saving:" + key + ":" + value);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(key, value);
//        editor.commit();
//
//    }
//
//    public static int getSize(Context context, String key) {
//        Log.e("Utils", "Get:" + key);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        return preferences.getInt(key, 60);
//    }
//
//    public static void saveDuration(Context context, String key,
//
//                                    int value) {
//
//        Log.e("Utils", "Saving:" + key + ":" + value);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(key, value);
//        editor.commit();
//
//    }
//
//    public static int getDuration(Context context, String key) {
//        Log.e("Utils", "Get:" + key);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        return preferences.getInt(key, 0);
//    }
//
//    public static void saveSettings(Context context, String key,
//
//                                    String value) {
//
//        //    Log.e("Utils", "Saving:" + key + ":" + value);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(key, value);
//        editor.commit();
//
//    }
//
//    public static String getSettings(Context context, String key) {
//        //    Log.e("Utils", "Get:" + key);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        return preferences.getString(key, "0");
//    }


//    public static void setExtention(Context context, String paths) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        sp.edit().putString(Constant.PREF_EXTENTION, paths).commit();
//    }
//
//    public static String getExtention(Context context) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        return sp.getString(Constant.PREF_EXTENTION, "");
//    }
//
//    public static void save_dialog_path(Context context, String key,
//
//                                        String value) {
//
//        Log.e("Utils", "Saving:" + key + ":" + value);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(key, value);
//        editor.commit();
//
//    }
//
//    public static String get_dialog_path(Context context, String key) {
//        Log.e("Utils", "Get:" + key);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        return preferences.getString(key, "0");
//    }
//
//    public static void save_extention(Context context, String key,
//
//                                      String value) {
//
//        //  Log.e("Utils", "Saving:" + key + ":" + value);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(key, value);
//        editor.commit();
//
//    }
//
//    public static String get_extention(Context context, String key) {
//        // Log.e("Utils", "Get:" + key);
//        SharedPreferences preferences = context.getSharedPreferences(
//                Constant.SHARED_PREFS, Context.MODE_PRIVATE);
//        return preferences.getString(key, "0");
//    }
//
//    public static void setBitmap(Context context, String paths) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        sp.edit().putString(Constant.BITMAP, paths).commit();
//    }
//
//    public static String getBitmap(Context context) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        return sp.getString(Constant.BITMAP, "");
//    }

}
