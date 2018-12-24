package com.tile.yvesv.nativeappsiproject.networking

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @interface [BoredActApi]: The interface which provides methods to get the result of the Boredapi.com webservice
 *
 * @author Yves Vanduynslager
 */
interface BoredActApi
{
    /**
     * Get a random activity from the API.
     */
    @GET("/api/activity/")
    fun getBoredActivity(): Observable<BoredAct>

    /**
     * Get an activity based on number of participants.
     */
    @GET("/api/activity/")
    fun getBoredActivity(@Path("nr")participants:Int) : Observable<BoredAct>
    /*
    @GET("/api/metar/{ icao }")
    fun getMetar(@Path("icao")icao:String) : Observable <Metar>
    */
}