package com.example.rssparcer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparcer.databinding.RvItemBinding
import com.example.rssparcer.tools.RssItem
import com.squareup.picasso.Picasso

class RVAdapter(private val rssItemsProvider: RssItemsProvider) : RecyclerView.Adapter<RVAdapter.RssViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RssViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, true)
        return RssViewHolder(view)
    }

    override fun onBindViewHolder(holder: RssViewHolder, position: Int) {
        holder.bindCrypto(rssItemsProvider.getArrayList()[position])
    }

    override fun getItemCount(): Int {
        return rssItemsProvider.getArrayList().size
    }


    inner class RssViewHolder(private var view: View) : RecyclerView.ViewHolder(view){

        private lateinit var rssItem: RssItem

        fun bindCrypto(rssItem: RssItem) {
            this.rssItem = rssItem

            val rvItemBinding = RvItemBinding.bind(view)

            rvItemBinding.newsTitle.text = rssItem.title

            rvItemBinding.newsText.text = rssItem.description

            Picasso.get().load(rssItem.image).into(rvItemBinding.newsImage)

            rvItemBinding.showArticle.setOnClickListener{
                Toast.makeText(it.context, "Not yet implemented", Toast.LENGTH_SHORT).show()
            }

        }
    }
}