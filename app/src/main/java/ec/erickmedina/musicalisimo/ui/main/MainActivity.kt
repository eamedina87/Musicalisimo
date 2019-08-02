package ec.erickmedina.musicalisimo.ui.main

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.common.base.BaseActivity
import ec.erickmedina.musicalisimo.common.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.container_main.*

class MainActivity : BaseActivity(), MainContract.View {

    override fun getLayoutId(): Int = R.layout.container_main

    override fun initView() {
        bottomNavigation.setupWithNavController(findNavController(R.id.navigation))
        setSupportActionBar(toolbar)
        supportActionBar?.setIcon(R.drawable.ic_music)
    }

    override val mViewModel: BaseViewModel
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}
