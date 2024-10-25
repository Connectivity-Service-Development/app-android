package cz.eman.skoda.csd.mobile.feature.service.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.result.Result
import cz.eman.skoda.csd.mobile.feature.service.presentation.model.ServiceDetailModel
import cz.eman.skoda.csd.mobile.feature.service.presentation.model.Type
import cz.eman.skoda.csd.shared.feature.service.domain.usecase.GetPrepaidServiceWithExpirationUseCase
import cz.eman.skoda.csd.shared.feature.service.domain.usecase.IsServiceExpiringUseCase
import cz.eman.skoda.csd.shared.feature.service.domain.usecase.ProlongServiceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

@KoinViewModel
class ServiceDetailViewModel(
    @Provided private val id: UUID,
    private val getService: GetPrepaidServiceWithExpirationUseCase,
    private val isServiceExpiring: IsServiceExpiringUseCase,
    private val prolongService: ProlongServiceUseCase,
) : ViewModel() {

    private val _model = MutableStateFlow(ServiceDetailModel())
    val model: StateFlow<ServiceDetailModel> = _model.asStateFlow()

    init {
        loadService()
    }

    private fun loadService() {
        viewModelScope.launch {
            when (val result = getService(id)) {
                is Result.Success -> {
                    _model.update {
                        it.copy(
                            data = ServiceDetailModel.Data(
                                name = result.value.service.name,
                                description = result.value.service.description,
                                bulletPoints = result.value.service.bulletPoints,
                                type = if (result.value.expiration != null) Type.Active else Type.Inactive,
                                expiresIn = result.value.expiration?.expiresIn?.let(FormatterExpiresIn::format),
                                isSoonExpiration = result.value.expiration
                                    ?.expiresIn
                                    ?.let { expiresIn ->
                                        isServiceExpiring(expiresIn)
                                    }
                                    ?: false,
                            ),
                        )
                    }
                }

                is Result.Error -> {
                    /* no-op */
                }
            }
        }
    }

    fun onOrderButtonClick() {
        viewModelScope.launch {
            val result = prolongService(id)
            when (result) {
                is Result.Success -> {
                    // reload
                    loadService()
                }

                is Result.Error -> {

                }
            }
        }
    }

    private companion object {
        private val FormatterExpiresIn = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ROOT)
    }
}
