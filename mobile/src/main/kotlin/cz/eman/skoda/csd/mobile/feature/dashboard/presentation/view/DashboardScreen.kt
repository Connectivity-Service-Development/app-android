package cz.eman.skoda.csd.mobile.feature.dashboard.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.eman.skoda.csd.mobile.R
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.DashboardItem
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.DashboardModel
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.RangeItem
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.ServicesItem
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.TemperatureItem
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.VehicleItem
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.viewmodel.DashboardViewModel
import cz.eman.skoda.csd.shared.presentation.theme.Background
import cz.eman.skoda.csd.shared.presentation.theme.SkodaNext
import cz.eman.skoda.csd.shared.presentation.theme.White
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    onServicesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val model by viewModel.model.collectAsState()
    DashboardScreen(
        modifier = modifier,
        model = model,
        onNotificationsButtonClick = { /* not-implemented */ },
        onItemClick = { item ->
            if (item is ServicesItem) {
                onServicesClick()
            } else {
                /* not-implemented */
            }
        },
        onRemindButtonClick = viewModel::onRemindButtonClick,
        onDiscoverButtonClick = onServicesClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreen(
    model: DashboardModel,
    onNotificationsButtonClick: () -> Unit,
    onItemClick: (DashboardItem) -> Unit,
    onRemindButtonClick: () -> Unit,
    onDiscoverButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = Background,
        contentColor = White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Enyaq",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = SkodaNext,
                        lineHeight = 48.sp,
                    )
                },
                actions = {
                    IconButton(
                        onClick = onNotificationsButtonClick,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bell),
                            contentDescription = null,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background,
                    navigationIconContentColor = White,
                    titleContentColor = White,
                    actionIconContentColor = White,
                ),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .fillMaxSize(),
        ) {
            if (model.isExpirationBanner) {
                item {
                    InfoBanner(
                        onRemindButtonClick = onRemindButtonClick,
                        onDiscoverButtonClick = onDiscoverButtonClick,
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            bottom = 8.dp,
                        ),
                ) {
                    Image(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterEnd)
                            .height(height = 230.dp),
                        painter = painterResource(id = R.drawable.img_car_enyaq),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                    )
                }
            }

            itemsIndexed(
                items = model.items,
            ) { index, item ->
                if (index > 0) {
                    Spacer(modifier = Modifier.height(height = 8.dp))
                }

                Box(
                    modifier = Modifier.padding(horizontal = 16.dp),
                ) {
                    when (item) {
                        is RangeItem -> RangeCard(
                            model = item,
                            onClick = { onItemClick(item) },
                        )

                        is ServicesItem -> ServicesCard(
                            model = item,
                            onClick = { onItemClick(item) },
                        )

                        is TemperatureItem -> TemperatureCard(
                            model = item,
                            onClick = { onItemClick(item) },
                        )

                        is VehicleItem -> VehicleCard(
                            model = item,
                            onClick = { onItemClick(item) },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen(
        model = DashboardModel(
            isExpirationBanner = true,
            items = listOf(
                VehicleItem,
                ServicesItem(
                    active = 5,
                    total = 6,
                    expired = 2,
                ),
                RangeItem,
                TemperatureItem,
            ),
        ),
        onNotificationsButtonClick = {},
        onItemClick = {},
        onRemindButtonClick = {},
        onDiscoverButtonClick = {},
    )
}
