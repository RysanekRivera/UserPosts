package com.rysanek.userposts.data.models.users

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rysanek.userposts.data.models.posts.Post
import com.rysanek.userposts.data.models.realm.UserRealm
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("address")
    val address: Address? = null,
    @SerializedName("company")
    val company: Company? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("website")
    val website: String? = null,
    var posts:List<Post>? = emptyList(),
): Parcelable {

    fun toUserRealm() = UserRealm(
        id = id,
        name = name,
        email = email,
        phone = phone,
        username = username,
        website = website,
//        address = address?.toAddressRealm(),
//        company = company?.toCompanyRealm(),
//        posts = posts?.map { it.toPostRealm() }?.toRealmList() ?: realmListOf()
    )

}