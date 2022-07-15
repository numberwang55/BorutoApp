package com.example.android.borutoapp.presentation.screens.details

import android.graphics.Color.parseColor
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.android.borutoapp.R
import com.example.android.borutoapp.domain.model.entity.HeroEntity
import com.example.android.borutoapp.presentation.components.InfoBox
import com.example.android.borutoapp.presentation.components.OrderedList
import com.example.android.borutoapp.presentation.ui.theme.*
import com.example.android.borutoapp.util.Constants.ABOUT_TEXT_MAX_LINES
import com.example.android.borutoapp.util.Constants.BASE_URL
import com.example.android.borutoapp.util.Constants.MIN_BACKGROUND_IMAGE_HEIGHT
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsContent(
    navController: NavController,
    selectedHero: HeroEntity?,
    colours: Map<String, String>
) {
    var vibrant by remember { mutableStateOf("#000000") }
    var darkVibrant by remember { mutableStateOf("#000000") }
    var onDarkVibrant by remember { mutableStateOf("#FFFFFF") }

    LaunchedEffect(key1 = selectedHero) {
        vibrant = colours["vibrant"]!!
        darkVibrant = colours["darkVibrant"]!!
        onDarkVibrant = colours["onDarkVibrant"]!!
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color(parseColor(darkVibrant))
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val currentSheetFraction = scaffoldState.currentSheetFraction
    val radiusAnim by animateDpAsState(
        targetValue = if (currentSheetFraction == 1f)
            EXTRA_LARGE_PADDING
        else
            EXPANDED_RADIUS_LEVEL
    )
    Log.d("Fraction NEW", "$currentSheetFraction")
    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(
            topEnd = radiusAnim,
            topStart = radiusAnim
        ),
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            selectedHero?.let {
                BottomSheetContent(
                    selectedHero = it,
                    infoBoxColour = Color(parseColor(vibrant)),
                    sheetBackgroundColour = Color(parseColor(darkVibrant)),
                    contentColour = Color(parseColor(onDarkVibrant))
                )
            }
        }
    ) {
        selectedHero?.image?.let {
            BackgroundContent(
                heroImage = it,
                imageFraction = currentSheetFraction,
                backgroundColour = Color(parseColor(darkVibrant))
            ) {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    selectedHero: HeroEntity,
    infoBoxColour: Color = MaterialTheme.colors.primary,
    sheetBackgroundColour: Color = MaterialTheme.colors.surface,
    contentColour: Color = MaterialTheme.colors.titleColor
) {
    Column(
        modifier = Modifier
            .background(color = sheetBackgroundColour)
            .padding(LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(INFO_ICON_SIZE)
                    .weight(2f),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.app_logo),
                tint = contentColour
            )
            Text(
                modifier = Modifier.weight(8f),
                text = selectedHero.name,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold,
                color = contentColour
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.ic_bolt),
                iconColour = infoBoxColour,
                bigText = "${selectedHero.power}",
                smallText = stringResource(id = R.string.power),
                textColour = contentColour
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_calendar),
                iconColour = infoBoxColour,
                bigText = selectedHero.month,
                smallText = stringResource(id = R.string.month),
                textColour = contentColour
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_cake),
                iconColour = infoBoxColour,
                bigText = selectedHero.day,
                smallText = stringResource(id = R.string.birthday),
                textColour = contentColour
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.about),
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            color = contentColour,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = MEDIUM_PADDING),
            text = selectedHero.about,
            fontSize = MaterialTheme.typography.body1.fontSize,
            color = contentColour,
            maxLines = ABOUT_TEXT_MAX_LINES
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = stringResource(id = R.string.family),
                items = selectedHero.family,
                textColor = contentColour
            )
            OrderedList(
                title = stringResource(id = R.string.abilities),
                items = selectedHero.abilities,
                textColor = contentColour
            )
            OrderedList(
                title = stringResource(id = R.string.nature_types),
                items = selectedHero.natureTypes,
                textColor = contentColour
            )
        }
    }
}

@Composable
fun BackgroundContent(
    heroImage: String,
    imageFraction: Float = 1f,
    backgroundColour: Color = MaterialTheme.colors.surface,
    onCloseClick: () -> Unit
) {
    val imageUrl = "$BASE_URL${heroImage}"
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        error = painterResource(id = R.drawable.ic_placeholder)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColour)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction + MIN_BACKGROUND_IMAGE_HEIGHT)
                .align(Alignment.TopStart),
            painter = painter,
            contentDescription = stringResource(id = R.string.hero_image),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.padding(SMALL_PADDING),
                onClick = { onCloseClick() }
            ) {
                Icon(
                    modifier = Modifier
                        .size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close_icon),
                    tint = Color.White
                )
            }
        }
    }
}

@ExperimentalMaterialApi
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        val fraction = bottomSheetState.progress.fraction
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        return when {
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 1f
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 0f
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> 1f - fraction
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Collapsed -> 0f + fraction
            else -> fraction
        }
    }

@Preview
@Composable
fun BottomSheetContentPreview() {
    BottomSheetContent(
        selectedHero = HeroEntity(
            id = 1,
            name = "Sasuke",
            image = "/images/sasuke.jpg",
            about = "Sasuke Uchiha (うちはサスケ, Uchiha Sasuke) is one of the last surviving members of Konohagakure's Uchiha clan. After his older brother, Itachi, slaughtered their clan, Sasuke made it his mission in life to avenge them by killing Itachi. He is added to Team 7 upon becoming a ninja and, through competition with his rival and best friend, Naruto Uzumaki.",
            rating = 5.0,
            power = 98,
            month = "July",
            day = "23rd",
            family = listOf(
                "Fugaku",
                "Mikoto",
                "Itachi",
                "Sarada",
                "Sakura"
            ),
            abilities = listOf(
                "Sharingan",
                "Rinnegan",
                "Sussano",
                "Amateratsu",
                "Intelligence"
            ),
            natureTypes = listOf(
                "Lightning",
                "Fire",
                "Wind",
                "Earth",
                "Water"
            )
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF2A2A2A)
@Composable
fun BackgroundContentPreview() {
    BackgroundContent(heroImage = "$BASE_URL/images/sasuke.jpg") {

    }
}