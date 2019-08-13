package ec.erickmedina.musicalisimo.ui.albums.list.top.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ec.erickmedina.domain.models.TopAlbumModel
import ec.erickmedina.domain.models.getImage
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.utils.ImageLoader
import kotlinx.android.synthetic.main.item_list.view.*

class TopAlbumsPageAdapter(private val callbacks: TopAlbumCallbacks) :
    PagedListAdapter<TopAlbumModel, TopAlbumsPageAdapter.ViewHolder>(DiffUtilCallback()) {

    interface TopAlbumCallbacks {
        fun onAlbumSelected(album:TopAlbumModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            album ->
            holder.bindItem(album)
            holder.itemView.setOnClickListener { callbacks.onAlbumSelected(album) }
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(model:TopAlbumModel) {
            ImageLoader.loadInImageView(model.getImage(), itemView.item_image)
            itemView.item_name.text = model.name
        }
    }

    class DiffUtilCallback: DiffUtil.ItemCallback<TopAlbumModel>() {
        override fun areItemsTheSame(oldItem: TopAlbumModel, newItem: TopAlbumModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: TopAlbumModel, newItem: TopAlbumModel): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.id == newItem.id
        }
    }
}