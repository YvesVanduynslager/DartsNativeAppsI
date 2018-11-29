package com.tile.yvesv.nativeappsiproject.gui.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.orhanobut.logger.Logger
import com.tile.yvesv.nativeappsiproject.gui.base.BaseViewModel
import com.tile.yvesv.nativeappsiproject.networking.BoredAct
import com.tile.yvesv.nativeappsiproject.networking.BoredActApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BoredActivityViewModel : BaseViewModel()
{
    private val rawActivity = MutableLiveData<String>()

    /**
     * The instance of the BoredActApi class to get back the results of the API
     */
    @Inject
    lateinit var boredActApi: BoredActApi

    /**
     * Indicates whether the loading view should be displayed
     */
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    /**
     * Represents a disposable resource
     */
    private var subscription: Disposable

    init
    {
        subscription = boredActApi.getBoredActivity()
                //we tell it to fetch the data on background by
                .subscribeOn(Schedulers.io())
                //we like the fetched data to be displayed on the MainTread (UI)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveActivityStart() }
                .doOnTerminate { onRetrieveActivityFinish() }
                .subscribe(
                        { result ->
                            onRetrieveActivitySuccess(result)
                        },
                        { error ->
                            onRetrieveActivityError(error)
                        }
                )
    }

    private fun onRetrieveActivityError(error: Throwable)
    {
        Logger.e(error.message!!)
    }

    private fun onRetrieveActivitySuccess(result: BoredAct)
    {
        rawActivity.value = result.activity
        Logger.i(result.activity)
    }

    private fun onRetrieveActivityFinish()
    {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveActivityStart()
    {
        loadingVisibility.value = View.VISIBLE
    }

    /**
     * Disposes the subscription when the [BaseViewModel] is no longer used.
     */
    override fun onCleared(){
        super.onCleared()
        subscription.dispose()
    }

    fun getRawAct(): MutableLiveData<String> {
        return rawActivity
    }
}