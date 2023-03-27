package com.rysanek.userposts.data.models.users

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rysanek.userposts.data.models.realm.GeoRealm
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geo(
    @SerializedName("lat")
    val lat: String?,
    @SerializedName("lng")
    val lng: String?
): Parcelable {

    fun toGeoRealm() = GeoRealm(
        lat = lat ?: "",
        lng = lng ?: ""
    )

}