package ru.vk_hack.museumguide.presentation.details

import ru.vk_hack.museumguide.data.models.ImageItem
import ru.vk_hack.museumguide.databinding.DetailsItemBinding
import ru.vk_hack.museumguide.presentation.list.DataBindingViewHolder
import ru.vk_hack.museumguide.utils.ImageUtils

/**
 * Created by bagrusss on 21.10.17
 */
class DetailsItemViewHolder(binding: DetailsItemBinding) : DataBindingViewHolder<DetailsItemBinding, ImageItem>(binding) {

    private val bindingData = DetailsItemData()

    init {
        binding.data = bindingData
    }

    override fun onBind(data: ImageItem) {
        bindingData.author.set(data.title)
        ImageUtils.loadImage(binding.imageView, data.pictureUrl)
    }

}