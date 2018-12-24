package com.tile.yvesv.nativeappsiproject.networking

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
/**
 * @constructor Sets all properties of the activity
 * @property activity Description of the activity
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