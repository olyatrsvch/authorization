package com.example.googleit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.googleit.R
import com.example.googleit.databinding.WebsiteItemBinding

class WebsitesAdapter : RecyclerView.Adapter<WebsitesAdapter.WebsiteHolder>() {

    val websiteList = ArrayList<WebsiteItem>()

    class WebsiteHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = WebsiteItemBinding.bind(item)
        fun bind(website: WebsiteItem) = with(binding) {
            imageView.setImageResource(website.imageId)
            tvTitle.text = website.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.website_item, parent, false)
        return WebsiteHolder(view)
    }

    override fun onBindViewHolder(holder: WebsiteHolder, position: Int) {
        holder.bind(websiteList[position])
    }

    override fun getItemCount(): Int {
        return websiteList.size
    }

    fun addWebsites(list: List<WebsiteItem>) {
        websiteList.addAll(list)
        notifyDataSetChanged()
    }

}