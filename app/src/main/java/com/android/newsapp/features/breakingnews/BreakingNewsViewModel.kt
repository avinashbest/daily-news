package com.android.newsapp.features.breakingnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.newsapp.data.NewsRepository
import com.android.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    private val refreshTriggerChannel = Channel<Unit>()
    private val refreshTrigger = refreshTriggerChannel.receiveAsFlow() // Hot Flow

    var pendingScrollToTopAfterRefresh = false

    val breakingNews = refreshTrigger.flatMapLatest {
        repository.getBreakingNews(
            onFetchSuccess = {
                pendingScrollToTopAfterRefresh = true
            },
            onFetchFailed = { t ->
                viewModelScope.launch { eventChannel.send(Event.ShowErrorMessage(t)) }
            }
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun onStart() {
        if (breakingNews.value !is Resource.Loading) {
            viewModelScope.launch {
                refreshTriggerChannel.send(Unit)
            }
        }
    }

    fun onManualRefresh() {
        if (breakingNews.value !is Resource.Loading) {
            viewModelScope.launch {
                refreshTriggerChannel.send(Unit)
            }
        }
    }

    sealed class Event {
        data class ShowErrorMessage(val error: Throwable) : Event()
    }
}