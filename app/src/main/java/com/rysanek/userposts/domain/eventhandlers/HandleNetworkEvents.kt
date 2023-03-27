package com.rysanek.userposts.domain.eventhandlers

abstract class HandleNetworkEvents : OnNetworkEvent {
    protected var success: () -> Unit = {}
    protected var error: (message: String?) -> Unit = {}
    protected var loading: () -> Unit = {}
    protected var idle: () -> Unit = {}
}