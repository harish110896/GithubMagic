package com.example.githubmagic.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubmagic.data.model.PrInfoDto
import com.example.githubmagic.data.remote.remote.GithubApi
import com.example.githubmagic.data.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubPagingSource @Inject constructor(private val githubApi: GithubApi) :
    PagingSource<Int, PrInfoDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PrInfoDto> {
        val page = params.key ?: 1
        return try {
            // Fake API Data
            var data = githubApi.getPrList("closed", page, Constants.PAGE_SIZE)
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PrInfoDto>): Int? {
        return state.anchorPosition
    }
}