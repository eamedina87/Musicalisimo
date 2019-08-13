package ec.erickmedina.musicalisimo.ui.albums.detail

import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.models.TrackModel
import ec.erickmedina.domain.models.getImage
import ec.erickmedina.domain.states.AlbumAction
import ec.erickmedina.domain.states.DataState
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import ec.erickmedina.musicalisimo.ui.albums.detail.adapter.TrackAdapter
import ec.erickmedina.musicalisimo.utils.ImageLoader
import ec.erickmedina.musicalisimo.utils.invisible
import ec.erickmedina.musicalisimo.utils.visible
import kotlinx.android.synthetic.main.fragment_album_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumDetailFragment : BaseFragment(), AlbumDetailContract.View {

    private var mAlbum: AlbumModel? = null
    private val args: AlbumDetailFragmentArgs by navArgs()

    override fun getLayoutId(): Int = R.layout.fragment_album_detail

    override val mViewModel by viewModel<AlbumDetailViewModel>()

    override fun initView() {
        setActivityTitle("Album Detail")
        setActivityButtonUp(true)
        setHasOptionsMenu(true)
        album_fav_button.setOnClickListener { onFavClicked() }
        initViewModel()
        getAlbumInfoFor(args.albumId)
    }

    private fun onFavClicked() {
        mAlbum?.let {
            if (it.isSaved) {
                mViewModel.deleteAlbum(it)
            } else {
                mViewModel.saveAlbum(it)
            }
        }
    }

    private fun initViewModel() {
        mViewModel.getAlbumActionObservable().observe(this, Observer {
            pair ->
            val action = pair.first
            val album = pair.second
            when (action) {
                AlbumAction.Save -> {
                    if (album != null) {
                        mAlbum = album
                        onAlbumSaveSuccess()
                    }
                    else onAlbumSaveError(null)
                }
                AlbumAction.Delete -> {
                    if (album != null) {
                        mAlbum = album
                        onAlbumDeleteSuccess()
                    }
                    else onAlbumDeleteError(null)
                }
            }
        })
        mViewModel.getAlbumObservable().observe(this, Observer {
            state ->
            when (state) {
                is DataState.Success -> {
                    hideProgress()
                    onAlbumSuccess(state.data)
                }
                is DataState.Error -> {
                    hideProgress()
                    onAlbumError(state.error.toString())
                }
            }
        })
    }

    private fun getAlbumInfoFor(albumId: String) {
        showProgress()
        mViewModel.getAlbumInfoFor(albumId)
    }

    override fun showProgress() {
        progressbar.visible()
    }

    override fun hideProgress() {
        progressbar.invisible()
    }

    override fun onAlbumSuccess(album: AlbumModel) {
        mAlbum = album
        album_title.text = album.name
        album_artist.text = album.artist
        ImageLoader.loadInImageView(album.getImage(), album_image)
        setTracks(album.tracks)
        album_fav_button.visible()
        album_fav_button.isSelected = album.isSaved
    }

    private fun setTracks(tracks: ArrayList<TrackModel>) {
        val adapter = TrackAdapter(tracks)
        album_track_list.adapter = adapter
    }

    override fun onAlbumError(error: String?) {
        showMessage(error ?: getString(R.string.search_error_default))
        navigator.pop(this)
    }

    override fun onAlbumSaveSuccess() {
        album_fav_button.isSelected = true
    }

    override fun onAlbumSaveError(error: String?) {
        album_fav_button.isSelected = false
        showMessage(error ?: getString(R.string.search_error_default))
    }

    override fun onAlbumDeleteSuccess() {
        album_fav_button.isSelected = false
    }

    override fun onAlbumDeleteError(error: String?) {
        album_fav_button.isSelected = true
        showMessage(error ?: getString(R.string.search_error_default))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigator.pop(this@AlbumDetailFragment)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}