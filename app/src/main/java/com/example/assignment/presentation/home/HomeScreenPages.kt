package com.example.assignment.presentation.home

import androidx.annotation.StringRes
import com.example.assignment.R.string as Strings

enum class HomeScreenPages(@StringRes val titleResId: Int) {
    Charcha(Strings.label_charcha),
    Bazaar(Strings.label_bazaar),
    Profile(Strings.label_profile)
}