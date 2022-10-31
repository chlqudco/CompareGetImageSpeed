package com.example.comparegetimagespeed.method2.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import com.example.comparegetimagespeed.R
import com.example.comparegetimagespeed.URL
import com.example.comparegetimagespeed.adapter.MainAdapter
import com.example.comparegetimagespeed.api.ApiService
import com.example.comparegetimagespeed.databinding.ActivityMethodTwoRetrofitBinding
import com.example.comparegetimagespeed.response.UrlDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MethodTwoRetrofitActivity : AppCompatActivity() {

    val binding by lazy { ActivityMethodTwoRetrofitBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //다크모드 금지
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        initViews()
    }

    private fun initViews() {
        val adapter = MainAdapter()
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.layoutManager = GridLayoutManager(this, 2)

        binding.getImageButton.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl(URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(ApiService::class.java).also {
                it.getImages()
                    .enqueue(object : Callback<UrlDto> {
                        override fun onResponse(call: Call<UrlDto>, response: Response<UrlDto>) {
                            if (response.isSuccessful){
                                Log.e("asdasd", response.body()?.images.toString())
                                adapter.urlList = response.body()?.images ?: listOf()
                                adapter.notifyDataSetChanged()
                            }
                        }

                        override fun onFailure(call: Call<UrlDto>, t: Throwable) {

                        }

                    })
            }
        }
    }
}