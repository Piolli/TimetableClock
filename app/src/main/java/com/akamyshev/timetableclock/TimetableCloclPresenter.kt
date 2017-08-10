package com.akamyshev.timetableclock

/**
 * Created by alexandr on 10.08.17.
 */
class TimetableCloclPresenter : BaseMvpPresenter<TimetableClockMvp.View>(), TimetableClockMvp.Presenter {


    override fun viewIsReady() {
        view?.setProgress(50)

    }

    override fun onTimeError(error: String) {

    }

}