package com.example.rssparcer.tools

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RssParser {
    private val rssItems = ArrayList<RssItem>()
    private var rssItem : RssItem?= null
    private var text: String? = null
    private val datePattern = "EEE, d MMM yyyy H:mm:ss X"
    private val datePatternShort = "yyyy-MM-dd HH:mm:ss"


    fun parse(inputStream: InputStream):List<RssItem> {
        val simpleDateParser = SimpleDateFormat(datePattern, Locale.ENGLISH)
        val simpleDateParserShort = SimpleDateFormat(datePatternShort, Locale.ENGLISH)

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)
            var eventType = parser.eventType
            var foundItem = false

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagname = parser.name

                when (eventType) {

                    XmlPullParser.START_TAG -> if (tagname.equals("item", ignoreCase = true)) {
                        foundItem = true
                        rssItem = RssItem()
                    }

                    XmlPullParser.TEXT -> text = parser.text

                    XmlPullParser.END_TAG -> if (tagname.equals("item", ignoreCase = true)) {
                        // add rssItem object to list
                        rssItem?.let { rssItems.add(it) }
                        foundItem = false
                    } else if (foundItem && tagname.equals("title", ignoreCase = true)) {
                        rssItem!!.title = text.toString()
                    } else if (foundItem && tagname.equals("link", ignoreCase = true)) {
                        rssItem!!.link = text.toString()
                    } else if (foundItem && tagname.equals("pubDate", ignoreCase = true)) {

                        try {
                            val date = simpleDateParser.parse(text.toString())
                            if (date != null) {
                                rssItem!!.pubDate = date.time
                            }
                        } catch (e: ParseException){
                            //if it is not possible to parse the date according to the standard pattern, try this
                            try {
                                val date = simpleDateParserShort.parse(text.toString())
                                if (date != null) {
                                    rssItem!!.pubDate = date.time
                                }
                            }catch (e: ParseException){
                                //if we cant parse date set time equals 0
                                rssItem!!.pubDate = 0
                            }
                        }

                    } else if (foundItem && tagname.equals("category", ignoreCase = true)) {
                        rssItem!!.category = text.toString()
                    } else if (foundItem && tagname.equals("description", ignoreCase = true)) {
                        rssItem!!.description = trimStringWithDescription(text.toString()) ?: ""
                    } else if (foundItem && tagname.equals("enclosure", ignoreCase = true)) {

                        val type = parser.getAttributeValue(null, "type")
                        if (type != null && type.contains("image")) {
                            // If there are multiple elements, we take only the first
                            rssItem!!.image = parser.getAttributeValue(null, "url")
                        }
                    } else if (foundItem && tagname.equals("content", ignoreCase = true)) {
                        if (parser.prefix.equals("media", ignoreCase = true)) {
                            rssItem!!.image = parser.getAttributeValue(null, "url")
                        }

                    } else if (foundItem && tagname.equals("image", ignoreCase = true)) {
                        if (parser.prefix.equals("itunes", ignoreCase = true)) {
                            rssItem!!.image = parser.getAttributeValue(null, "href")
                        }

                    } else if (foundItem && tagname.equals("thumbnail", ignoreCase = true)) {
                        if (parser.prefix.equals("media", ignoreCase = true)) {
                            rssItem!!.image = parser.getAttributeValue(null, "url")
                        }
                    }
                }
                eventType = parser.next()
            }

        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return rssItems
    }


    private fun trimStringWithDescription(input: String) : String? {
        val patternDescription =
            """\s?([A-zА-я0-9\.,\?;:!\-/№&${'$'}%’"”“'()]*\s)+([A-zА-я\-0-9&${'$'}%'"”“’()]*[\.!\?])""".toRegex()
        val matcherDescription = patternDescription.find(input)
        return matcherDescription?.value?.trim()
    }

}