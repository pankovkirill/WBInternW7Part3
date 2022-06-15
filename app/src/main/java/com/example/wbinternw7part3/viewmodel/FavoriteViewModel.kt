package com.example.wbinternw7part3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbinternw7part3.model.AppState
import com.example.wbinternw7part3.model.room.FavoriteDataBaseFactory
import com.example.wbinternw7part3.utils.mapFavoriteEntityToFavoriteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel : ViewModel() {
    private val db = FavoriteDataBaseFactory.create().favoriteDao()

    private val _data = MutableLiveData<AppState>()

    val liveData: LiveData<AppState> = _data

    fun getData() {
        _data.postValue(AppState.Loading)
        viewModelScope.launch { loadData() }
    }

    private suspend fun loadData() = withContext(Dispatchers.IO) {
        try {
            val data = db.getFavoriteList()
            _data.postValue(AppState.SuccessFavorite(mapFavoriteEntityToFavoriteData(data)))
        } catch (e: Throwable) {
            _data.postValue(AppState.Error(e))
        }
    }
}