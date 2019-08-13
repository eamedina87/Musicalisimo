package ec.erickmedina.data.util

import com.google.gson.Gson
import ec.erickmedina.data.entity.ErrorEntity
import retrofit2.Response

fun <T> Response<T>.handleErrors(errorMessage: String) : T =
    try {
        if (this.isSuccessful) {
            this.body()?.let {
                return it
            }
            throw Exception(errorMessage)
        } else {
            val message = this.errorBody()?.charStream()?.readText()
            val mError = Gson().fromJson(message, ErrorEntity::class.java)
            throw Exception(mError.message)
        }
    } catch (e: Exception){
        throw Exception(e.message)
    }