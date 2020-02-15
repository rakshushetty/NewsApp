package com.test.newsapp.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.test.newsapp.Article
import com.test.newsapp.Source

const val DB_VERSION: Int = 1
const val TABLE_NAME: String = "news_table"
const val DATABASE_NAME: String = "database_name"

const val KEY_AUTHOR: String = "author"
const val KEY_CONTENT: String = "content"
const val KEY_DESCRIPTION: String = "description"
const val KEY_PUBLISHED_AT: String = "published_at"
const val KEY_SOURCE: String = "source"
const val KEY_TITLE: String = "title"
const val KEY_URL: String = "url"
const val KEY_URL_TO_IMAGE: String = "url_to_image"

class SQLiteManager private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION) {

    companion object {
        private var instance: SQLiteManager? = null

        fun newInstance(context: Context): SQLiteManager? {
            return synchronized(this) {
                if (instance == null) {
                    instance = SQLiteManager(context)
                }
                instance
            }
        }

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME ( " +
                    "$KEY_TITLE TEXT PRIMARY KEY, " +   // 1
                    "$KEY_AUTHOR TEXT, " +              // 2
                    "$KEY_CONTENT TEXT, " +             // 3
                    "$KEY_DESCRIPTION TEXT, " +         // 4
                    "$KEY_PUBLISHED_AT TEXT, " +        // 5
                    "$KEY_SOURCE TEXT, " +              // 6
                    "$KEY_URL TEXT, " +                 // 7
                    "$KEY_URL_TO_IMAGE TEXT )"          // 8
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        this.onCreate(db)
    }

    fun addToDb(article: Article) {
        val contentValues = ContentValues()
        contentValues.apply {
            put(KEY_AUTHOR, article.author)
            put(KEY_CONTENT, article.content)
            put(KEY_DESCRIPTION, article.description)
            put(KEY_PUBLISHED_AT, article.publishedAt)
            put(KEY_SOURCE, article.source?.name ?: "")
            put(KEY_TITLE, article.title)
            put(KEY_URL, article.url)
            put(KEY_URL_TO_IMAGE, article.urlToImage)
        }
        val db = writableDatabase
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun deleteItem(article: Article) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "id = ?", arrayOf(article.title))
        db.close()
    }

    fun getAllData(): ArrayList<Article> {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = writableDatabase
        val cursor = db.rawQuery(query, null)
        val articleList = ArrayList<Article>()
        if (cursor.moveToFirst()) {
            do {
                val article = Article(
                    cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    Source(name = cursor.getString(6)),
                    cursor.getString(1),
                    cursor.getString(7),
                    cursor.getString(8)
                )
                articleList.add(article)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return articleList
    }
}