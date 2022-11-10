package com.example.comparegetimagespeed.method4

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.comparegetimagespeed.api.ApiService
import com.example.comparegetimagespeed.model.UrlModel

class MethodFourPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, UrlModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UrlModel> {
        return try {
            val position = params.key ?: 1

            val response = apiService.getImagesWithCoroutine()
            val imageList = response.body()?.images ?: listOf()

            LoadResult.Page(
                data = imageList,
                prevKey = if (position == 1) null else position -1,
                nextKey = null
            )

        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    //뭐고 이건
    override fun getRefreshKey(state: PagingState<Int, UrlModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}