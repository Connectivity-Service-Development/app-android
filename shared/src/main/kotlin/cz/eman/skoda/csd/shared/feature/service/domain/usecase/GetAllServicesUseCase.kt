package cz.eman.skoda.csd.shared.feature.service.domain.usecase

import cz.eman.kaal.core.InternalResult
import cz.eman.kaal.usecase.AsyncSupplierUseCase
import cz.eman.skoda.csd.shared.feature.service.data.ServiceRepository
import cz.eman.skoda.csd.shared.feature.service.domain.model.PrepaidService
import org.koin.core.annotation.Factory

@Factory
class GetAllServicesUseCase(
    private val repository: ServiceRepository,
) : AsyncSupplierUseCase<InternalResult<List<PrepaidService>>> {

    override suspend fun invoke(): InternalResult<List<PrepaidService>> =
        repository.getServices()
}
