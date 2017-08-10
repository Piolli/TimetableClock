package com.akamyshev.timetableclock

/**
 * Created by alexandr on 10.08.17.
 */
abstract class BaseMvpPresenter<T : MvpView> : MvpPresenter<T> {

    var view: T? = null
        private set

    override fun attachView(mvpView: T) {
        view = mvpView
    }

    override fun detachView() {
        view = null
    }

    protected val isViewAttached: Boolean
        get() = view != null

    override fun destroy() {

    }
}