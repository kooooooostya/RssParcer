package com.example.rssparcer.tools

class RssItem {
    var title = ""

    var link = ""
    var pubDate : Long = 0
    var description = ""
    var category = ""
    var image = ""

    override fun toString(): String {
        return "RssItem(title='$title', link='$link', pubDate='$pubDate', description='$description', category='$category', image='$image')"
    }

}