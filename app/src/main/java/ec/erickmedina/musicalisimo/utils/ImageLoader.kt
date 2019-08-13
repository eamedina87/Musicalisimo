package ec.erickmedina.musicalisimo.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader {

    companion object {
        fun loadInImageView(url: String, view: ImageView) {
            Glide.with(view.context).load(url).into(view)
        }
    }

}