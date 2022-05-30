package com.android.newsapp.features.breakingnews

import androidx.lifecycle.ViewModel
import com.android.newsapp.data.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

}