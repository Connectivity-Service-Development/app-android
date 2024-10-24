package cz.eman.skoda.csd.shared.feature.service.domain.usecase

import cz.eman.kaal.core.InternalResult
import cz.eman.kaal.usecase.AsyncUseCase
import cz.eman.skoda.csd.shared.feature.service.data.ServiceRepository
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class ProlongServiceUseCase(
    private val repository: ServiceRepository,
) : AsyncUseCase<UUID, InternalResult<Unit>> {

    override suspend fun invoke(input: UUID): InternalResult<Unit> =
        repository.prolongService(id = input)
}
