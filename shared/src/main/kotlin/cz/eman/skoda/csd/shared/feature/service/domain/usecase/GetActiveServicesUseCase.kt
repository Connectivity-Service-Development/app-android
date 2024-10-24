package cz.eman.skoda.csd.shared.feature.service.domain.usecase

import cz.eman.kaal.core.InternalResult
import cz.eman.kaal.usecase.AsyncSupplierUseCase
import cz.eman.skoda.csd.shared.feature.service.data.ServiceRepository
import cz.eman.skoda.csd.shared.feature.service.domain.model.ActiveService
import org.koin.core.annotation.Factory

@Factory
class GetActiveServicesUseCase(
    private val repository: ServiceRepository,
) : AsyncSupplierUseCase<InternalResult<List<ActiveService>>> {

    override suspend fun invoke(): InternalResult<List<ActiveService>> =
        repository.getActiveServices()
}
