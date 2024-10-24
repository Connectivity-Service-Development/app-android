package cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model

data class DashboardModel(
    val items: List<DashboardItem> = emptyList(),
    val isExpirationBanner: Boolean = false,
)
