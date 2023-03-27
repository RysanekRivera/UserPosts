package com.rysanek.userposts.data.models.users

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rysanek.userposts.data.models.realm.AddressRealm
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    @SerializedName("city")
    val city: String?,
    @SerializedName("geo")
    val geo: Geo?,
    @SerializedName("street")
    val street: String?,
    @SerializedName("suite")
    val suite: String?,
    @SerializedName("zipcode")
    val zipcode: String?
): Parcelable {

    fun toAddressRealm() = AddressRealm(
        city = city ?: "",
        geo = geo?.toGeoRealm(),
        street = street ?: "",
        suite = suite ?: "",
        zipcode = zipcode ?: ""
    )

}