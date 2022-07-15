package com.example.android.borutoapp.presentation.screens.details

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android.borutoapp.util.Constants.BASE_URL
import com.example.android.borutoapp.util.PaletteGenerator.convertImageUrlToBitmap
import com.example.android.borutoapp.util.PaletteGenerator.extractColoursFromBitmap
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailsScreen(
    navController: NavController,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    val selectedHero by detailsViewModel.selectedHero
    val colourPalette by detailsViewModel.colourPalette
    Log.d("DetailsScreen", "DetailsScreen: $colourPalette")
    if (colourPalette.isNotEmpty()) {
        DetailsContent(
            navController = navController,
            selectedHero = selectedHero,
            colours = colourPalette
        )
    } else {
        detailsViewModel.generateColourPalette()
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        detailsViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.GenerateColourPalette -> {
                    val bitmap = convertImageUrlToBitmap(
                        imageUrl = "$BASE_URL${selectedHero?.image}",
                        context = context
                    )
                    if (bitmap != null) {
                        detailsViewModel.setColourPalette(
                            colours = extractColoursFromBitmap(bitmap = bitmap)
                        )
                    }
                }
            }
        }
    }

}