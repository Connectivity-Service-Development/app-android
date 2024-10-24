package cz.eman.skoda.csd.shared.feature.service.domain.usecase

import cz.eman.kaal.core.InternalResult
import cz.eman.kaal.core.success
import cz.eman.kaal.result.ErrorCode
import cz.eman.kaal.result.ErrorInfo
import cz.eman.kaal.result.successOrNull
import cz.eman.kaal.usecase.AsyncSupplierUseCase
import cz.eman.skoda.csd.shared.feature.service.domain.model.ActivePrepaidService
import cz.eman.skoda.csd.shared.feature.service.domain.model.PrepaidService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class GetActivePrepaidServicesUseCase(
    private val getAllServices: GetAllServicesUseCase,
    private val getActiveServices: GetActiveServicesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : AsyncSupplierUseCase<InternalResult<List<ActivePrepaidService>>> {

    override suspend fun invoke(): InternalResult<List<ActivePrepaidService>> = coroutineScope {
        val allServicesDeferred = async { getAllServices() }
        val activeServicesDeferred = async { getActiveServices() }

        val allServices = allServicesDeferred.await().successOrNull()
        val activeServices = activeServicesDeferred.await().successOrNull()
        if (allServices != null && activeServices != null) {
            withContext(dispatcher) {
                val services = allServices.associateBy(PrepaidService::id)
                success(
                    activeServices.mapNotNull { activeService ->
                        services[activeService.serviceId]?.let { service ->
                            ActivePrepaidService(
                                service = service,
                                expiresIn = activeService.expiresIn,
                                isExpired = activeService.isExpired,
                            )
                        }
                    },
                )
            }
        } else {
            error(ErrorInfo(code = ErrorCode.UNDEFINED))
        }
    }
}
