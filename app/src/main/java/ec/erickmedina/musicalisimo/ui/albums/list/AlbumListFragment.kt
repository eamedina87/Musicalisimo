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

    val navFragment by lazy { getBaseActivity().supportFragmentManager.findFragmentById(R.id.navigation) }
    val mustShowUp by lazy { navFragment?.childFragmentManager?.backStackEntryCount!! > 1 }

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
        checkNavigateUp()
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

    private fun checkNavigateUp() {
        setHasOptionsMenu(mustShowUp)
        setActivityButtonUp(mustShowUp)
    }

}