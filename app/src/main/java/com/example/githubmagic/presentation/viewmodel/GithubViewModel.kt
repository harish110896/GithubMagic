package com.example.githubmagic.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubmagic.data.GithubRepository
import com.example.githubmagic.data.model.PrInfoDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    val posts: Flow<PagingData<PrInfoDto>> = repository.getPagedPosts()
        .cachedIn(viewModelScope) // Caches data in ViewModel
}