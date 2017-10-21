package ru.vk_hack.museumguide.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.vk_hack.museumguide.data.models.ImageItem
import ru.vk_hack.museumguide.data.models.Painting
import ru.vk_hack.museumguide.databinding.DetailsItemBinding
import ru.vk_hack.museumguide.presentation.list.BindedRecyclerViewAdapter

/**
 * Created by bagrusss on 21.10.17
 */
class DetailsAdapter: BindedRecyclerViewAdapter<DetailsItemViewHolder, Painting>() {

    private var items = emptyList<Painting>()

    override fun swap(items: List<Painting>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DetailsItemViewHolder(DetailsItemBinding.inflate(inflater, parent, false))
    }

    override fun getData(position: Int) = items[position]


}