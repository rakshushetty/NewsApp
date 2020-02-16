package com.test.newsapp.feature

import com.test.newsapp.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NewsRepo {

    /**
     * Method to do Api call and get data from server
     */
    fun getData(): ApiResults<NewsData> {
        val result = StringBuilder()
        var urlConnection: HttpURLConnection? = null
        try {
            //  News Api Url
            val apiUrl = BuildConfig.URL
            val requestUrl = URL(apiUrl)
            urlConnection = requestUrl.openConnection() as HttpURLConnection
            urlConnection.connect()
            val inData: InputStream = BufferedInputStream(urlConnection.inputStream)
            val reader = BufferedReader(InputStreamReader(inData))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                result.append(line)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
        }
        val newsData = parseNewsJson(result)
        return if (newsData == null) {
            ApiResults.Error(NullPointerException("Did not receive any data!"))
        } else {
            ApiResults.Success(newsData)
        }
    }

    /**
     * Parsing data using jason object and jasonArray
     */
    private fun parseNewsJson(result: StringBuilder): NewsData? {
        if (result.isEmpty()) return null
        val newsObject = JSONObject(result.toString())
        val articleListJson: JSONArray? = newsObject.get("articles") as? JSONArray
        return articleListJson?.let {
            val newsData = NewsData()
            val articleList = ArrayList<Article>()
            for (index in 0 until articleListJson.length()) {
                val jsonObject = articleListJson[index] as JSONObject
                val article = Article(
                    jsonObject.get("author").toString(),
                    jsonObject.get("content").toString(),
                    jsonObject.get("description").toString(),
                    jsonObject.get("publishedAt").toString(),
                    jsonObject.get("source") as? Source,
                    jsonObject.get("title").toString(),
                    jsonObject.get("url").toString(),
                    jsonObject.get("urlToImage").toString()
                )
                articleList.add(article)
            }
            newsData.apply { articles = articleList }
        } ?: kotlin.run {
            null
        }
    }
}