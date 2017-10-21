package ru.vk_hack.museumguide.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.vk_hack.museumguide.data.models.ImageItem
import ru.vk_hack.museumguide.databinding.DetailsItemBinding
import ru.vk_hack.museumguide.presentation.list.BindedRecyclerViewAdapter

/**
 * Created by bagrusss on 21.10.17
 */
class DetailsAdapter: BindedRecyclerViewAdapter<DetailsItemViewHolder, ImageItem>() {

    private val listStub = arrayListOf(ImageItem(title = "Морской пейзаж",
                                         pictureUrl = "https://www.tretyakovgallery.ru/upload/iblock/951/95170e80eebc1f942139bf69536af350.jpg",
                                         url = "некоторый текст"),
                                       ImageItem(title = "Морской пейзаж",
                                                 pictureUrl = "https://www.tretyakovgallery.ru/upload/iblock/951/95170e80eebc1f942139bf69536af350.jpg",
                                                 url = "некоторый текст"),
                                       ImageItem(title = "Морской пейзаж",
                                                 pictureUrl = "https://www.tretyakovgallery.ru/upload/iblock/951/95170e80eebc1f942139bf69536af350.jpg",
                                                 url = "некоторый текст"))

    override fun getItemCount() = listStub.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DetailsItemViewHolder(DetailsItemBinding.inflate(inflater, parent, false))
    }

    override fun getData(position: Int) = listStub[position]


}