package cz.eman.skoda.csd.automotive.feature.service.presentation.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import cz.eman.skoda.csd.automotive.R
import cz.eman.skoda.csd.shared.presentation.theme.White

enum class Status(
    @StringRes val label: Int,
    @StringRes val color: Color,
) {
    Active(
        label = R.string.common_active,
        color = White,
    ),

    Inactive(
        label = R.string.common_inactive,
        color = Color(0xFFFF6A0E),
    ),

    Expiring(
        label = R.string.common_expiring,
        color = Color(0xFFD4564C),
    ),
    ;
}
