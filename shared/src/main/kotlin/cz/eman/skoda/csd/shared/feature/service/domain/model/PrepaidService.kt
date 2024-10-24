package cz.eman.skoda.csd.shared.feature.service.domain.model

import java.util.UUID

data class PrepaidService(
    val id: UUID,
    val name: String,
    val price: Double,
    val description: String,
    val bulletPoints: List<String>,
)
