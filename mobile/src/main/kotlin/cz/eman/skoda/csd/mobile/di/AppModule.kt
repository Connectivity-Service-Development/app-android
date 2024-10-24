package cz.eman.skoda.csd.mobile.di

import cz.eman.skoda.csd.shared.data.api.service.PrepaidServicesApi
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.openapitools.client.infrastructure.ApiClient

@Module
@ComponentScan("cz.eman.skoda.csd.mobile")
class AppModule {

    @Single
    fun provideApiClient(): ApiClient = ApiClient()

    @Single
    fun providePrepaidServicesApiService(client: ApiClient): PrepaidServicesApi =
        client.createService(PrepaidServicesApi::class.java)
}
