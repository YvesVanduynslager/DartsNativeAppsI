package com.tile.yvesv.nativeappsiproject.gui.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.orhanobut.logger.Logger
import com.tile.yvesv.nativeappsiproject.gui.base.BaseViewModel
import com.tile.yvesv.nativeappsiproject.networking.BoredAct
import com.tile.yvesv.nativeappsiproject.networking.BoredActApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class BoredActivityViewModel : BaseViewModel()
{
    private val rawActivity = MutableLiveData<String>()

    /**
     * The instance of the BoredActApi class to get back the results of the API
     */
    @Inject //Injected with class ViewModelInjectorComponent
    lateinit var boredActApi: BoredActApi

    /**
     * Indicates whether the loading view should be displayed
     */
    private val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    /**
     * Represents a disposable resource
     */
    private lateinit var subscription: Disposable

    init
    {
        this.newActivity()
    }

    fun newActivity()
    {
        RxJavaPlugins.setErrorHandler { e -> onRetrieveActivityError(e) }

        subscription = boredActApi.getBoredActivity()
                //we tell it to fetch the data on background by
                .subscribeOn(Schedulers.io())
                //we like the fetched data to be displayed on the MainTread (UI)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveActivityStart() }
                .doOnTerminate { onRetrieveActivityFinish() }
                .subscribe(
                        //success
                        { result ->
                            onRetrieveActivitySuccess(result)
                        },
                        //error
                        //TODO: check if this can be removed
                        { error ->
                            onRetrieveActivityError(error)
                        }
                )
    }

    private fun onRetrieveActivityError(error: Throwable)
    {
        when (error)
        {
            //TODO: check why logger doesn't log
            //TODO: find out how to display toasts in activity from here
            is SocketTimeoutException ->
            {
                //Toast.makeText(this, "The server took to long to respond", Toast.LENGTH_LONG).show()
                Logger.e(error, "Socket timeout")
            }
            is IOException ->
            {
                //Toast.makeText(this, "You are not connected to the Internet!", Toast.LENGTH_LONG).show()
                Logger.e(error, "No connection")
            }
            is HttpException ->
            {
                //Toast.makeText(this, "You are not connected to the Internet!", Toast.LENGTH_LONG).show()
                Logger.e(error, "Http status error")
            }
            is UnknownHostException ->
            {
                //Toast.makeText(this, "You are not connected to the Internet!", Toast.LENGTH_LONG).show()
                Logger.e(error, "Host not found")
                Logger.d("test")
            }
            is UndeliverableException ->
            {
                Logger.e(error, "Undeliverable error")
            }
        }
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