package ec.erickmedina.musicalisimo.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.container_main.*

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
    }

    fun setActivityTitle(title: String) {
        supportActionBar!!.title = title
        //toolbar.title = title
    }

    fun displayHomeAsUpButton(show: Boolean) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(show)
    }


}