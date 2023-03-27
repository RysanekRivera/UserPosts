package com.rysanek.userposts.data.models.realm

import io.realm.kotlin.types.RealmObject

open class AddressRealm(
    var street: String = "",
    var suite: String = "",
    var city: String = "",
    var zipcode: String = "",
    var geo: GeoRealm? = null
) : RealmObject {
    constructor() : this("")

}