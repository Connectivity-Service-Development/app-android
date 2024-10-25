package cz.eman.skoda.csd.mobile.feature.service.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.eman.skoda.csd.mobile.R
import cz.eman.skoda.csd.mobile.feature.service.presentation.model.ServiceItem
import cz.eman.skoda.csd.mobile.feature.service.presentation.model.ServicesModel
import cz.eman.skoda.csd.mobile.feature.service.presentation.viewmodel.ServicesViewModel
import cz.eman.skoda.csd.shared.presentation.theme.Background
import cz.eman.skoda.csd.shared.presentation.theme.BackgroundGray
import cz.eman.skoda.csd.shared.presentation.theme.Divider
import cz.eman.skoda.csd.shared.presentation.theme.OrangeDark
import cz.eman.skoda.csd.shared.presentation.theme.SkodaNext
import cz.eman.skoda.csd.shared.presentation.theme.White
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServicesScreen(
    viewModel: ServicesViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val model by viewModel.model.collectAsState()
    ServicesScreen(
        modifier = modifier,
        model = model,
        onBackButtonClick = onBackClick,
        onOwnerPanelClick = { /* TODO */ },
        onItemClick = { /* TODO */ },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ServicesScreen(
    model: ServicesModel,
    onBackButtonClick: () -> Unit,
    onOwnerPanelClick: () -> Unit,
    onItemClick: (ServiceItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = Background,
        contentColor = White,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackButtonClick,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                title = {
                    Text(
                        text = "Prepaid Services",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = SkodaNext,
                        lineHeight = 24.sp,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background,
                    navigationIconContentColor = White,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White,
                ),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 24.dp),
        ) {
            item {
                OwnerPanel(
                    onClick = onOwnerPanelClick,
                )
            }

            item {
                CarPanel(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(
                            top = 8.dp,
                            bottom = 32.dp,
                        ),
                )
            }

            itemsIndexed(
                items = model.items,
            ) { index, item ->
                if (index > 0) {
                    HorizontalDivider(color = Divider)
                }

                ServiceItem(
                    model = item,
                    onClick = { onItemClick(item) },
                )
            }
        }
    }
}

@Preview
@Composable
private fun ServicesScreenPreview() {
    ServicesScreen(
        model = ServicesModel(
            items = listOf(
                ServiceItem(
                    name = "Infotainment Online",
                    type = Type.Active,
                    expiresIn = "12/11/2024",
                ),
                ServiceItem(
                    name = "Media Streaming",
                    type = Type.Inactive,
                ),
            ),
        ),
        onBackButtonClick = {},
        onOwnerPanelClick = {},
        onItemClick = {},
    )
}

@Composable
private fun OwnerPanel(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(size = 40.dp)
                .clip(shape = CircleShape),
            painter = painterResource(id = R.drawable.img_dummy_owner),
            contentDescription = null,
        )

        Text(
            modifier = Modifier.weight(weight = 1f),
            text = "Miroslav Udatný",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
            fontFamily = SkodaNext,
            lineHeight = 24.sp,
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = null,
            tint = Color.White,
        )
    }
}

@Preview
@Composable
private fun OwnerPanelPreview() {
    OwnerPanel(
        onClick = {},
    )
}

@Composable
private fun CarPanel(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = BackgroundGray)
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = "Eniaq",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                fontFamily = SkodaNext,
                lineHeight = 24.sp,
            )

            Text(
                text = "Vehicles",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                fontFamily = SkodaNext,
                lineHeight = 24.sp,
            )
        }

        Image(
            modifier = Modifier
                .height(height = 44.dp)
                .padding(end = 16.dp),
            painter = painterResource(id = R.drawable.img_car_enyaq_small),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
        )
    }
}

@Preview
@Composable
private fun CarPanelPreview() {
    CarPanel()
}

@Composable
private fun ServiceItem(
    model: ServiceItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(weight = 1f),
        ) {
            Text(
                text = model.name,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
                fontFamily = SkodaNext,
                lineHeight = 24.sp,
            )

            if (model.expiresIn != null) {
                Text(
                    text = stringResource(id = R.string.common_soon_expire, model.expiresIn),
                    color = OrangeDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = SkodaNext,
                    lineHeight = 16.sp,
                )
            }
        }

        Text(
            text = stringResource(id = model.type.label),
            color = model.type.color,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            fontFamily = SkodaNext,
            lineHeight = 20.sp,
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = null,
            tint = Color.White,
        )
    }
}

@Preview
@Composable
private fun ServiceItemPreview() {
    ServiceItem(
        model = ServiceItem(
            name = "Infotainment Online",
            type = Type.Active,
            expiresIn = "12/11/2024",
        ),
        onClick = {},
    )
}
