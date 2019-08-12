package ec.erickmedina.musicalisimo.ui.albums.list.local.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.models.getImage
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.utils.ImageLoader
import kotlinx.android.synthetic.main.item_list.view.*

class LocalAlbumsAdapter(private val albums: ArrayList<AlbumModel>) : RecyclerView.Adapter<LocalAlbumsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =
        albums.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(albums[position])
    }

    fun updateList(albumList: ArrayList<AlbumModel>) {

    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {
        fun bindItem(album:AlbumModel) {
            ImageLoader.loadInImageView(album.getImage(), itemView.item_image)
            itemView.item_name.text = "${album.name}\n${album.artist}"
        }
    }
}