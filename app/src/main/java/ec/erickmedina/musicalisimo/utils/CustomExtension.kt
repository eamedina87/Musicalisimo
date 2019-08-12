package ec.erickmedina.musicalisimo.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.ImageSize
import java.util.concurrent.TimeUnit

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.invisible() {
    this.visibility = GONE
}

fun Long.toDurationString() : String {
    val minutes = TimeUnit.SECONDS.toMinutes(this)
    val seconds = TimeUnit.SECONDS.toSeconds(this) % 60
    return when (seconds > 9) {
        true -> "0$minutes:$seconds"
        false -> "0$minutes:0$seconds"
    }
}

