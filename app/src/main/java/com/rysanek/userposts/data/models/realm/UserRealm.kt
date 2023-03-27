package com.rysanek.userposts.data.models.realm

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

open class UserRealm(
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke(),
    var id: Int = 0,
    var name: String = "",
    var email: String? = null,
    var phone: String? = null,
    var username: String? = null,
    var website: String? = null,
//    var posts: RealmList<PostRealm> = realmListOf(),
//    var address: AddressRealm? = null,
//    var company: CompanyRealm? = null
) : RealmObject {

    constructor() : this(ObjectId.invoke())

}