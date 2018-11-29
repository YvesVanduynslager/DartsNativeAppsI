package com.tile.yvesv.nativeappsiproject.networking

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * The interface which provides methods to get the result of the Boredapi.com webservice
 */
interface BoredActApi
{
    /**
     * Get an activity from the API
     */
    @GET("/api/activity/")
    fun getBoredActivity(): Observable<BoredAct>

    /*
    @GET("/api/metar/{ icao }")
    fun getMetar(@Path("icao")icao:String) : Observable <Metar>
    */
}