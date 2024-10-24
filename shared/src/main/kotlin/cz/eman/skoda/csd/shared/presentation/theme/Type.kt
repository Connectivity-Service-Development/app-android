package cz.eman.skoda.csd.shared.presentation.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import cz.eman.skoda.csd.shared.R

val SkodaNext = FontFamily(
    Font(
        resId = R.font.skodanext_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.skodanext_regular_italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.skodanext_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.skodanext_bold_italic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.skodanext_black,
        weight = FontWeight.Black,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.skodanext_black_italic,
        weight = FontWeight.Black,
        style = FontStyle.Italic,
    ),
)
