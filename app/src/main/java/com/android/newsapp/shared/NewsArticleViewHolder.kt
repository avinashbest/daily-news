package com.android.newsapp.shared

import androidx.recyclerview.widget.RecyclerView
import com.android.newsapp.R
import com.android.newsapp.data.NewsArticle
import com.android.newsapp.databinding.ItemNewsArticleBinding
import com.bumptech.glide.Glide

class NewsArticleViewHolder(
    private val binding: ItemNewsArticleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: NewsArticle) {
        binding.apply {
            Glide.with(itemView)
                .load(article.thumbnailUrl)
                .error(R.drawable.image_placeholder)
                .into(imageView)

            textViewTitle.text = article.title ?: ""

            imageViewBookmark.setImageResource(
                when {
                    article.isBookmarked -> R.drawable.ic_bookmark_selected
                    else -> R.drawable.ic_bookmark_unselected
                }
            )
        }
    }


}