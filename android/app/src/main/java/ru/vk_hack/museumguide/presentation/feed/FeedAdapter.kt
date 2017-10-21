package ru.vk_hack.museumguide.presentation.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.vk_hack.museumguide.data.models.FeedEvent
import ru.vk_hack.museumguide.databinding.EventItemBinding
import ru.vk_hack.museumguide.presentation.list.BindedRecyclerViewAdapter

/**
 * Created by bagrusss on 21.10.17
 */
class FeedAdapter : BindedRecyclerViewAdapter<FeedViewHolder, FeedEvent>() {

    private val stubEvents = listOf(FeedEvent(picture = "https://www.tretyakovgallery.ru/upload/iblock/951/95170e80eebc1f942139bf69536af350.jpg",
                                          title = "Город и люди. Москва в графике ХХ века",
                                          location = "Новая Третьяковка",
                                          description = "А.А. Дейнека. Площадь Свердлова. 1941–1946. Серия «Москва военная». Бумага, гуашь, темпера, уголь. 67 х 81,7. Третьяковская галерея",
                                          dates = "1 октября 2017 - 28 января 2018"),
                                    FeedEvent(picture = "https://www.tretyakovgallery.ru/upload/iblock/951/95170e80eebc1f942139bf69536af350.jpg",
                                          title = "Город и люди. Москва в графике ХХ века",
                                          location = "Новая Третьяковка",
                                          description = "А.А. Дейнека. Площадь Свердлова. 1941–1946. Серия «Москва военная». Бумага, гуашь, темпера, уголь. 67 х 81,7. Третьяковская галерея",
                                          dates = "1 октября 2017 - 28 января 2018"),
                                    FeedEvent(picture = "https://www.tretyakovgallery.ru/upload/iblock/951/95170e80eebc1f942139bf69536af350.jpg",
                                          title = "Город и люди. Москва в графике ХХ века",
                                          location = "Новая Третьяковка",
                                          description = "А.А. Дейнека. Площадь Свердлова. 1941–1946. Серия «Москва военная». Бумага, гуашь, темпера, уголь. 67 х 81,7. Третьяковская галерея",
                                          dates = "1 октября 2017 - 28 января 2018"))


    override fun getItemCount() = stubEvents.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EventItemBinding.inflate(inflater, parent, false)
        return FeedViewHolder(binding)
    }

    override fun getData(position: Int): FeedEvent {
        return stubEvents[position]
    }

}