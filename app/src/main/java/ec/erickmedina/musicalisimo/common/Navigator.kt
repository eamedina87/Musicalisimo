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

    fun goToTopAlbumList(fragment: BaseFragment) {
        fragment.findNavController().navigate(R.id.action_navigation_search_to_navigation_top_albums)
    }

    fun goToLocalAlbumList(fragment: BaseFragment) {
        fragment.findNavController().navigate(R.id.navigation_local_albums)
    }

    fun goToAlbumDetail(fragment: BaseFragment) {
        fragment.findNavController().navigate(R.id.navigation_album_detail)
    }

    fun pop(fragment: BaseFragment) {
        fragment.findNavController().popBackStack()
    }

}