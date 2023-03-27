package com.rysanek.userposts.data.models.realm

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

open class PostRealm(
    var id: Int = 0,
    var title: String = "",
    var body: String = "",
    var comments: RealmList<CommentRealm> = realmListOf()
) : RealmObject {

    constructor() : this(0)

}