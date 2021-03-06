package com.example.rssparcer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.rssparcer.databinding.RvItemBinding
import com.example.rssparcer.tools.RssItem
import com.squareup.picasso.Picasso

class RVAdapter(private val data: ArrayList<RssItem>) : RecyclerView.Adapter<RVAdapter.RssViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RssViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return RssViewHolder(view)
    }

    override fun onBindViewHolder(holder: RssViewHolder, position: Int) {
        holder.bindCrypto(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class RssViewHolder(private var view: View) : RecyclerView.ViewHolder(view){

        private lateinit var rssItem: RssItem

        fun bindCrypto(rssItem: RssItem) {
            this.rssItem = rssItem

            val rvItemBinding = RvItemBinding.bind(view)

            rvItemBinding.newsTitle.text = rssItem.title

            rvItemBinding.newsText.text = rssItem.description

            if (rssItem.image != ""){
                Picasso.get().load(rssItem.image).into(rvItemBinding.newsImage)
            }


            rvItemBinding.showArticle.setOnClickListener{

                val bundle = Bundle()
                bundle.putString("link", rssItem.link)
                Navigation.findNavController(it).navigate(R.id.webFragment, bundle)
            }

        }
    }
}