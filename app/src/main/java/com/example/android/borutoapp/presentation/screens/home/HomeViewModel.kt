package com.example.android.borutoapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.example.android.borutoapp.data.remote.BorutoApi
import com.example.android.borutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    val getAllHeroes = useCases.getAllHeroesUseCase()

}