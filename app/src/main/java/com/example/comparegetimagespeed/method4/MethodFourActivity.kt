package com.example.comparegetimagespeed.method4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.GridLayoutManager
import com.example.comparegetimagespeed.BuildConfig
import com.example.comparegetimagespeed.adapter.PagingAdapter
import com.example.comparegetimagespeed.api.ApiService
import com.example.comparegetimagespeed.databinding.ActivityMethodFourBinding
import com.example.comparegetimagespeed.model.UrlModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MethodFourActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMethodFourBinding.inflate(layoutInflater) }

    private val _urlPagingResult = MutableStateFlow<PagingData<String>>(PagingData.empty())
    val urlPagingResult: StateFlow<PagingData<String>> = _urlPagingResult.asStateFlow()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {

        val adapter = PagingAdapter()
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.layoutManager = GridLayoutManager(this, 2)

        collectLatestStateFlow(urlPagingResult){
            adapter.submitData(it)
        }

        binding.getImageButton.setOnClickListener {
            getImages()
        }

    }

    private fun getImages() {
        CoroutineScope(Dispatchers.Main).launch {
            getImagesPaging()
                .cachedIn(lifecycleScope)
                .collect{
                    _urlPagingResult.value = it
                }
        }
    }

    private fun getImagesPaging(): Flow<PagingData<String>> {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        val pagingSourceFactory = { MethodFourPagingSource(service) }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                maxSize = 60
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}