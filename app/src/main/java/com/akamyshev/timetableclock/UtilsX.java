package com.akamyshev.timetableclock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alexander on 04.02.2016.
 */
public class UtilsX {
    /**
     * Created by Alexander on 04.02.2016.
     */
    public static class App {

        public static void restartApp(Context context) {
            Intent i = context.getPackageManager()
                    .getLaunchIntentForPackage(context.getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }

    }

    /**
     * Created by Alexander on 24.01.2016.
     */
    public static class LessonTime {
        public static final String ID_LESSON_TIME = "lessonTime";



        public static String getLessonTime(Context context) {
            return LocalData.getDataString(context, ID_LESSON_TIME);
        }

        public static Date getLocalDate() {
            Calendar calendar = Calendar.getInstance();
            Date localTime = new Date(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            return localTime;
        }

        public static Date getTimeLocalDate() {
            Calendar cal = Calendar.getInstance();
            return new Date(0, 0, 0, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), 0);
        }
    }

    /**
     * Created by Alexander on 23.12.2015.
     */
    public static class LocalData {
        private enum typeData {INT, STRING, BOOLEAN};
        private static String ID_PATH_DATA = "data.s";

        public static boolean saveData(Context context, Object data, String key) {
            SharedPreferences preferences =
                    context.getSharedPreferences(ID_PATH_DATA, Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = preferences.edit();
            try {
                if(data instanceof Integer) {
                    editor.putInt(key, (int)data);
                }
                if(data instanceof String) {
                    editor.putString(key, (String) data);
                }
                if(data instanceof Boolean) {
                    editor.putBoolean(key, (boolean)data);
                }
                editor.apply();
            }
            catch (ClassCastException e) {
                throw new ClassCastException("expected type isn't preference.putType");
            }
            return true;
        }

        private static Object getData(Context context, String key, typeData type) {
            SharedPreferences preferences =
                    context.getSharedPreferences(ID_PATH_DATA, Context.MODE_PRIVATE);
            try {
                if (type == typeData.INT) return preferences.getInt(key, 0);
                if (type == typeData.STRING) return preferences.getString(key, "");
                if (type == typeData.BOOLEAN) return preferences.getBoolean(key, false);
            }
            catch (ClassCastException e) {
                throw new ClassCastException("expected type isn't preference.getType");
            }

            return null;
        }

        public static int getDataInt(Context context, String key) {
            return (int)getData(context, key, typeData.INT);
        }

        public static String getDataString(Context context, String key) {
            return getData(context, key, typeData.STRING).toString();
        }

        public static boolean getDataBoolean(Context context, String key) {
            return (boolean)getData(context, key, typeData.BOOLEAN);
        }

        public static boolean clearDataString(Context context, String key) {
            return saveData(context, "", key);
        }

        public static boolean clearDataInt(Context context, String key) {
            return saveData(context, 0, key);
        }

        public static boolean clearDataBoolean(Context context, String key) {
            return saveData(context, false, key);
        }
    }

    /**
     * Created by Alexander on 22.12.2015.
     */
    public static class Network {
        private static final String ID_MD5_HASH = "MD5 hash for api";

        public static boolean isNetwork(Context context) {
            ConnectivityManager manager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netWork = manager.getActiveNetworkInfo();
            return netWork != null ? true : false;
        }


        private static String createMD5Hash(String mes) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                byte[] result = messageDigest.digest(mes.getBytes());
                BigInteger bigInteger = new BigInteger(1, result);
                return bigInteger.toString(16);
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }

        public static void saveMD5Hash(Context context, String text) {
            LocalData.saveData(context, createMD5Hash(text), ID_MD5_HASH);
        }

        public static String getMD5Hash(Context context) {
            return LocalData.getDataString(context, ID_MD5_HASH);
        }
        public static int getId() {
            return 0;
        }

    }

    /**
     * Created by Alexander on 17.01.2016.
     */

    /**
     * Created by Alexander on 03.02.2016.
     */


    /**
     * Created by Alexander on 23.12.2015.
     */

}
