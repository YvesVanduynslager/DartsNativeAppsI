package com.tile.yvesv.nativeappsiproject.networking

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Data class representing a Metar. METAR is a format for reporting weather information.
 * A METAR weather report is predominantly used by pilots in fulfillment of a part of a
 * pre-flight weather briefing, and by meteorologists, who use aggregated METAR
 * information to assist in weather forecasting.
 *
 *
 * @constructor Sets all properties of the post
 * @property act Description of the activity
 * @property rawMetar The raw code of the metar
 * @property airport The airport where this Metar belongs to
 * @property dayOfMonth The day of the month for this Metar
 * @property time The time of this Metar
 * @property windDirection The wind direction in Metar format (e.g. 12012MPS indicates
 * the wind direction is from 120° (east-southeast) at a speed of 12 m/s (23 knots; 27 mph; 44 km/h)
 * @property windSpeed Speed of the wind
 * @property gusts The possible gusts of the weather
 * @property lineOfSight The line of sight (expressed in meter)
 */
@Parcelize
class BoredAct(
        @field:Json(name = "activity") val activity: String,
        @field:Json(name = "accessibility") val accessibility: Double,
        @field:Json(name = "type") val type: String,
        @field:Json(name = "participants") val participants: Int,
        @field:Json(name = "price") val price: Double,
        @field:Json(name = "key") val key: Long)
    : Parcelable