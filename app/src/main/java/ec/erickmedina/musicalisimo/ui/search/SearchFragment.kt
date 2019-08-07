package ec.erickmedina.musicalisimo.ui.search

import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseFragment
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment(), SearchContract.View {

    override fun getLayoutId(): Int = R.layout.fragment_search

    override fun initView() {
        detail_button.setOnClickListener {
            navigator.goToAlbumList(this@SearchFragment)
        }
        setActivityTitle("Search")
        setActivityButtonUp(false)
    }

    override val mViewModel: BaseViewModel
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onArtistSearchSuccess(list: ArrayList<ArtistModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onArtistSearchEmpty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onArtistSearchError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}