package ec.erickmedina.musicalisimo.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.ImageSize

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.invisible() {
    this.visibility = GONE
}

