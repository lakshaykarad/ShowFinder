package com.example.showfinder.ui.theme.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showfinder.data.Repository.MediaRepository
import com.example.showfinder.data.model.Media
import com.example.showfinder.data.model.Movie
import com.example.showfinder.data.model.TvShow
import com.example.showfinder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx3.await
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel(){

    private val _detailState = MutableStateFlow<Resource<Media>>(Resource.Loading)
    val detailState: StateFlow<Resource<Media>> = _detailState

    fun loadDetailScreen(imdbId: String){
        viewModelScope.launch {
            _detailState.value = Resource.Loading
            try {
                val movie = repository.getMediaDetailsByImdb(imdbId)
                if (movie != null) {
                    _detailState.value = Resource.Success(movie)
                } else {
                    _detailState.value = Resource.Error("Details not found")
                }
            }  catch ( e : Exception){
                _detailState.value = Resource.Error(e.message ?: "Unknow Error")
            }
        }
    }

}