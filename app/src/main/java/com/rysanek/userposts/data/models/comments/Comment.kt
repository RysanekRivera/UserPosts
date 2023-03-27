package com.rysanek.userposts.data.models.comments

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rysanek.userposts.data.models.realm.CommentRealm
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
    @SerializedName("body")
    val body: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("postId")
    val postId: Int
): Parcelable {

    fun toCommentRealm() = CommentRealm(
        id = id,
        postId = postId,
        name = name,
        body = body,
        email = email
    )
}