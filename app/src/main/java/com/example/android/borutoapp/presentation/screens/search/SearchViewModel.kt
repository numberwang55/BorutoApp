package com.example.android.borutoapp.presentation.screens.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.borutoapp.domain.model.entity.HeroEntity
import com.example.android.borutoapp.domain.use_cases.UseCases
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _searchedHero = MutableStateFlow<PagingData<HeroEntity>>(PagingData.empty())
    val searchedHero: StateFlow<PagingData<HeroEntity>> = _searchedHero

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchHero(query: String) {
        viewModelScope.launch {
            useCases.searchHeroUseCase(query).cachedIn(viewModelScope).collect {
                _searchedHero.value = it
            }
        }
    }
}