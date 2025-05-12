package com.example.randomstring

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomstring.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: StringViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = StringAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.randomStrings.observe(this) { strings ->
            binding.recyclerView.adapter = StringAdapter(strings)
        }

        binding.generateButton.setOnClickListener {
            val length = binding.inputLength.text.toString().toIntOrNull() ?: 0
            viewModel.fetchRandomString(length)
        }
    }
}
