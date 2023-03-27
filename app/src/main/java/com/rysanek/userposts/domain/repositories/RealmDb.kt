package com.rysanek.userposts.domain.repositories

import com.rysanek.userposts.data.models.realm.UserRealm
import com.rysanek.userposts.data.repositories.RealmRepository
import com.rysanek.userposts.domain.utils.Constants.ATLAS_APP_ID
import io.realm.kotlin.Realm
import io.realm.kotlin.internal.interop.sync.SyncUserIdentity
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.subscriptions
import io.realm.kotlin.mongodb.sync.SyncConfiguration

object RealmDb : RealmRepository {

    private val app = App.create(ATLAS_APP_ID)
    private val user = app.currentUser
    lateinit var realm: Realm

    override fun configureRealm() {
        user?.let {
            if (user.loggedIn) {
                val config = SyncConfiguration.Builder(user = user, schema = setOf(UserRealm::class))
                    .initialSubscriptions {sub ->
                        add(
                            query = sub.query(clazz = UserRealm::class, query = "_id == $0 AND name == $1", user.id),
                            name = user.id
                        )
                    }
                    .log(LogLevel.ALL)
                    .build()

                realm = Realm.open(config)
            }
        }
    }
}