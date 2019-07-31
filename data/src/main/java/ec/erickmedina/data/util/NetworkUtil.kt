package ec.erickmedina.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

class NetworkUtil {
    companion object {
        @SuppressLint("MissingPermission")
        fun isOnline(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
    }
}
