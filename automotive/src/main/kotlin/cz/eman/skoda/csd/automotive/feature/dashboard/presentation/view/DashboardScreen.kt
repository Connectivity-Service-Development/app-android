package cz.eman.skoda.csd.automotive.feature.dashboard.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.eman.skoda.csd.automotive.R
import cz.eman.skoda.csd.automotive.feature.dashboard.presentation.model.DashboardItem
import cz.eman.skoda.csd.automotive.feature.dashboard.presentation.model.DashboardModel
import cz.eman.skoda.csd.automotive.feature.dashboard.presentation.viewmodel.DashboardViewModel
import cz.eman.skoda.csd.shared.presentation.theme.Background
import cz.eman.skoda.csd.shared.presentation.theme.BackgroundGray
import cz.eman.skoda.csd.shared.presentation.theme.Green
import cz.eman.skoda.csd.shared.presentation.theme.SkodaNext
import cz.eman.skoda.csd.shared.presentation.theme.White
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onDiscoverClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val model by viewModel.model.collectAsState()
    DashboardScreen(
        modifier = modifier,
        model = model,
        onBackButtonClick = onBackClick,
        onDiscoverButtonClick = onDiscoverClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreen(
    model: DashboardModel,
    onBackButtonClick: () -> Unit,
    onDiscoverButtonClick: () -> Unit,
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
                        text = stringResource(id = R.string.dashboard_title),
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
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
                contentPadding = PaddingValues(
                    horizontal = 64.dp,
                    vertical = 8.dp,
                ),
            ) {
                Button(
                    onClick = onDiscoverButtonClick,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green,
                        contentColor = Background,
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 44.dp,
                        vertical = 21.dp,
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.dashboard_button_discover),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = SkodaNext,
                        lineHeight = 14.72.sp,
                    )
                }
            }
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
            if (model.items.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.dashboard_content),
                        color = White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SkodaNext,
                        lineHeight = 32.sp,
                    )

                    Spacer(modifier = Modifier.height(height = 45.dp))
                }
            }

            itemsIndexed(
                items = model.items,
            ) { index, item ->
                if (index > 0) {
                    Spacer(modifier = Modifier.height(height = 40.dp))
                }

                Item(
                    model = item,
                )
            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen(
        model = DashboardModel(
            items = listOf(
                DashboardItem(
                    id = UUID.randomUUID(),
                    name = "Traffication",
                    expiresIn = "13/11/2024",
                    description = "You will loose access to real-time information on accidents, dangerous sections and closures.",
                ),
                DashboardItem(
                    id = UUID.randomUUID(),
                    name = "Travel Assist",
                    expiresIn = "12/11/2024",
                    description = "You will loose access to traffic data to recommend routes and find you the best route at any given time.",
                ),
            ),
        ),
        onBackButtonClick = {},
        onDiscoverButtonClick = {},
    )
}

@Composable
private fun Item(
    model: DashboardItem,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Green)) {
                    append(model.name)
                }
                append(stringResource(id = R.string.common_expires_template))
                append(model.expiresIn)
            },
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
            fontFamily = SkodaNext,
            lineHeight = 32.sp,
        )

        Text(
            text = model.description,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.W400,
            fontFamily = SkodaNext,
            lineHeight = 32.sp,
        )
    }
}

@Preview
@Composable
private fun ItemPreview() {
    Item(
        model = DashboardItem(
            id = UUID.randomUUID(),
            name = "Traffication",
            expiresIn = "13/11/2024",
            description = "You will loose access to real-time information on accidents, dangerous sections and closures.",
        ),
    )
}
