package com.example.comparegetimagespeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.comparegetimagespeed.databinding.ActivityMainBinding
import com.example.comparegetimagespeed.method1.MethodOneActivity
import com.example.comparegetimagespeed.method2.MethodTwoActivity
import com.example.comparegetimagespeed.method3.MethodThreeActivity
import com.example.comparegetimagespeed.method4.MethodFourActivity

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //다크모드 금지
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.Method1RetrofitButton.setOnClickListener {
            val intent = Intent(this, MethodOneActivity::class.java)
            startActivity(intent)
        }

        binding.Method2Button.setOnClickListener {
            val intent = Intent(this, MethodTwoActivity::class.java)
            startActivity(intent)
        }

        binding.Method3Button.setOnClickListener {
            val intent = Intent(this, MethodThreeActivity::class.java)
            startActivity(intent)
        }

        binding.Method4Button.setOnClickListener {
            val intent = Intent(this, MethodFourActivity::class.java)
            startActivity(intent)
        }

    }
}