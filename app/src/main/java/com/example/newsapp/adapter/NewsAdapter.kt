package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ArticleRowBinding
import com.example.newsapp.retrofit.model.Article
import com.squareup.picasso.Picasso

class NewsAdapter(val onItemClicked:OnClickListened): RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    interface OnClickListened{
        fun itemClicked(position: Int)
    }

    class MyViewHolder(val binding: ArticleRowBinding ): RecyclerView.ViewHolder(binding.root) {
    fun bindata(article: Article?) {
      binding.tvTitle.text = article?.title.toString()
      binding.tvSource.text = article?.source?.name.toString()
      binding.tvDescription.text = article?.description.toString()
        binding.tvPublishedAt.text = article?.publishedAt.toString()
        Picasso.get().load(article?.urlToImage).into(binding.ivArticleImage)
        if (article?.description==null){
            binding.tvDescription.text = ""
        }
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ArticleRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
val article = differ.currentList[position]
    holder.bindata(article)
        holder.binding.tvTitle.setOnClickListener {
            onItemClicked.itemClicked(position)
        }
    }

    override fun getItemCount(): Int {
return differ.currentList.size   }

    val diffutil = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return (oldItem.url==newItem.url)
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return (oldItem==newItem)
        }
    }
    val differ = AsyncListDiffer(this,diffutil)


}











