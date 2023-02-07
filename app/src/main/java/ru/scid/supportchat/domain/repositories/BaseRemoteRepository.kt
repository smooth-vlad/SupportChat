package ru.scid.supportchat.domain.repositories

import org.json.JSONObject
import retrofit2.Response
import ru.scid.supportchat.util.Resource

open class BaseRemoteRepository {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            response.errorBody()?.string()?.let {
                val jsonObject = JSONObject(it)
                val message = "${jsonObject.getString("error")} - ${jsonObject.getString("description")}"
                if (message.isNotEmpty()) {
                    return Resource.error(message, null, response.code())
                }
            }
            return Resource.error(" ${response.code()} ${response.message()}", null, response.code())
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.error(e.message ?: e.toString(), null, null)
        }
    }


}