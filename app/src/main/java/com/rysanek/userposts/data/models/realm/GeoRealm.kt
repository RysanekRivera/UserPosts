package com.rysanek.userposts.data.models.realm

import io.realm.kotlin.types.RealmObject

open class GeoRealm(
    var lat: String = "",
    var lng: String = ""
) : RealmObject {

    constructor() : this("")

}