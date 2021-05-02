package com.example.rssparcer

import com.example.rssparcer.tools.RssItem
import com.example.rssparcer.tools.RssParser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit


class MainModel {

    private val newsLink =
            "https://www.buzzfeed.com/world.xml"

    fun getNewsFromWeb(): ArrayList<RssItem> {

        val url = URL(newsLink)
        val client = OkHttpClient().newBuilder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .build()

        val request = Request.Builder()
                .url(url)
                .build()

        val arrayList = ArrayList<RssItem>()

        client.newCall(request).execute().use {
            if (it.isSuccessful) {
                val inputStream = it.body?.byteStream()
                val parser = RssParser()
                arrayList.addAll((parser.parse(inputStream!!)))

            } else throw IOException("Unexpected code $it")
        }

        return arrayList
    }
}