
package ec.erickmedina.domain.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ec.erickmedina.domain.states.NetworkState

data class Listing<T>(
        val pagedList: LiveData<PagedList<T>>,
        val dataState: LiveData<NetworkState>)