
package com.example.showfinder.ui.theme.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showfinder.data.Repository.MediaRepository
import com.example.showfinder.data.model.Movie
import com.example.showfinder.data.model.SearchItem
import com.example.showfinder.data.model.TvShow
import com.example.showfinder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx3.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {

    private val _mediaState = MutableStateFlow <Resource<Pair<List<Movie>, List<TvShow>>>>( Resource.Loading)
    val mediaState : StateFlow<Resource<Pair<List<Movie>, List<TvShow>>>> = _mediaState

    private val _searchState = MutableStateFlow <Resource<Pair<List<SearchItem>, List<SearchItem>>>> (Resource.Loading)
    val searchState : StateFlow <Resource<Pair<List<SearchItem>, List<SearchItem>>>> = _searchState

    private val _posterState = MutableStateFlow <Resource<String?>> (Resource.Loading)
    val posterState : StateFlow <Resource<String?>> = _posterState


    init {
        loadMediaData()
    }

    fun loadMediaData(){
        viewModelScope.launch {
            _mediaState.value = Resource.Loading
            try {
                val result = repository.getMoviesAndTvShows().await()
                _mediaState.value = Resource.Success(result)
            } catch ( e : Exception){
                _mediaState.value = Resource.Error(e.message ?: "Unknow Error")
            }
        }
    }

    fun searchQuery(query: String){
        viewModelScope.launch {
            _searchState.value = Resource.Loading
            try {
                val result = repository.getSearchMedia(query).await()
                _searchState.value = Resource.Success(result)
            } catch (e : Exception){
                _searchState.value = Resource.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun fetchPoster(imdbID : String){
        viewModelScope.launch {
            _posterState.value = Resource.Loading
            try {
                val result = repository.getPostersByImbd(imdbID)
                _posterState.value = Resource.Success(result)
            } catch (e : Exception){
                _posterState.value = Resource.Error(e.message ?: "Unknown Error")
            }
        }
    }


}