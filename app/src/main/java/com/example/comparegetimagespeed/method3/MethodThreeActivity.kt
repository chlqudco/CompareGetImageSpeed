package com.example.comparegetimagespeed.method3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.comparegetimagespeed.R
import com.example.comparegetimagespeed.URL
import com.example.comparegetimagespeed.adapter.MainAdapter
import com.example.comparegetimagespeed.api.ApiService
import com.example.comparegetimagespeed.databinding.ActivityMethodThreeBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class MethodThreeActivity : AppCompatActivity(){

//    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.IO + Job()

    private val binding by lazy { ActivityMethodThreeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {

        //ListAdapter 안쓰고 코루틴만 쓴 경우
        val adapter = MainAdapter()
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.layoutManager = GridLayoutManager(this, 1)

        //불러오기
        val retrofit = Retrofit.Builder()
            .baseUrl(URL.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        //ListAdapter 안쓰고 코루틴만 쓴 경우
        binding.getImageButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getImagesWithCoroutine()
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        Log.e("asdasd", "성공")
                        adapter.urlList = response.body()?.images ?: listOf()
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }

        //ListAdapter 안쓰고 LayoutInflater 돌리기
//        binding.getImageButton.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val response = service.getImagesWithCoroutine()
//                if (response.isSuccessful) {
//                    val imageList = response.body()?.images ?: listOf()
//                    withContext(Dispatchers.Main) {
//                        for (i in 0..6000){
//                            launch {
//                                val new_item = LayoutInflater.from(this@MethodThreeActivity).inflate(R.layout.item_image, null, false)
//                                Glide.with(this@MethodThreeActivity)
//                                    .load(imageList[i].imageUrl)
//                                    .into(new_item.findViewById(R.id.item_image))
//                                new_item.findViewById<TextView>(R.id.item_id).text = "${imageList[i].id}"
//                                binding.threeLinearLayout.addView(new_item)
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
}