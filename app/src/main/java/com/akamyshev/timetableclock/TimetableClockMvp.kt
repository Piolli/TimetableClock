package com.akamyshev.timetableclock

import com.akamyshev.timetableclock.timez.LessonPeriod

/**
 * Created by alexandr on 10.08.17.
 */
interface TimetableClockMvp {


    interface View : MvpView{
        fun setDataForAnalyze(dataTimes: ArrayList<LessonPeriod>, isFromServer: Boolean)
        fun setProgress(value: Int)
        fun showTitleInfo(text: String)
    }

    interface Presenter : MvpPresenter<TimetableClockMvp.View> {
        fun onTimeError(error: String)
    }
}