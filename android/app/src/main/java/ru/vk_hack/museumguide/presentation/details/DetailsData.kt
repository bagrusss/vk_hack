package ru.vk_hack.museumguide.presentation.details

import android.databinding.ObservableField

/**
 * Created by bagrusss on 21.10.17
 */
class DetailsData: DetailsItemData() {
    val fullName = ObservableField<String>()
    val location = ObservableField<String>()
    val description = ObservableField<String>()
}