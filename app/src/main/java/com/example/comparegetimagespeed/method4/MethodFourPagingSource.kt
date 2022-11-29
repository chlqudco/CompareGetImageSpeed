package com.example.comparegetimagespeed.method4

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.comparegetimagespeed.api.ApiService
import com.example.comparegetimagespeed.model.UrlModel

class MethodFourPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, String>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        return try {
            val position = params.key ?: 1

            val response = apiService.getImagesPaging(position)

            val imageList = response.body()?.images!!
            Log.e("gsgsgs","${params.loadSize}")
            Log.e("gsgsgs","$imageList")

            val nextKey =
                position + (params.loadSize / 20)


            LoadResult.Page(
                data = imageList,
                prevKey = if (position == 1) null else position -1,
                nextKey = nextKey
            )

        } catch (e: Exception){
            Log.e("gsgsgs",e.toString())
            LoadResult.Error(e)
        }
    }

    //뭐고 이건
    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}