package cz.eman.skoda.csd.mobile.feature.service.presentation.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import cz.eman.skoda.csd.mobile.R
import cz.eman.skoda.csd.shared.presentation.theme.Green
import cz.eman.skoda.csd.shared.presentation.theme.OrangeDark

data class ServicesModel(
    val items: List<ServiceItem> = emptyList(),
)

data class ServiceItem(
    val name: String,
    val type: Type,
    val expiresIn: String? = null,
) {

    enum class Type(
        @StringRes val label: Int,
        @StringRes val color: Color,
    ) {
        Active(
            label = R.string.common_active,
            color = Green,
        ),

        Inactive(
            label = R.string.common_inactive,
            color = OrangeDark,
        ),
        ;
    }
}
