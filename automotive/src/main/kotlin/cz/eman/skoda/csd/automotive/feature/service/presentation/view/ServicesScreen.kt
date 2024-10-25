package cz.eman.skoda.csd.automotive.feature.service.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.eman.skoda.csd.automotive.R
import cz.eman.skoda.csd.automotive.feature.service.presentation.model.ServiceItem
import cz.eman.skoda.csd.automotive.feature.service.presentation.model.ServicesModel
import cz.eman.skoda.csd.automotive.feature.service.presentation.model.Status
import cz.eman.skoda.csd.automotive.feature.service.presentation.viewmodel.ServicesViewModel
import cz.eman.skoda.csd.shared.presentation.theme.Background
import cz.eman.skoda.csd.shared.presentation.theme.BackgroundGray
import cz.eman.skoda.csd.shared.presentation.theme.Green
import cz.eman.skoda.csd.shared.presentation.theme.SkodaNext
import cz.eman.skoda.csd.shared.presentation.theme.White
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

@Composable
fun ServicesScreen(
    viewModel: ServicesViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onServiceClick: (UUID) -> Unit,
    modifier: Modifier = Modifier,
) {
    val model by viewModel.model.collectAsState()
    ServicesScreen(
        modifier = modifier,
        model = model,
        onBackButtonClick = onBackClick,
        onOrderButtonClick = viewModel::onOrderButtonClick,
        onItemClick = { onServiceClick(it.id) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ServicesScreen(
    model: ServicesModel,
    onBackButtonClick: () -> Unit,
    onOrderButtonClick: (ServiceItem) -> Unit,
    onItemClick: (ServiceItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = BackgroundGray,
        contentColor = White,
        topBar = {
            MediumTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.padding(start = 48.dp),
                        text = stringResource(id = R.string.services_title),
                        color = White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = SkodaNext,
                        lineHeight = 60.sp,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = White,
                ),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = 64.dp,
                vertical = 32.dp,
            ),
        ) {
            itemsIndexed(
                items = model.items,
            ) { index, item ->
                if (index > 0) {
                    Spacer(modifier = Modifier.height(height = 30.dp))
                }

                Item(
                    model = item,
                    onClick = { onItemClick(item) },
                    onOrderButtonClick = { onOrderButtonClick(item) },
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
                    id = UUID.randomUUID(),
                    name = "Infotainment Online",
                    status = Status.Active,
                    expiresIn = "12/11/2024",
                ),
                ServiceItem(
                    id = UUID.randomUUID(),
                    name = "Media Streaming",
                    status = Status.Inactive,
                ),
                ServiceItem(
                    id = UUID.randomUUID(),
                    name = "Media Streaming",
                    status = Status.Expiring,
                    expiresIn = "12/11/2024",
                ),
            ),
        ),
        onBackButtonClick = {},
        onOrderButtonClick = {},
        onItemClick = {},
    )
}

@Composable
private fun Item(
    model: ServiceItem,
    onClick: () -> Unit,
    onOrderButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(fraction = .6f)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(space = 12.dp),
    ) {
        Text(
            text = model.name,
            color = Green,
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            fontFamily = SkodaNext,
            lineHeight = 28.sp,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Column(
                modifier = Modifier.weight(weight = 1f),
                verticalArrangement = Arrangement.spacedBy(space = 12.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.services_label_status),
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = SkodaNext,
                    lineHeight = 28.sp,
                )

                Text(
                    text = stringResource(id = R.string.services_label_expiration),
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = SkodaNext,
                    lineHeight = 28.sp,
                )
            }

            Spacer(modifier = Modifier.weight(weight = 4f))

            Column(
                modifier = Modifier.weight(weight = 1f),
                verticalArrangement = Arrangement.spacedBy(space = 12.dp),
            ) {
                Text(
                    text = stringResource(id = model.status.label),
                    color = if (Status.Inactive == model.status) model.status.color else White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = SkodaNext,
                    lineHeight = 28.sp,
                )

                Text(
                    text = model.expiresIn.orEmpty(),
                    color = model.status.color,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = SkodaNext,
                    lineHeight = 28.sp,
                )
            }
        }

        if (Status.Active != model.status) {
            Button(
                modifier = Modifier.padding(top = 18.dp),
                onClick = onOrderButtonClick,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green,
                    contentColor = Background,
                ),
                contentPadding = PaddingValues(
                    horizontal = 28.dp,
                    vertical = 12.dp,
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.services_button_order),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = SkodaNext,
                    lineHeight = 14.72.sp,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ItemPreview() {
    Item(
        model = ServiceItem(
            id = UUID.randomUUID(),
            name = "Traffication",
            status = Status.Active,
            expiresIn = "11/10/2024",
        ),
        onClick = {},
        onOrderButtonClick = {},
    )
}
