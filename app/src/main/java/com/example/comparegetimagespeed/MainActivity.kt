package com.example.comparegetimagespeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.comparegetimagespeed.URL.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java).also {
            it.getImages()
                .enqueue(object : Callback<UrlDto>{
                    override fun onResponse(call: Call<UrlDto>, response: Response<UrlDto>) {
                        if (response.isSuccessful){
                            Log.e("asdasd", response.body()?.images.toString())
                        }
                    }

                    override fun onFailure(call: Call<UrlDto>, t: Throwable) {

                    }

                })
        }
    }
}