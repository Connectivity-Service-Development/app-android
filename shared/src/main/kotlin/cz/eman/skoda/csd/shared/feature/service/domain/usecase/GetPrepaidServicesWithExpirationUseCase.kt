package cz.eman.skoda.csd.shared.feature.service.domain.usecase

import cz.eman.kaal.core.InternalResult
import cz.eman.kaal.core.success
import cz.eman.kaal.result.ErrorCode
import cz.eman.kaal.result.ErrorInfo
import cz.eman.kaal.result.successOrNull
import cz.eman.kaal.usecase.AsyncSupplierUseCase
import cz.eman.skoda.csd.shared.feature.service.domain.model.ActiveService
import cz.eman.skoda.csd.shared.feature.service.domain.model.PrepaidServiceWithExpiration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class GetPrepaidServicesWithExpirationUseCase(
    private val getAllServices: GetAllServicesUseCase,
    private val getActiveServices: GetActiveServicesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : AsyncSupplierUseCase<InternalResult<List<PrepaidServiceWithExpiration>>> {

    override suspend fun invoke(): InternalResult<List<PrepaidServiceWithExpiration>> = coroutineScope {
        val allServicesDeferred = async { getAllServices() }
        val activeServicesDeferred = async { getActiveServices() }

        val allServices = allServicesDeferred.await().successOrNull()
        val activeServices = activeServicesDeferred.await().successOrNull()
        if (allServices != null && activeServices != null) {
            withContext(dispatcher) {
                val services = activeServices.associateBy(ActiveService::serviceId)
                success(
                    allServices.map { service ->
                        PrepaidServiceWithExpiration(
                            service = service,
                            expiration = services[service.id]?.let { activeService ->
                                PrepaidServiceWithExpiration.Expiration(
                                    expiresIn = activeService.expiresIn,
                                    isExpired = activeService.isExpired,
                                )
                            },
                        )
                    },
                )
            }
        } else {
            error(ErrorInfo(code = ErrorCode.UNDEFINED))
        }
    }
}
