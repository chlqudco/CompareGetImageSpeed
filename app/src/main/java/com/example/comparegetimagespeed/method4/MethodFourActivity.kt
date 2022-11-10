package com.example.comparegetimagespeed.method4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comparegetimagespeed.R
import com.example.comparegetimagespeed.URL
import com.example.comparegetimagespeed.adapter.MainAdapter
import com.example.comparegetimagespeed.adapter.PagingAdapter
import com.example.comparegetimagespeed.api.ApiService
import com.example.comparegetimagespeed.databinding.ActivityMethodFourBinding
import com.example.comparegetimagespeed.model.UrlModel
import com.example.comparegetimagespeed.response.UrlDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MethodFourActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMethodFourBinding.inflate(layoutInflater) }

    private val _urlPagingResult = MutableStateFlow<PagingData<UrlModel>>(PagingData.empty())
    val urlPagingResult: StateFlow<PagingData<UrlModel>> = _urlPagingResult.asStateFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {

        val adapter = PagingAdapter()
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.layoutManager = GridLayoutManager(this, 1)

        collectLatestStateFlow(urlPagingResult){
            adapter.submitData(it)
        }

        binding.getImageButton.setOnClickListener {
            getImages()
        }

    }

    private fun getImages() {
        CoroutineScope(Dispatchers.IO).launch {
            getImagesPaging()
                .cachedIn(this)
                .collect{
                    _urlPagingResult.value = it
                }
        }
    }


    fun getImagesPaging(): Flow<PagingData<UrlModel>> {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        val pagingSourceFactory = { MethodFourPagingSource(service) }

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                maxSize = 30
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}