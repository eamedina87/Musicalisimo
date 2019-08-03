package ec.erickmedina.data.entity

import com.google.gson.annotations.SerializedName

data class ErrorEntity(
    @SerializedName("status") val status:String,
    @SerializedName("code") val code:Int,
    @SerializedName("message") val message:String)