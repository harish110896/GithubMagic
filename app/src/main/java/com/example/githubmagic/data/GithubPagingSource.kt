package com.example.githubmagic.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubmagic.data.model.PrInfoDto
import com.example.githubmagic.data.remote.remote.GithubApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubPagingSource  @Inject constructor(private val githubApi: GithubApi) : PagingSource<Int, List<PrInfoDto>>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, List<PrInfoDto>> {
        val page = params.key ?: 1
        return try {
            // Fake API Data
            var data = githubApi.getPrList("closed", page, 10)
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return state.anchorPosition
    }
}