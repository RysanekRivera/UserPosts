package com.rysanek.userposts.data.models.posts

import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import com.google.gson.annotations.SerializedName
import com.rysanek.userposts.data.models.comments.Comment
import com.rysanek.userposts.data.models.realm.PostRealm
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    @SerializedName("body")
    val body: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("userId")
    val userId: Int?,
    var comments: List<Comment>? = mutableStateListOf()
): Parcelable {

    fun toPostRealm() = PostRealm(
        body = body ?: "",
        id = id,
        title = title ?: "",
        comments = comments?.map { it.toCommentRealm() }?.toRealmList() ?: realmListOf()
    )

}