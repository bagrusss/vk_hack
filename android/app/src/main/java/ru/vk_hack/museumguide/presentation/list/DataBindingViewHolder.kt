package ru.vk_hack.museumguide.presentation.list

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * Created by bagrusss on 21.10.17
 */
abstract class DataBindingViewHolder<out B: ViewDataBinding, in D>(protected val binding: B): RecyclerView.ViewHolder(binding.root) {
    abstract fun onBind(data: D)
}