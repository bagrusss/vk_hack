package ru.vk_hack.museumguide.presentation.list

import android.support.v7.widget.RecyclerView

/**
 * Created by bagrusss on 21.10.17
 */
abstract class BindedRecyclerViewAdapter<VH: DataBindingViewHolder<*, D>, D>: RecyclerView.Adapter<VH>() {

    abstract fun getData(position: Int): D

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = getData(position)
        holder.onBind(data)
    }

    abstract fun swap(items: List<D>)

}