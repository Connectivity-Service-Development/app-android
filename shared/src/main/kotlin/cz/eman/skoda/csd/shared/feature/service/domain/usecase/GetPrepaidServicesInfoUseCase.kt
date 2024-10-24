package cz.eman.skoda.csd.shared.feature.service.domain.usecase

import cz.eman.kaal.core.InternalResult
import cz.eman.kaal.core.success
import cz.eman.kaal.result.ErrorCode
import cz.eman.kaal.result.ErrorInfo
import cz.eman.kaal.result.successOrNull
import cz.eman.kaal.usecase.AsyncSupplierUseCase
import cz.eman.skoda.csd.shared.feature.service.domain.model.PrepaidServicesInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.koin.core.annotation.Factory

@Factory
class GetPrepaidServicesInfoUseCase(
    private val getAllServices: GetAllServicesUseCase,
    private val getActiveServices: GetActiveServicesUseCase,
) : AsyncSupplierUseCase<InternalResult<PrepaidServicesInfo>> {

    override suspend fun invoke(): InternalResult<PrepaidServicesInfo> = coroutineScope {
        val allServicesDeferred = async { getAllServices() }
        val activeServicesDeferred = async { getActiveServices() }

        val allServices = allServicesDeferred.await().successOrNull()
        val activeServices = activeServicesDeferred.await().successOrNull()
        if (allServices != null && activeServices != null) {
            val expiredCount = activeServices.count { it.isExpired }
            success(
                PrepaidServicesInfo(
                    active = activeServices.size,
                    total = allServices.size,
                    expired = expiredCount,
                ),
            )
        } else {
            error(ErrorInfo(code = ErrorCode.UNDEFINED))
        }
    }
}
