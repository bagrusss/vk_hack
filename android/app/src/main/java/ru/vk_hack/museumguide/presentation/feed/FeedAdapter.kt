package ru.vk_hack.museumguide.presentation.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.vk_hack.museumguide.data.models.FeedEvent
import ru.vk_hack.museumguide.data.models.ImageItem
import ru.vk_hack.museumguide.databinding.EventItemBinding
import ru.vk_hack.museumguide.presentation.list.BindedRecyclerViewAdapter

/**
 * Created by bagrusss on 21.10.17
 */
class FeedAdapter : BindedRecyclerViewAdapter<FeedViewHolder, FeedEvent>() {

    private var items = emptyList<FeedEvent>()

    override fun swap(items: List<FeedEvent>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EventItemBinding.inflate(inflater, parent, false)
        return FeedViewHolder(binding)
    }

    override fun getData(position: Int): FeedEvent {
        return items[position]
    }

}