package ec.erickmedina.musicalisimo.ui.albums.list.top

import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import ec.erickmedina.domain.models.TopAlbumModel
import ec.erickmedina.domain.states.NetworkState
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import ec.erickmedina.musicalisimo.ui.albums.list.AlbumListViewModel
import ec.erickmedina.musicalisimo.ui.albums.list.top.adapter.TopAlbumsPageAdapter
import ec.erickmedina.musicalisimo.utils.invisible
import ec.erickmedina.musicalisimo.utils.visible
import kotlinx.android.synthetic.main.layout_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumTopFragment : BaseFragment(), AlbumTopContract.View, TopAlbumsPageAdapter.TopAlbumCallbacks {

    val args: AlbumTopFragmentArgs by navArgs()

    override fun getLayoutId(): Int = R.layout.fragment_album_list

    override val mViewModel by viewModel<AlbumListViewModel>()

    private var mAdapter : TopAlbumsPageAdapter = TopAlbumsPageAdapter(this)

    override fun initView() {
        setHasOptionsMenu(true)
        setActivityButtonUp(true)
        setActivityTitle("Album List")
        setupRecyclerView()
        getAlbumsForArtist(args.artistId)
    }

    override fun showProgress() {
        progressbar.visible()
    }

    override fun hideProgress() {
        progressbar.invisible()
    }

    override fun onTopAlbumsSuccess(albumList: PagedList<TopAlbumModel>) {
        empty_message.invisible()
        mAdapter.submitList(albumList)
    }

    override fun onTopAlbumsEmpty() {
        empty_message.visible()
    }

    override fun onTopAlbumsError(error: String?) {
        showMessage(error ?: getString(R.string.search_error_default))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigator.pop(this@AlbumTopFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //PRIVATE FUNCTIONS

    private fun setupRecyclerView() {
        list_container.adapter = mAdapter
        list_container.layoutManager = GridLayoutManager(context, 2)
    }

    private fun getAlbumsForArtist(artistId: String) {
        val listing = mViewModel.getTopAlbumsFor(artistId)
        listing.pagedList.observe(this, Observer {
            when (it.size) {
                0 -> onTopAlbumsEmpty()
                else -> onTopAlbumsSuccess(it)
            }
        })
        listing.dataState.observe(this, Observer {
            when (it) {
                is NetworkState.Loading -> showProgress()
                is NetworkState.Success -> hideProgress()
                is NetworkState.Error -> {
                    hideProgress()
                    onTopAlbumsError(it.error.toString())
                }
            }
        })
    }

    //ADAPTER CALLBACKS

    override fun onAlbumSelected(album: TopAlbumModel) {
        val action = AlbumTopFragmentDirections.actionNavigationTopAlbumsToNavigationAlbumDetail(album.name)
        navigator.goToNext(this, action)
    }

}