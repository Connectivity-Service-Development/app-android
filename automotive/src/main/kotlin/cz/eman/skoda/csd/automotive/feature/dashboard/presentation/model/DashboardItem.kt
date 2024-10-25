package cz.eman.skoda.csd.automotive.feature.dashboard.presentation.model

import java.util.UUID

data class DashboardItem(
    val id: UUID,
    val name: String,
    val expiresIn: String,
    val description: String,
)
