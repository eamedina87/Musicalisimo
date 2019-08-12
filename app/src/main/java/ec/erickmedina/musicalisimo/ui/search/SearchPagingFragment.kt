package ec.erickmedina.musicalisimo.ui.search

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.states.NetworkState
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import ec.erickmedina.musicalisimo.ui.search.adapter.SearchPageAdapter
import ec.erickmedina.musicalisimo.utils.invisible
import ec.erickmedina.musicalisimo.utils.visible
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchPagingFragment : BaseFragment(), SearchContract.View, SearchPageAdapter.SearchCallbacks {

    override fun getLayoutId(): Int = R.layout.fragment_search

    override val mViewModel by viewModel<SearchPagingViewModel>()

    private var mAdapter : SearchPageAdapter = SearchPageAdapter(this)

    override fun initView() {
        setActivityTitle("Search")
        setActivityButtonUp(false)
        setupRecyclerView()
        initViewModel()
        search_img.setOnClickListener { searchForArtist() }
    }

    private fun initViewModel() {
        mViewModel.getArtist().observe(this, Observer {
            listing ->
            if (listing == null) {
                onArtistSearchError(null)
                return@Observer
            }
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
        })
    }

    override fun showProgress() {
        progressbar.visible()
    }

    override fun hideProgress() {
        progressbar.invisible()
    }

    override fun onArtistSearchSuccess(list: PagedList<ArtistModel>) {
        empty_message.invisible()
        mAdapter.submitList(list)
    }

    override fun onArtistSearchEmpty() {
        empty_message.visible()
    }

    override fun onArtistSearchError(error: String?) {
        showMessage(error ?: getString(R.string.search_error_default))
    }

    //PRIVATE FUNCTIONS

    private fun setupRecyclerView() {
        list_container.adapter = mAdapter
        list_container.layoutManager = GridLayoutManager(context, 2)
    }

    private fun searchForArtist() {
        when {
            search_text.text.toString().isNotEmpty() -> {
                mViewModel.getArtistForInput(search_text.text.toString())
            }
            else -> search_text.requestFocus()
        }
    }

    //ADAPTER CALLBACKS

    override fun onArtistSelected(artist: ArtistModel) {
        val action = SearchPagingFragmentDirections.actionNavigationSearchToNavigationTopAlbums(artist.name)
        navigator.goToNext(this, action)
    }

}