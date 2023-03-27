package com.rysanek.userposts.data.models.users

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rysanek.userposts.data.models.realm.CompanyRealm
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    @SerializedName("bs")
    val bs: String?,
    @SerializedName("catchPhrase")
    val catchPhrase: String?,
    @SerializedName("name")
    val name: String?
): Parcelable {

    fun toCompanyRealm() = CompanyRealm(
        bs = bs ?: "",
        name = name ?: "",
        catchPhrase = catchPhrase ?: "",
    )
}