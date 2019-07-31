package ec.erickmedina.data.remote

import android.content.Context
import ec.erickmedina.data.util.NetworkUtil
import ec.erickmedina.data.util.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtil.isOnline(context)) {
            throw NoConnectivityException("No internet connection detected.")
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}
