package com.rakamin.newsapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rakamin.newsapp.databinding.HorizontalItemBinding
import com.rakamin.newsapp.model.DataArticle
import com.rakamin.newsapp.utils.convertTime

class HorizontalListAdapter(var clickItemNews: (DataArticle) -> Unit) :
    RecyclerView.Adapter<HorizontalListAdapter.ViewHolder>() {

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

    class ViewHolder(private var binding: HorizontalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(data: DataArticle) {
            with(binding) {
                tvTitle.text = data.title
                tvAuthor.text = data.author
                tvDateRelease.text = convertTime(data.publishedAt)
                Glide.with(itemView)
                    .load(data.urlToImage)
                    .centerCrop()
                    .into(ivHeadline)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = HorizontalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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