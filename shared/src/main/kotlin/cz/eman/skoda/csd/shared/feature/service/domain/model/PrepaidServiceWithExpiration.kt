package cz.eman.skoda.csd.shared.feature.service.domain.model

import java.time.LocalDate

data class PrepaidServiceWithExpiration(
    val service: PrepaidService,
    val expiration: Expiration?,
) {

    data class Expiration(
        val expiresIn: LocalDate,
        val isExpired: Boolean,
    )
}
