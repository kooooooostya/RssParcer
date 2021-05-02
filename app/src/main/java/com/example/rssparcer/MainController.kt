package com.example.rssparcer

import com.example.rssparcer.tools.RssItem

class MainController(){

    private val model = MainModel()

    suspend fun getArrayList(): ArrayList<RssItem> {
        return model.getNewsFromWeb()
    }
}