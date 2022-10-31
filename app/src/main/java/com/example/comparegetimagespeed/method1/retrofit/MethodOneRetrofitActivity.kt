package com.example.comparegetimagespeed.method1.retrofit

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.comparegetimagespeed.R
import com.example.comparegetimagespeed.URL
import com.example.comparegetimagespeed.api.ApiService
import com.example.comparegetimagespeed.databinding.ActivityMethodOneRetrofitBinding
import com.example.comparegetimagespeed.response.UrlDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MethodOneRetrofitActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMethodOneRetrofitBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.oneRetrofitButton.setOnClickListener {
            //초기화
            binding.oneRetrofitLinearLayout.removeAllViews()

            //불러오기
            val retrofit = Retrofit.Builder()
                .baseUrl(URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(ApiService::class.java).also {
                it.getImages()
                    .enqueue(object : Callback<UrlDto> {
                        @SuppressLint("InflateParams")
                        override fun onResponse(call: Call<UrlDto>, response: Response<UrlDto>) {
                            if (response.isSuccessful) {
                                Log.e("asdasd", response.body()?.images.toString())
                                val urlList = response.body()?.images ?: listOf()
                                for (i in 0..7000) {
                                    val new_list =
                                        LayoutInflater.from(this@MethodOneRetrofitActivity)
                                            .inflate(R.layout.item_image, null, false)
                                    Glide.with(this@MethodOneRetrofitActivity)
                                        .load(urlList[i].imageUrl)
                                        .into(new_list.findViewById(R.id.item_image))
                                    new_list.findViewById<TextView>(R.id.item_id).text =
                                        "${urlList[i].id}"
                                    binding.oneRetrofitLinearLayout.addView(new_list)
                                }
                            }
                        }

                        override fun onFailure(call: Call<UrlDto>, t: Throwable) {

                        }

                    })


            }

        }
    }
}