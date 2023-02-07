package ru.scid.supportchat.util

data class Resource<out T>(val status: Status, val data: T? = null, val message: String? = null, val code: Int? = null) {

    enum class Status {
        SUCCESS,
        ERROR,
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(message: String, data: T? = null, code: Int? = null): Resource<T> {
            return Resource(Status.ERROR, data, message, code)
        }

    }
}