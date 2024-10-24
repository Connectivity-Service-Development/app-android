package cz.eman.skoda.csd.mobile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.view.DashboardScreen
import cz.eman.skoda.csd.mobile.feature.inspection.InspectionScreen
import cz.eman.skoda.csd.mobile.feature.map.MapScreen
import cz.eman.skoda.csd.mobile.feature.settings.SettingsScreen
import cz.eman.skoda.csd.shared.presentation.theme.Background
import cz.eman.skoda.csd.shared.presentation.theme.Green
import cz.eman.skoda.csd.shared.presentation.theme.SkodaNext
import cz.eman.skoda.csd.shared.presentation.theme.White

data class NavigationItem(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
)

val MainMenu = listOf(
    NavigationItem(
        label = R.string.main_navigation_item_vehicle_label,
        icon = R.drawable.ic_car,
    ),
    NavigationItem(
        label = R.string.main_navigation_item_maps_label,
        icon = R.drawable.ic_marker,
    ),
    NavigationItem(
        label = R.string.main_navigation_item_inspection_label,
        icon = R.drawable.ic_health_scan,
    ),
    NavigationItem(
        label = R.string.main_navigation_item_settings_label,
        icon = R.drawable.ic_settings,
    ),
)

@Composable
fun MainContainer(
    modifier: Modifier = Modifier,
) {
    val (selectedItem, selectItem) = remember { mutableStateOf(MainMenu[0]) }
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigation(
                items = MainMenu,
                selectedItem = selectedItem,
                onItemClick = selectItem,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .fillMaxSize(),
        ) {
            when (selectedItem.label) {
                R.string.main_navigation_item_vehicle_label -> DashboardScreen()
                R.string.main_navigation_item_maps_label -> MapScreen()
                R.string.main_navigation_item_inspection_label -> InspectionScreen()
                R.string.main_navigation_item_settings_label -> SettingsScreen()
            }
        }
    }
}

@Composable
private fun BottomNavigation(
    items: List<NavigationItem>,
    selectedItem: NavigationItem,
    onItemClick: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier
            .drawBehind {
                drawLine(
                    color = Color(0x1FF3F3F3),
                    start = Offset.Zero,
                    end = Offset(
                        x = size.width,
                        y = 0f,
                    ),
                    strokeWidth = 1.dp.toPx(),
                )
            },
        containerColor = Background,
        contentColor = Color.Unspecified,
    ) {
        items.fastForEach { item ->
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.label),
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.label),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SkodaNext,
                        lineHeight = 14.sp,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Green,
                    selectedTextColor = Green,
                    unselectedIconColor = White,
                    unselectedTextColor = White,
                    indicatorColor = Color.Transparent,
                ),
            )
        }
    }
}

@Preview
@Composable
private fun BottomNavigationPreview() {
    BottomNavigation(
        items = MainMenu,
        selectedItem = MainMenu[0],
        onItemClick = {},
    )
}
