package cz.eman.skoda.csd.mobile.feature.dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.result.Result
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.DashboardModel
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.RangeItem
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.ServicesItem
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.TemperatureItem
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.VehicleItem
import cz.eman.skoda.csd.shared.feature.service.domain.usecase.GetPrepaidServicesInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DashboardViewModel(
    private val getPrepaidServicesInfo: GetPrepaidServicesInfoUseCase,
) : ViewModel() {

    private val _model = MutableStateFlow(DashboardModel())
    val model: StateFlow<DashboardModel> = _model.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            when (val result = getPrepaidServicesInfo()) {
                is Result.Success -> {
                    _model.update {
                        it.copy(
                            items = listOf(
                                VehicleItem,
                                ServicesItem(
                                    active = result.value.active,
                                    total = result.value.total,
                                    expired = result.value.expired,
                                ),
                                RangeItem,
                                TemperatureItem,
                            ),
                            isExpirationBanner = result.value.expired > 0,
                        )
                    }
                }

                is Result.Error -> {
                    /* no-op */
                }
            }
        }
    }

    fun onRemindButtonClick() {
        _model.update { it.copy(isExpirationBanner = false) }
    }
}
