package com.example.android.borutoapp.presentation.screens.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.borutoapp.domain.model.entity.HeroEntity
import com.example.android.borutoapp.domain.use_cases.UseCases
import com.example.android.borutoapp.util.Constants.DETAILS_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _selectedHero: MutableState<HeroEntity?> = mutableStateOf(null)
    val selectedHero: State<HeroEntity?> = _selectedHero

    init {
        viewModelScope.launch {
            val heroId = savedStateHandle.get<Int>(DETAILS_ARGUMENT_KEY)
            _selectedHero.value = heroId?.let {
                useCases.getHeroByIdUseCase(it)
            }
        }
    }
    // changed from MutableSharedFlow
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _colourPalette: MutableState<Map<String, String>> = mutableStateOf(mapOf())
    val colourPalette: State<Map<String, String>> = _colourPalette

    fun generateColourPalette() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.GenerateColourPalette)
        }
    }

    fun setColourPalette(colours: Map<String, String>) {
        _colourPalette.value = colours
    }

}