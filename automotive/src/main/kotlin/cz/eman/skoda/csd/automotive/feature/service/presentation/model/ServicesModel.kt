package cz.eman.skoda.csd.automotive.feature.service.presentation.model

import java.util.UUID

data class ServicesModel(
    val items: List<ServiceItem> = emptyList(),
)

data class ServiceItem(
    val id: UUID,
    val name: String,
    val status: Status,
    val expiresIn: String? = null,
)
