package cz.eman.skoda.csd.mobile.feature.service.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.result.Result
import cz.eman.kaal.result.successOrNull
import cz.eman.skoda.csd.mobile.feature.service.presentation.model.ServiceItem
import cz.eman.skoda.csd.mobile.feature.service.presentation.model.ServicesModel
import cz.eman.skoda.csd.shared.feature.service.domain.model.ActiveService
import cz.eman.skoda.csd.shared.feature.service.domain.usecase.GetActiveServicesUseCase
import cz.eman.skoda.csd.shared.feature.service.domain.usecase.GetAllServicesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@KoinViewModel
class ServicesViewModel(
    private val getAllServices: GetAllServicesUseCase,
    private val getActiveServices: GetActiveServicesUseCase,
) : ViewModel() {

    private val _model = MutableStateFlow(ServicesModel())
    val model: StateFlow<ServicesModel> = _model.asStateFlow()

    init {
        loadServices()
    }

    private fun loadServices() {
        viewModelScope.launch {
            val allServicesDeferred = async { getAllServices() }
            val activeServicesDeferred = async { getActiveServices() }

            when (val allServicesResult = allServicesDeferred.await()) {
                is Result.Success -> {
                    val activeServices = activeServicesDeferred.await()
                        .successOrNull()
                        ?.associateBy(ActiveService::serviceId)
                        ?: return@launch

                    val items = allServicesResult.value.map { service ->
                        val activeService = activeServices[service.id]
                        ServiceItem(
                            name = service.name,
                            type = if (activeService != null) ServiceItem.Type.Active else ServiceItem.Type.Inactive,
                            expiresIn = activeService?.expiresIn
                                ?.takeIf { expiresIn ->
                                    ChronoUnit.DAYS.between(LocalDate.now(), expiresIn) <= 30
                                }
                                ?.let(FormatterExpiresIn::format),
                        )
                    }
                    _model.update {
                        it.copy(items = items)
                    }
                }

                is Result.Error -> {
                    /* no-op */
                }
            }
        }
    }

    companion object {
        private val FormatterExpiresIn = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ROOT)
    }
}
