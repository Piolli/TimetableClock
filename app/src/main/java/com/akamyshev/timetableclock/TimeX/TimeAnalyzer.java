package com.akamyshev.timetableclock.TimeX;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;


import com.akamyshev.timetableclock.R;
import com.akamyshev.timetableclock.UtilsX;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alexander on 26.12.2015.
 */
public class TimeAnalyzer {
    public static final String TAG = "TimeAnalyzer";

    typeToday today;
    Date date, maxTimeLesson;
    int[] hoursL, minutL, restL;
    Context context;
    BiTime mBiTime = new BiTime();


    //Конструктор, вызаваемый при наличии данных с интернета
    public TimeAnalyzer(Context context, @NonNull ITime iTime, boolean isFromServer) {
        this.context = context;
        date = new Time(Calendar.getInstance().getTime().getTime());

        hoursL = iTime.hours;
        minutL = iTime.minut;
        restL = iTime.rests;

//        today = isFromServer ? -1 : Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

//    int[][] getInfoLess() {
//        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//        //Log.i(TAG, "dayOfWeak: " + day);
//        int[][] HMR = new int[3][];
//        switch (day) {
//            //среда
//            case Calendar.WEDNESDAY: {
//                today = typeToday.WEDNESDAY;
//                HMR[0] = getArrayTimes(R.string.time_wednesdayHours);
//                HMR[1] = getArrayTimes(R.string.time_wednesdayMinutes);
//                HMR[2] = getArrayTimes(R.string.time_wednesdayRests);
//                return HMR;
//            }
//            //суббота
//            case Calendar.SATURDAY: {
//                today = typeToday.SATURDAY;
//                HMR[0] = getArrayTimes(R.string.time_saturdayHours);
//                HMR[1] = getArrayTimes(R.string.time_saturdayMinutes);
//                HMR[2] = getArrayTimes(R.string.time_saturdayRests);
//                return HMR;
//            }
//            case Calendar.MONDAY: { }
//            case Calendar.TUESDAY: { }
//            case Calendar.THURSDAY: { }
//            case Calendar.FRIDAY: {
//                today = typeToday.OTHER;
//                HMR[0] = getArrayTimes(R.string.time_otherDayHours);
//                HMR[1] = getArrayTimes(R.string.time_otherDayMinutes);
//                HMR[2] = getArrayTimes(R.string.time_otherDayRests);
//                return HMR;
//            }
//            default: {
//                mBiTime.setMessage(BiTime.Message.TodayIsNotSupport);
//                Log.e(TAG, "getInfoLess return null");
//                return null;
//            }
//        }
//    }

    private int[] getArrayTimes(int id) {
        return convertStringArrayToIntArray(context.getResources().getString(id).split(":"));
    }

    public BiTime getTimeLesson() {
        maxTimeLesson = new Date(0, 0, 0, hoursL[hoursL.length - 1], minutL[minutL.length - 1], 0);
        Date minTimeLesson = new Date(0, 0, 0, hoursL[0], minutL[0], 0);

        Log.i(TAG, "maxH: " + maxTimeLesson.getHours() + ", maxM: " + maxTimeLesson.getMinutes() + ",time: " + maxTimeLesson.getTime());
        Log.i(TAG, "locH: " + UtilsX.LessonTime.getTimeLocalDate().getHours() + ",locM: " + UtilsX.LessonTime.getTimeLocalDate().getMinutes() + " ,time: " + UtilsX.LessonTime.getTimeLocalDate().getTime());
        Log.i(TAG, "minH: " + minTimeLesson.getHours() + ", minM: " + minTimeLesson.getMinutes() + " ,time: " + minTimeLesson.getTime());

        if(hoursL == null || minutL == null) return mBiTime.setMessage(BiTime.Message.HMRIsNull);
        if(hoursL.length != minutL.length) return mBiTime.setMessage(BiTime.Message.HoursIsNotEqualMinuts);
        if(UtilsX.LessonTime.getTimeLocalDate().getTime() > maxTimeLesson.getTime()) return mBiTime.setMessage(BiTime.Message.BeforeLesson);
        if(UtilsX.LessonTime.getTimeLocalDate().getTime() < minTimeLesson.getTime()) return mBiTime.setMessage(BiTime.Message.AfterLesson);


        mBiTime.setMaxTimeLesson(maxTimeLesson);
        LessonTime lessonTime = new LessonTime();
        Time gtime = new Time(date.getHours(), date.getMinutes(), 0);
        for(int i = 0, n = 1; i < hoursL.length - 1; i+=2, n++) {
            Time time = new Time(hoursL[i], minutL[i], 0);
            Time time1 = new Time(hoursL[i + 1], minutL[i + 1], 0);
            if (gtime.getTime() >= time.getTime()
                    && gtime.getTime() < time1.getTime()) {
                lessonTime.setNumberLesson(n);
                lessonTime.setRestAfterLesson(restL[n - 1]);
                lessonTime.setToday(today);
                lessonTime.setLessonCompleteIn(substractionTime(time1, gtime).getMinutes());
                lessonTime.setLessonPassed(substractionTime(gtime, time).getMinutes());
                lessonTime.setLessonStartAt(time);
                lessonTime.setLessonEndAt(time1);
                return mBiTime.setData(lessonTime);
            }
        }
        return analyzeREst();
    }

    private Time substractionTime(Time time, Time substr) {
        long diff = time.getTime() - substr.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        return new Time((int)diffHours, (int)diffMinutes, 0);
    }

    private BiTime analyzeREst() {
        RestTime rest = new RestTime();
        Time gtime = new Time(date.getHours(), date.getMinutes(), 0);
        for(int i = 1, n = 0; i < hoursL.length - 1; i+=2, n++) {
            Time time1 = new Time(hoursL[i], minutL[i], 0);
            Time timerest = new Time(hoursL[i + 1], minutL[i + 1], 0);
            if(gtime.getTime() >= time1.getTime()
                    && gtime.getTime() < timerest.getTime()) {
                rest.setRestAfterNumberLesson(n + 1);
                rest.setRestFullTime(restL[n]);
                rest.setToday(today);
                rest.setNextLessonTime(timerest);
                rest.setRestPassed(substractionTime(gtime, time1).getMinutes());
                rest.setRestCompleteIn(substractionTime(timerest, gtime).getMinutes());
                return mBiTime.setData(rest);
            }
        }
        return mBiTime.setMessage(BiTime.Message.TimeIndexOfIsNull);
    }

    private int[] convertStringArrayToIntArray(String[] arr) {
        int[] intArr = new int[arr.length];
        for(int i = 0; i < arr.length; i++)
            intArr[i] = Integer.parseInt(arr[i]);
        return intArr;
    }

    public enum typeToday {WEDNESDAY, SATURDAY, OTHER, SERVER}
}
