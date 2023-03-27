package com.rysanek.userposts.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.kotlin.Realm

@HiltAndroidApp
class UserPostsApp: Application()