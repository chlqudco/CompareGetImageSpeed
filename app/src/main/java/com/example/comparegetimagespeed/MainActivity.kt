package com.example.comparegetimagespeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import com.example.comparegetimagespeed.URL.BASE_URL
import com.example.comparegetimagespeed.adapter.MainAdapter
import com.example.comparegetimagespeed.api.ApiService
import com.example.comparegetimagespeed.databinding.ActivityMainBinding
import com.example.comparegetimagespeed.method1.retrofit.MethodOneRetrofitActivity
import com.example.comparegetimagespeed.method1.volley.MethodOneVolleyActivity
import com.example.comparegetimagespeed.method2.retrofit.MethodTwoRetrofitActivity
import com.example.comparegetimagespeed.method2.volley.MethodTwoVolleyActivity
import com.example.comparegetimagespeed.response.UrlDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //다크모드 금지
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.Method1RetrofitButton.setOnClickListener {
            val intent = Intent(this, MethodOneRetrofitActivity::class.java)
            startActivity(intent)
        }

        binding.Method1VolleyButton.setOnClickListener {
            val intent = Intent(this, MethodOneVolleyActivity::class.java)
            startActivity(intent)
        }

        binding.Method2RetrofitButton.setOnClickListener {
            val intent = Intent(this, MethodTwoRetrofitActivity::class.java)
            startActivity(intent)
        }

        binding.Method2VolleyButton.setOnClickListener {
            val intent = Intent(this, MethodTwoVolleyActivity::class.java)
            startActivity(intent)
        }
    }
}