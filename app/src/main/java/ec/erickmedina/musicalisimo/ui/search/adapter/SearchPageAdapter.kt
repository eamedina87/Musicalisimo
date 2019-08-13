package ec.erickmedina.musicalisimo.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.getImage
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.utils.ImageLoader
import kotlinx.android.synthetic.main.item_list.view.*

class SearchPageAdapter(private val callbacks: SearchCallbacks) :
    PagedListAdapter<ArtistModel, SearchPageAdapter.ViewHolder>(DiffUtilCallback()) {

    interface SearchCallbacks {
        fun onArtistSelected(artist:ArtistModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            artist ->
            holder.bindItem(artist)
            holder.itemView.setOnClickListener { callbacks.onArtistSelected(artist) }
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(artist:ArtistModel) {
            ImageLoader.loadInImageView(artist.getImage(), itemView.item_image)
            itemView.item_name.text = artist.name
        }
    }

    class DiffUtilCallback: DiffUtil.ItemCallback<ArtistModel>() {
        override fun areItemsTheSame(oldItem: ArtistModel, newItem: ArtistModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ArtistModel, newItem: ArtistModel): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.id == newItem.id
        }
    }
}