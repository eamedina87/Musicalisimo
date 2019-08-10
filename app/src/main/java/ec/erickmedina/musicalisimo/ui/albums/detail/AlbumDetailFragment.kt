package ec.erickmedina.musicalisimo.ui.albums.detail

import android.view.MenuItem
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumDetailFragment : BaseFragment(), AlbumDetailContract.View {

    override fun getLayoutId(): Int = R.layout.fragment_album_detail

    override val mViewModel by viewModel<AlbumDetailViewModel>()

    override fun initView() {
        setActivityTitle("Album Detail")
        setActivityButtonUp(true)
        setHasOptionsMenu(true)
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAlbumSuccess(album: AlbumModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAlbumError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAlbumSaveSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAlbumSaveError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAlbumDeleteSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAlbumDeleteError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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