package com.rysanek.userposts.domain.eventhandlers

interface OnNetworkEvent {
    fun setOnSuccess(onSuccess : () -> Unit)
    fun setOnError(onError : (message: String?) -> Unit)
    fun setOnLoading(onLoading : () -> Unit)
    fun setOnIdle(onIdle : () -> Unit)
    fun onSuccess()
    fun onError(message: String?)
    fun onLoading()
    fun onIdle()
}