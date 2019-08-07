package ec.erickmedina.data.util

import java.io.IOException

class NoConnectivityException(override var message: String) : IOException(message)
