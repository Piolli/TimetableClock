package com.akamyshev.timetableclock

/**
 * Created by alexandr on 10.08.17.
 */
interface MvpPresenter<in V : MvpView> {

    fun attachView(mvpView: V)

    fun viewIsReady()

    fun detachView()

    fun destroy()
}