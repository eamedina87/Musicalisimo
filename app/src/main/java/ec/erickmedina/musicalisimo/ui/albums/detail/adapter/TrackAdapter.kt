package ec.erickmedina.musicalisimo.ui.albums.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ec.erickmedina.domain.models.TrackModel
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.utils.toDurationString
import kotlinx.android.synthetic.main.item_track.view.*

class TrackAdapter(private val trackList: ArrayList<TrackModel>) : RecyclerView.Adapter<TrackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =
        trackList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTrack(trackList[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTrack(track: TrackModel) {
            itemView.track_rank.text = track.order.toString()
            itemView.track_name.text = track.name
            itemView.track_duration.text = track.duration.toDurationString()

        }
    }
}