package cz.eman.skoda.csd.shared.feature.service.domain.model

import java.time.LocalDate
import java.util.UUID

data class ActiveService(
    val serviceId: UUID,
    val expiresIn: LocalDate,
    val isExpired: Boolean,
)
