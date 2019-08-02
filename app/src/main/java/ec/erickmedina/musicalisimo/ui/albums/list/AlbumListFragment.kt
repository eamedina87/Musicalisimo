package ec.erickmedina.musicalisimo.ui.albums.list

import android.util.Log
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import androidx.navigation.get
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_album_list.*

class AlbumListFragment : BaseFragment(), AlbumDetailContract.View {

    override fun getLayoutId(): Int = R.layout.fragment_album_list

    val navController by lazy { findNavController() }

    override fun initView() {
        detail_button.setOnClickListener {
            navigator.goToAlbumDetail(this@AlbumListFragment)
        }
        setActivityTitle("Album List")
    }

    override val mViewModel: BaseViewModel
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    override fun onResume() {
        super.onResume()
        if (navController.graph.startDestination != navController.currentDestination?.id) {
            //Comes from search -> display up button
            setHasOptionsMenu(true)
            setActivityButtonUp(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigator.pop(this@AlbumListFragment)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}