package cz.eman.skoda.csd.shared.feature.service.data

import cz.eman.kaal.core.InternalResult
import cz.eman.kaal.result.ErrorCode
import cz.eman.kaal.result.ErrorInfo
import cz.eman.kaal.result.error
import cz.eman.kaal.result.success
import cz.eman.skoda.csd.shared.data.api.model.ProlongRequestDto
import cz.eman.skoda.csd.shared.data.api.service.PrepaidServicesApi
import cz.eman.skoda.csd.shared.feature.service.domain.model.ActiveService
import cz.eman.skoda.csd.shared.feature.service.domain.model.PrepaidService
import org.koin.core.annotation.Single
import retrofit2.Response
import java.util.UUID
import kotlin.coroutines.cancellation.CancellationException

@Single
class ServiceRepository(
    private val apiService: PrepaidServicesApi,
) {

    suspend fun getServices(): InternalResult<List<PrepaidService>> = callApi(
        operation = { apiService.getAllPrepaidServices() },
        map = { services ->
            services.map { dto ->
                PrepaidService(
                    id = dto.id,
                    name = dto.serviceName,
                    price = dto.price,
                    description = dto.description,
                    bulletPoints = dto.bulletPoints.orEmpty(),
                )
            }
        },
    )

    suspend fun getActiveServices(): InternalResult<List<ActiveService>> = callApi(
        operation = { apiService.getUserPrepaidServices() },
        map = { services ->
            services.map { dto ->
                ActiveService(
                    serviceId = dto.prepaidServiceId,
                    expiresIn = dto.expirationDate,
                    isExpired = dto.expired,
                )
            }
        },
    )

    suspend fun prolongService(id: UUID): InternalResult<Unit> = callApi(
        operation = { apiService.prolongPrepaidService(ProlongRequestDto(serviceId = id)) },
    )

    private inline fun <T, R : Any> callApi(
        operation: () -> Response<T>,
        map: (T) -> R,
    ): InternalResult<R> {
        return try {
            val response = operation()
            if (response.isSuccessful) {
                success(map(checkNotNull(response.body()) { "Missing response body" }))
            } else {
                kotlin.error("Unexpected response")
            }
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: Throwable) {
            error(
                ErrorInfo(
                    code = ErrorCode.UNDEFINED,
                    exception = ex,
                ),
            )
        }
    }

    private inline fun callApi(
        operation: () -> Response<Unit>,
    ): InternalResult<Unit> {
        return try {
            val response = operation()
            if (response.isSuccessful) {
                success(Unit)
            } else {
                kotlin.error("Unexpected response")
            }
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: Throwable) {
            error(
                ErrorInfo(
                    code = ErrorCode.UNDEFINED,
                    exception = ex,
                ),
            )
        }
    }
}
