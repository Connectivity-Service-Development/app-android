package cz.eman.skoda.csd.shared.feature.service.domain.model

data class PrepaidServicesInfo(
    val active: Int,
    val total: Int,
    val expired: Int,
)
