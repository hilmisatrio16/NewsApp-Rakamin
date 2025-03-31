package com.rakamin.newsapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rakamin.newsapp.databinding.ItemSearchBinding
import com.rakamin.newsapp.model.DataArticle

class SearchListAdapter(var clickItemNews: (DataArticle) -> Unit) :
    RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    private var diffCallbackUser = object : DiffUtil.ItemCallback<DataArticle>() {
        override fun areItemsTheSame(
            oldItem: DataArticle,
            newItem: DataArticle
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: DataArticle,
            newItem: DataArticle
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private var differ = AsyncListDiffer(this, diffCallbackUser)

    fun submitData(valueList: ArrayList<DataArticle>) {
        differ.submitList(valueList)
    }

    class ViewHolder(private var binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(data: DataArticle) {
            with(binding) {
                tvTitle.text = data.title
                tvChannel.text = data.sourceName
                Glide.with(itemView)
                    .load(data.urlToImage)
                    .centerCrop()
                    .into(ivNews)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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