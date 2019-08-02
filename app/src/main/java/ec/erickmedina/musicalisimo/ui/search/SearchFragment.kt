package ec.erickmedina.musicalisimo.ui.search

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

}