package ec.erickmedina.musicalisimo.ui.search

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.domain.states.NetworkState
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import ec.erickmedina.musicalisimo.ui.search.adapter.SearchAdapter
import ec.erickmedina.musicalisimo.ui.search.adapter.SearchPageAdapter
import ec.erickmedina.musicalisimo.utils.invisible
import ec.erickmedina.musicalisimo.utils.visible
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchPagingFragment : BaseFragment(), SearchContract.View {

    override fun getLayoutId(): Int = R.layout.fragment_search

    override val mViewModel by viewModel<SearchPagingViewModel>()

    private var mAdapter : SearchPageAdapter = SearchPageAdapter()

    override fun initView() {
        setActivityTitle("Search")
        setActivityButtonUp(false)
        setupRecyclerView()
        search_img.setOnClickListener { searchForArtist() }
    }

    private fun setupRecyclerView() {
        search_list_container.adapter = mAdapter
        search_list_container.layoutManager = GridLayoutManager(context, 2)
    }

    private fun searchForArtist() {
        when {
            search_text.text.toString().isNotEmpty() -> {
                val listing = mViewModel.getArtistForInput(search_text.text.toString())
                listing.pagedList.observe(this, Observer {
                    when (it.size) {
                        0 -> onArtistSearchEmpty()
                        else -> onArtistSearchSuccess(it)
                    }
                })
                listing.dataState.observe(this, Observer {
                    when (it) {
                        is NetworkState.Loading -> showProgress()
                        is NetworkState.Success -> hideProgress()
                        is NetworkState.Error -> {
                            hideProgress()
                            onArtistSearchError(it.error.toString())
                        }
                    }
                })
            }
            else -> search_text.requestFocus()
        }
    }

    private fun initViewModel() {

    }

    override fun showProgress() {
        progressbar.visible()
    }

    override fun hideProgress() {
        progressbar.invisible()
    }

    override fun onArtistSearchSuccess(list: PagedList<ArtistModel>) {
        mAdapter.submitList(list)
    }

    override fun onArtistSearchEmpty() {

    }

    override fun onArtistSearchError(error: String?) {
        showMessage(error ?: getString(R.string.search_error_default))
    }

}