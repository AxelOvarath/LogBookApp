package com.myloginfbrd

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {

    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()
    private val _images = MutableLiveData<List<Bitmap>>()
    val images: LiveData<List<Bitmap>> get() = _images

    fun onTakePhoto(bitmap: Bitmap) {
        _bitmaps.value += bitmap
    }

    fun setImages(imageList: List<Bitmap>) {
        _images.value = imageList
    }
}