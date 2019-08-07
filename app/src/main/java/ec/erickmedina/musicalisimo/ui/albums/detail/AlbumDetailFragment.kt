package ec.erickmedina.musicalisimo.ui.albums.detail

import android.view.MenuItem
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import ec.erickmedina.musicalisimo.ui.albums.list.AlbumDetailContract

class AlbumDetailFragment : BaseFragment(), AlbumDetailContract.View {

    override fun getLayoutId(): Int = R.layout.fragment_album_detail

    override fun initView() {
        setActivityTitle("Album Detail")
        setActivityButtonUp(true)
        setHasOptionsMenu(true)
    }

    override val mViewModel: BaseViewModel
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
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