package ru.vk_hack.museumguide.presentation.event

import android.databinding.ObservableField

/**
 * Created by bagrusss on 21.10.17
 */
class EventData {
    val title = ObservableField<String>()
    val dates = ObservableField<String>()
    val location = ObservableField<String>()
    val description = ObservableField<String>()
}