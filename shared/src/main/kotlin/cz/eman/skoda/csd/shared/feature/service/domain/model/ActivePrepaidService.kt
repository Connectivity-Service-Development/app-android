package cz.eman.skoda.csd.shared.feature.service.domain.model

import java.time.LocalDate

data class ActivePrepaidService(
    val service: PrepaidService,
    val expiresIn: LocalDate,
    val isExpired: Boolean,
)
