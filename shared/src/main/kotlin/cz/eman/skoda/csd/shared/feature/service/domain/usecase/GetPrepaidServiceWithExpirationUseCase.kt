package cz.eman.skoda.csd.shared.feature.service.domain.usecase

import cz.eman.kaal.core.InternalResult
import cz.eman.kaal.core.success
import cz.eman.kaal.result.ErrorCode
import cz.eman.kaal.result.ErrorInfo
import cz.eman.kaal.result.successOrNull
import cz.eman.kaal.usecase.AsyncUseCase
import cz.eman.skoda.csd.shared.feature.service.domain.model.PrepaidServiceWithExpiration
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class GetPrepaidServiceWithExpirationUseCase(
    private val getPrepaidServicesWithExpiration: GetPrepaidServicesWithExpirationUseCase,
) : AsyncUseCase<UUID, InternalResult<PrepaidServiceWithExpiration>> {

    override suspend fun invoke(input: UUID): InternalResult<PrepaidServiceWithExpiration> {
        val service = getPrepaidServicesWithExpiration().successOrNull()
            ?.find { input == it.service.id }
        return if (service != null) {
            success(service)
        } else {
            error(ErrorInfo(code = ErrorCode.UNDEFINED))
        }
    }
}
