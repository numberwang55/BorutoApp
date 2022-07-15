package com.example.android.borutoapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.borutoapp.R
import com.example.android.borutoapp.presentation.ui.theme.INFO_ICON_SIZE
import com.example.android.borutoapp.presentation.ui.theme.SMALL_PADDING
import com.example.android.borutoapp.presentation.ui.theme.titleColor

@Composable
fun InfoBox(
    icon: Painter,
    iconColour: Color,
    bigText: String,
    smallText: String,
    textColour: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(end = SMALL_PADDING)
                .size(INFO_ICON_SIZE),
            painter = icon,
            contentDescription = stringResource(id = R.string.info_icon),
            tint = iconColour
        )
        Column(

        ) {
            Text(
                text = bigText,
                color = textColour,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Black
            )
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = smallText,
                color = textColour,
                fontSize = MaterialTheme.typography.overline.fontSize,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoBoxPreview() {
    InfoBox(
        icon = painterResource(id = R.drawable.ic_bolt),
        iconColour = MaterialTheme.colors.primary,
        bigText = "90",
        smallText = "Power",
        textColour = MaterialTheme.colors.titleColor
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun InfoBoxDarkPreview() {
    InfoBox(
        icon = painterResource(id = R.drawable.ic_bolt),
        iconColour = MaterialTheme.colors.primary,
        bigText = "90",
        smallText = "Power",
        textColour = MaterialTheme.colors.titleColor
    )
}