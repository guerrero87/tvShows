package com.example.kotlintvshows.interfaces

interface TvShowContract {

    interface View {
        fun setSubscribeBtnUnClicked()
        fun setSubscribeBtnClicked()
        fun isSubscribeBtnEnabled(boolean: Boolean)
        fun showToast(message: String)
    }

    interface Presenter {

    }
}