package com.evgeny5454.testapp.other

class Resource(val status: Status, val message: String?) {
    companion object {
        fun success() = Resource(Status.SUCCESS, null)
        fun error(message: String?) = Resource(Status.ERROR, message)
        fun loading() = Resource(Status.LOADING, null)
    }
}

enum class Status {
    SUCCESS, ERROR, LOADING
}