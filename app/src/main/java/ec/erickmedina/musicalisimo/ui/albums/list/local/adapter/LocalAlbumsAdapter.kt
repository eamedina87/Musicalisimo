package ec.erickmedina.musicalisimo.ui.albums.list.local.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.models.getImage
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.utils.ImageLoader
import kotlinx.android.synthetic.main.item_list.view.*

class LocalAlbumsAdapter(private val callback: LocalAlbumsCallbacks) :
    ListAdapter<AlbumModel, LocalAlbumsAdapter.ViewHolder>(AlbumDiff()) {

    interface LocalAlbumsCallbacks {
        fun onAlbumClicked(album:AlbumModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
        holder.itemView.setOnClickListener { callback.onAlbumClicked(getItem(position)) }
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {
        fun bindItem(album:AlbumModel) {
            ImageLoader.loadInImageView(album.getImage(), itemView.item_image)
            itemView.item_name.text = "${album.name}\n${album.artist}"
        }
    }

    class AlbumDiff : DiffUtil.ItemCallback<AlbumModel>() {
        override fun areItemsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean =
            oldItem.name == newItem.name &&
                    oldItem.artist == newItem.artist &&
                    oldItem.tracks.size == newItem.tracks.size

    }
}