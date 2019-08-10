package ec.erickmedina.domain.states

sealed class NetworkState {
    object Loading : NetworkState()
    object Success: NetworkState()
    class Error(val error: Any) : NetworkState()
}