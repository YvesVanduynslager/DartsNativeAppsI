package com.tile.yvesv.nativeappsiproject.gui.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

/**
 * @class [BoredActivityViewModel]: ViewModel that requests data and holds the result from API calls
 *
 * @property rawActivity: Holds the result from API calls.
 *
 * @author Yves Vanduynslager
 * */
class BoredActivityViewModel : BaseViewModel()
{
    private val rawActivity = MutableLiveData<String>()

    /**
     * The instance of the BoredActApi class to get back the results of the API
     * Injected with ViewModelInjectorComponent
     */
    @Inject
    lateinit var boredActApi: BoredActApi

    /**
     * Indicates whether the loading view should be displayed
     * //TODO: check how to use [loadingVisibility]
     */
    private val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    /**
     * Represents a disposable resource
     */
    private lateinit var subscription: Disposable

    init
    {
        /**
         * Immediately retrieve an activity when initializing the BoredActivityViewModel
         */
        this.newActivity()
    }

    /**
     * Retrieve a new boredActivity by fetching data on the background.
     * Display fetched data on the UI main thread.
     */
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
                        //onSuccess
                        { result ->
                            onRetrieveActivitySuccess(result)
                        },
                        //onError
                        //TODO: check if this can be removed
                        { error ->
                            onRetrieveActivityError(error)
                        }
                )
    }

    /**
     * Executes when there's an error retrieving data from the API
     */
    private fun onRetrieveActivityError(error: Throwable)
    {
        when (error)
        {
            is SocketTimeoutException ->
            {
                Log.e("boredAPI", error.message.toString())
            }
            is IOException ->
            {
                Logger.e("boredAPI", error.message)
            }
            is HttpException ->
            {
                Logger.e("boredAPI", error.message)
            }
            is UnknownHostException ->
            {
                Logger.e("boredAPI", error.message)
            }
            is UndeliverableException ->
            {
                Logger.e("boredAPI", error.message)
            }
        }
    }

    /**
     * Executes when retrieving data from the API is successful
     * @param result: the result received from the API
     */
    private fun onRetrieveActivitySuccess(result: BoredAct)
    {
        rawActivity.value = result.activity
        Log.i("boredAPI", result.activity)
    }

    /**
     * When retrieving is finished, set [loadingVisibility]
     */
    private fun onRetrieveActivityFinish()
    {
        loadingVisibility.value = View.GONE
    }

    /**
     * When retrieving starts, set [loadingVisibility]
     */
    private fun onRetrieveActivityStart()
    {
        loadingVisibility.value = View.VISIBLE
    }

    /**
     * Disposes the subscription when the [BaseViewModel] is no longer used.
     * When the Disposable [subscription] get disposed, the Observer will no longer
     * receive values from the Observable.
     * @url: https://medium.com/@vanniktech/rxjava-2-disposable-under-the-hood-f842d2373e64
     */
    /*override fun onCleared()
    {
        super.onCleared()
        subscription.dispose()
    }*/

    /**
     * @return The rawActivity with the data retrieved from the API
     */
    fun getRawAct(): MutableLiveData<String>
    {
        return rawActivity
    }
}