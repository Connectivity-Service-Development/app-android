package cz.eman.skoda.csd.mobile.feature.service.presentation.model

import java.util.UUID

data class ServicesModel(
    val items: List<ServiceItem> = emptyList(),
)

data class ServiceItem(
    val id: UUID,
    val name: String,
    val type: Type,
    val expiresIn: String? = null,
)
