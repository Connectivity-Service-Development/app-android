package cz.eman.skoda.csd.automotive.feature.dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.result.Result
import cz.eman.skoda.csd.automotive.feature.dashboard.presentation.model.DashboardItem
import cz.eman.skoda.csd.automotive.feature.dashboard.presentation.model.DashboardModel
import cz.eman.skoda.csd.shared.feature.service.domain.usecase.GetActivePrepaidServicesUseCase
import cz.eman.skoda.csd.shared.feature.service.domain.usecase.IsServiceExpiringUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@KoinViewModel
class DashboardViewModel(
    private val getActiveServices: GetActivePrepaidServicesUseCase,
    private val isServiceExpiring: IsServiceExpiringUseCase,
) : ViewModel() {

    private val _model = MutableStateFlow(DashboardModel())
    val model: StateFlow<DashboardModel> = _model.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            when (val result = getActiveServices()) {
                is Result.Success -> {
                    _model.update {
                        it.copy(
                            items = result.value
                                .filter { service ->
                                    isServiceExpiring(service.expiresIn)
                                }
                                .map { service ->
                                    DashboardItem(
                                        id = service.service.id,
                                        name = service.service.name,
                                        expiresIn = service.expiresIn.format(FormatterExpiresIn),
                                        description = service.service.description,
                                    )
                                },
                        )
                    }
                }

                is Result.Error -> {
                    /* no-op */
                }
            }
        }
    }

    private companion object {
        private val FormatterExpiresIn = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ROOT)
    }
}
