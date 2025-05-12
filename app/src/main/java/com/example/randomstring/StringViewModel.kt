package com.example.randomstring

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StringViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StringRepository(application)
    val randomStrings = MutableLiveData<List<RandomString>>()

    fun fetchRandomString(length: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val randomString = repository.fetchRandomString(length)
            randomString?.let {
                val currentList = randomStrings.value.orEmpty().toMutableList()
                currentList.add(it)
                randomStrings.postValue(currentList)
            }
        }
    }
}
