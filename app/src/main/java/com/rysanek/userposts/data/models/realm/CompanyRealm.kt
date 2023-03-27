package com.rysanek.userposts.data.models.realm

import io.realm.kotlin.types.RealmObject

open class CompanyRealm(
    var name: String = "",
    var catchPhrase: String = "",
    var bs: String = ""
) : RealmObject {
    constructor() : this("")

}