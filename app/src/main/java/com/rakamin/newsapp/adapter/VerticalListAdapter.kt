package com.rakamin.newsapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rakamin.newsapp.data.remote.response.Article
import com.rakamin.newsapp.databinding.VerticalItemBinding
import com.rakamin.newsapp.utils.convertTime

class VerticalListAdapter(var clickItemNews: (Article) -> Unit) :
    RecyclerView.Adapter<VerticalListAdapter.ViewHolder>() {

    private var diffCallbackUser = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private var differ = AsyncListDiffer(this, diffCallbackUser)

    fun submitData(valueList: ArrayList<Article>) {
        differ.submitList(valueList)
    }

    class ViewHolder(private var binding: VerticalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(data: Article) {
            with(binding) {
                tvTitle.text = data.title
                tvWriter.text = data.author
                tvChannel.text = data.source.name
                tvDateRelease.text = convertTime(data.publishedAt)
                Glide.with(itemView)
                    .load(data.urlToImage)
                    .centerCrop()
                    .into(ivNews)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = VerticalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]

        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener {
                clickItemNews.invoke(data)
            }
        }
    }
}