package cz.eman.skoda.csd.automotive.feature.service.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.result.Result
import cz.eman.skoda.csd.automotive.feature.service.presentation.model.ServiceItem
import cz.eman.skoda.csd.automotive.feature.service.presentation.model.ServicesModel
import cz.eman.skoda.csd.automotive.feature.service.presentation.model.Status
import cz.eman.skoda.csd.shared.feature.service.domain.usecase.GetPrepaidServicesWithExpirationUseCase
import cz.eman.skoda.csd.shared.feature.service.domain.usecase.ProlongServiceUseCase
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
    private val getPrepaidServicesWithExpiration: GetPrepaidServicesWithExpirationUseCase,
    private val prolongService: ProlongServiceUseCase,
) : ViewModel() {

    private val _model = MutableStateFlow(ServicesModel())
    val model: StateFlow<ServicesModel> = _model.asStateFlow()

    init {
        loadServices()
    }

    private fun loadServices() {
        viewModelScope.launch {
            when (val result = getPrepaidServicesWithExpiration()) {
                is Result.Success -> {
                    val items = result.value.map { service ->
                        ServiceItem(
                            id = service.service.id,
                            name = service.service.name,
                            status = service.expiration.let { expiration ->
                                when {
                                    expiration == null -> Status.Inactive
                                    ChronoUnit.DAYS.between(LocalDate.now(), expiration.expiresIn) <= 30 -> Status.Expiring
                                    else -> Status.Active
                                }
                            },
                            expiresIn = service.expiration?.expiresIn?.let(FormatterExpiresIn::format),
                        )
                    }
                    _model.update { it.copy(items = items) }
                }

                is Result.Error -> {
                    /* no-op */
                }
            }
        }
    }

    fun onOrderButtonClick(item: ServiceItem) {
        viewModelScope.launch {
            val result = prolongService(item.id)
            when (result) {
                is Result.Success -> {
                    // reload
                    loadServices()
                }

                is Result.Error -> {

                }
            }
        }
    }

    companion object {
        private val FormatterExpiresIn = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ROOT)
    }
}
