package ru.vk_hack.museumguide.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.vk_hack.museumguide.databinding.EventItemBinding
import ru.vk_hack.museumguide.presentation.list.BindedRecyclerViewAdapter

/**
 * Created by bagrusss on 21.10.17
 */
class EventsAdapter: BindedRecyclerViewAdapter<EventViewHolder, Int>() {

    override fun getItemCount() = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EventItemBinding.inflate(inflater, parent, false)
        return EventViewHolder(binding)
    }

    override fun getData(position: Int): Int {
        return 0 //stub
    }

}