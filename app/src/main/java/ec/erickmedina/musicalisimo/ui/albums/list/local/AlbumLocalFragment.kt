package ec.erickmedina.musicalisimo.ui.albums.list.local

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import ec.erickmedina.musicalisimo.ui.albums.list.AlbumListViewModel
import ec.erickmedina.musicalisimo.ui.albums.list.local.adapter.LocalAlbumsAdapter
import ec.erickmedina.musicalisimo.utils.invisible
import ec.erickmedina.musicalisimo.utils.visible
import kotlinx.android.synthetic.main.fragment_album_list.*
import kotlinx.android.synthetic.main.layout_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumLocalFragment : BaseFragment(), AlbumLocalContract.View {

    override fun getLayoutId(): Int = R.layout.fragment_album_list

    private var mAdapter: LocalAlbumsAdapter? = null
    override val mViewModel by viewModel<AlbumListViewModel>()

    override fun initView() {
        setActivityButtonUp(false)
        setActivityTitle("Album List")
        initVewModel()
        mViewModel.getLocalAlbums()
    }

    private fun initVewModel() {
        mViewModel.getLocalAlbumsObservable().observe(this, Observer {
            state ->
            when (state) {
                is DataState.Success -> {
                    hideProgress()
                    state.data.observe(this, Observer {
                        when (it.size) {
                            0 -> onLocalAlbumsEmpty()
                            else -> onLocalAlbumsLoaded(it)
                        }
                    })
                }
                is DataState.Error -> {
                    hideProgress()
                    onLocalAlbumsError(state.error.toString())
                }
            }
        })
    }

    override fun showProgress() {
        progressbar.visible()
    }

    override fun hideProgress() {
        progressbar.invisible()
    }

    override fun onLocalAlbumsLoaded(albumList: ArrayList<AlbumModel>) {
        empty_message.invisible()
        if (mAdapter == null) {
            mAdapter = LocalAlbumsAdapter(albumList)
            val layoutManager = GridLayoutManager(context, 2)
            list_container.adapter = mAdapter
            list_container.layoutManager = layoutManager
        } else {
            mAdapter?.updateList(albumList)
        }
    }

    override fun onLocalAlbumsEmpty() {
        empty_message.visible()
    }

    override fun onLocalAlbumsError(error: String?) {
        showMessage(error ?: getString(R.string.search_error_default))
    }

}