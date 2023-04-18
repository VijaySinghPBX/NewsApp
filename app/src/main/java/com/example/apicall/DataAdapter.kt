package com.example.apicall

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DataAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {
    private val dataSet:ArrayList<NewsDataModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_items,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(dataSet[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews = dataSet[position]
        holder.newsTitle.text = currentNews.newsTitle
        holder.newsAuthor.text = currentNews.newAuthor
        holder.imageLoading.visibility = View.GONE
        Glide.with(holder.itemView.context).load(currentNews.newsImage).into(holder.newsImage)
        holder.newsDescription.text = currentNews.newsDescription
    }
    fun getNews(news:List<NewsDataModel>){
        dataSet.clear()
        dataSet.addAll(news)
        notifyDataSetChanged()
    }
}
class NewsViewHolder(item: View): RecyclerView.ViewHolder(item){
    val newsTitle:TextView = item.findViewById(R.id.news_title)
    val newsAuthor:TextView = item.findViewById(R.id.news_author)
    val newsImage:ImageView = item.findViewById(R.id.news_image)
    val imageLoading:ProgressBar = item.findViewById(R.id.progress_bar)
    val newsDescription:TextView = item.findViewById(R.id.news_description)
}
interface NewsItemClicked {
    fun onItemClicked(item: NewsDataModel)
}