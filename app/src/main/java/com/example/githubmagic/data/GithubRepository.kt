package com.example.githubmagic.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubmagic.data.model.PrInfoDto
import com.example.githubmagic.data.remote.remote.GithubApi
import com.example.githubmagic.data.util.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val apiService: GithubApi
) {

    fun getPagedPosts(): Flow<PagingData<PrInfoDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(apiService) }
        ).flow
    }
}