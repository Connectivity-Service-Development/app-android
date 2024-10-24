package cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model

sealed interface DashboardItem

data object VehicleItem : DashboardItem

data object RangeItem : DashboardItem

data object TemperatureItem : DashboardItem

data class ServicesItem(
    val active: Int,
    val total: Int,
    val expired: Int,
) : DashboardItem
