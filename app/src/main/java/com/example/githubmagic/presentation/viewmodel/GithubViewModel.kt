package com.example.githubmagic.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubmagic.data.model.PrInfoDto
import com.example.githubmagic.data.repository.GithubRepository
import com.example.githubmagic.data.repository.GithubViewRepository
import com.example.githubmagic.data.repository.impl.GithubRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<PagingData<PrInfoDto>>(PagingData.empty())
    val posts: StateFlow<PagingData<PrInfoDto>> = _posts.asStateFlow()

    init {
        fetchPages("closed", 1, 10) // Default call on initialization
    }

    private fun fetchPages(state: String, page: Int, pageSize: Int) {
        viewModelScope.launch {
            repository.getPagedPosts().cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _posts.value = pagingData
                }
        }
    }
}