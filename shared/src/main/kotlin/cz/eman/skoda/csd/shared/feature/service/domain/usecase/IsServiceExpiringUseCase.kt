package cz.eman.skoda.csd.shared.feature.service.domain.usecase

import cz.eman.kaal.usecase.UseCase
import org.koin.core.annotation.Factory
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Factory
class IsServiceExpiringUseCase : UseCase<LocalDate, Boolean> {

    override fun invoke(input: LocalDate): Boolean =
        ChronoUnit.DAYS.between(LocalDate.now(), input) <= 30
}
