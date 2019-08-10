package ec.erickmedina.musicalisimo.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.getImage
import ec.erickmedina.musicalisimo.R
import ec.erickmedina.musicalisimo.utils.ImageLoader
import kotlinx.android.synthetic.main.item_artist.view.*

class SearchAdapter(var items: ArrayList<ArtistModel>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_artist, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    fun updateList(list: ArrayList<ArtistModel>) {
        //diff utils
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(artist:ArtistModel) {
            ImageLoader.loadInImageView(artist.getImage(), itemView.item_artist_image)
            itemView.item_artist_name.text = artist.name
        }
    }
}