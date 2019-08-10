package ec.erickmedina.musicalisimo.ui.albums.list.top

import android.view.MenuItem
import ec.erickmedina.domain.models.TopAlbumModel
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import ec.erickmedina.musicalisimo.ui.albums.list.AlbumListViewModel
import kotlinx.android.synthetic.main.fragment_album_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumTopFragment : BaseFragment(), AlbumTopContract.View {


    override fun getLayoutId(): Int = R.layout.fragment_album_list

    override val mViewModel by viewModel<AlbumListViewModel>()

    override fun initView() {
        detail_button.setOnClickListener {
            navigator.goToAlbumDetail(this@AlbumTopFragment)
        }
        setHasOptionsMenu(true)
        setActivityButtonUp(true)
        setActivityTitle("Album List")
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTopAlbumsLoaded(albumList: ArrayList<TopAlbumModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTopAlbumsEmpty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTopAlbumsError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

}