package ec.erickmedina.musicalisimo.ui.albums.list.local

import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import ec.erickmedina.musicalisimo.ui.albums.list.AlbumListViewModel
import kotlinx.android.synthetic.main.fragment_album_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumLocalFragment : BaseFragment(), AlbumLocalContract.View {

    override fun getLayoutId(): Int = R.layout.fragment_album_list

    override val mViewModel by viewModel<AlbumListViewModel>()

    override fun initView() {
        setActivityButtonUp(false)
        setActivityTitle("Album List")
    }

    override fun onResume() {
        super.onResume()
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocalAlbumsLoaded(albumList: ArrayList<AlbumModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocalAlbumsEmpty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocalAlbumsError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}