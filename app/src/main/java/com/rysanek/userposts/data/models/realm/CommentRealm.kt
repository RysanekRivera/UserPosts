package com.rysanek.userposts.data.models.realm

import io.realm.kotlin.types.RealmObject

open class CommentRealm(
    var id: Int = 0,
    var postId: Int = 0,
    var name: String? = "",
    var body: String? = "",
    var email: String? = ""
): RealmObject {
    constructor() : this(0)

}