package com.example.apicall

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var mAdapter: DataAdapter
    private val newArray:ArrayList<NewsDataModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar = findViewById(R.id.progressBar)
        mAdapter = DataAdapter(this)
        loadNews()
        recyclerView.adapter = mAdapter

    }
    private fun loadNews() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                for (i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = NewsDataModel(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("urlToImage"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("description"),
                    )
                    Log.i("Title", news.newsDescription)
                    newArray.add(news)
                    progressBar.visibility = View.GONE
                    mAdapter.getNews(newArray)
                }
            },
            { Toast.makeText(this,"fail",Toast.LENGTH_LONG).show()})
        queue.add(jsonObjectRequest)

    }

    override fun onItemClicked(item: NewsDataModel) {
       try {
           val builder = CustomTabsIntent.Builder()
           val customTabsIntent = builder.build()
           customTabsIntent.launchUrl(this, Uri.parse(item.newsUrl))
       }
       catch (e:java.lang.Exception){
           Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
       }
    }
}