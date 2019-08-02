package ec.erickmedina.musicalisimo.common

import androidx.navigation.fragment.findNavController
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseActivity
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import javax.inject.Singleton

@Singleton
class Navigator {

    fun showDialog(fragment: BaseFragment) {

    }

    fun goToAlbumList(fragment: BaseFragment) {
        fragment.findNavController().navigate(R.id.albumListFragment)
    }

    fun goToAlbumDetail(fragment: BaseFragment) {
        fragment.findNavController().navigate(R.id.albumDetailFragment)
    }

    fun pop(fragment: BaseFragment) {
        fragment.findNavController().popBackStack()
    }

}