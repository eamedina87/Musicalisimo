package ec.erickmedina.domain.exceptions

import java.io.IOException

class NoConnectivityException(override var message: String) : IOException(message)
