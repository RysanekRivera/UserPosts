package com.rysanek.userposts.domain.eventhandlers

import javax.inject.Inject

/**
 * This class allows activities of fragments to define what happens when network events are
 * triggered.
 * **/
class NetworkEventHandler @Inject constructor() : HandleNetworkEvents() {
    override fun setOnSuccess(onSuccess: () -> Unit) { success = onSuccess }
    override fun setOnError(onError: (message: String?) -> Unit) { error = onError }
    override fun setOnLoading(onLoading: () -> Unit) { loading = onLoading }
    override fun setOnIdle(onIdle: () -> Unit) { idle = onIdle }
    override fun onSuccess() = success.invoke()
    override fun onError(message: String?) = error.invoke(message)
    override fun onLoading() = loading.invoke()
    override fun onIdle() = idle.invoke()
}