package com.example.wbinternw7part3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbinternw7part3.model.AppState
import com.example.wbinternw7part3.model.remote.PostsService
import com.example.wbinternw7part3.model.remote.dto.ImageResponse
import com.example.wbinternw7part3.model.remote.dto.MessageResponse
import com.example.wbinternw7part3.model.remote.dto.VoteRequest
import com.example.wbinternw7part3.model.room.FavoriteDataBaseFactory
import com.example.wbinternw7part3.utils.mapImageResponseToFavoriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val service = PostsService.create()
    private val db = FavoriteDataBaseFactory.create().favoriteDao()

    private val _dataImage = MutableLiveData<AppState>()
    private val _dataVote = MutableLiveData<MessageResponse>()

    val liveDataPost: LiveData<AppState> = _dataImage
    val liveDataVote: LiveData<MessageResponse> = _dataVote

    fun getData() {
        _dataImage.postValue(AppState.Loading)
        viewModelScope.launch { loadData() }
    }

    private suspend fun loadData() = withContext(Dispatchers.IO) {
        try {
            _dataImage.postValue(AppState.SuccessMain(service.getRandomImage()))
        } catch (e: Throwable) {
            _dataImage.postValue(AppState.Error(e))
        }
    }

    fun saveFavoriteCatToDB(imageResponse: ImageResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            db.insert(mapImageResponseToFavoriteEntity(imageResponse))
        }
    }

    fun postVote(postRequest: VoteRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _dataVote.postValue(service.createVote(postRequest))
        }
    }
}