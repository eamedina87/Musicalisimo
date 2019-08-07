package ec.erickmedina.musicalisimo.ui.albums.list.local

import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_album_list.*

class AlbumLocalFragment : BaseFragment(), AlbumLocalContract.View {

    override fun getLayoutId(): Int = R.layout.fragment_album_list

    override fun initView() {
        detail_button.setOnClickListener {
            navigator.goToAlbumDetail(this@AlbumLocalFragment)
        }
        setActivityButtonUp(false)
        setActivityTitle("Album List")
    }

    override val mViewModel: BaseViewModel
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


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