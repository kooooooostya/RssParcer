package com.example.rssparcer

import com.example.rssparcer.tools.RssItem

interface RssItemsProvider {
    fun getArrayList(): ArrayList<RssItem>
}