package com.valdirsilva.repositorieslist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.valdirsilva.repositorieslist.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }
}
