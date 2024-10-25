package cz.eman.skoda.csd.mobile.feature.service.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import cz.eman.skoda.csd.mobile.R
import cz.eman.skoda.csd.mobile.feature.service.presentation.model.ServiceDetailModel
import cz.eman.skoda.csd.mobile.feature.service.presentation.model.Type
import cz.eman.skoda.csd.mobile.feature.service.presentation.viewmodel.ServiceDetailViewModel
import cz.eman.skoda.csd.shared.presentation.theme.Background
import cz.eman.skoda.csd.shared.presentation.theme.Divider
import cz.eman.skoda.csd.shared.presentation.theme.Gray
import cz.eman.skoda.csd.shared.presentation.theme.Green
import cz.eman.skoda.csd.shared.presentation.theme.OrangeDark
import cz.eman.skoda.csd.shared.presentation.theme.SkodaNext
import cz.eman.skoda.csd.shared.presentation.theme.White
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.UUID

@Composable
fun ServiceDetailScreen(
    serviceId: UUID,
    viewModel: ServiceDetailViewModel = koinViewModel(
        parameters = { parametersOf(serviceId) },
    ),
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val model by viewModel.model.collectAsState()
    ServiceDetailScreen(
        modifier = modifier,
        model = model,
        onBackButtonClick = onBackClick,
        onOrderButtonClick = viewModel::onOrderButtonClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ServiceDetailScreen(
    model: ServiceDetailModel,
    onBackButtonClick: () -> Unit,
    onOrderButtonClick: () -> Unit,
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
                        text = model.data?.name.orEmpty(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = SkodaNext,
                        lineHeight = 24.sp,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background,
                    navigationIconContentColor = White,
                    titleContentColor = White,
                    actionIconContentColor = White,
                ),
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                if (model.data != null && (model.data.expiresIn == null || model.data.isSoonExpiration)) {
                    Button(
                        onClick = onOrderButtonClick,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green,
                            contentColor = Background,
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 24.dp,
                            vertical = 10.dp,
                        ),
                    ) {
                        Text(
                            text = stringResource(id = R.string.service_detail_button_order),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = SkodaNext,
                            lineHeight = 24.sp,
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(state = rememberScrollState()),
        ) {
            if (model.data != null) {
                Content(model.data)
            }
        }
    }
}

@Preview
@Composable
private fun ServiceDetailScreenPreview() {
    ServiceDetailScreen(
        model = ServiceDetailModel(
            data = ServiceDetailModel.Data(
                name = "Remote Access",
                description = "To use Connect LITE app, you need a DataPlug adapter. You can buy it at your ŠKODA dealer and insert into diagnostic board inside your vehicle. Then enjoy full digital connectivity of your ŠKODA.",
                bulletPoints = listOf(
                    "See and share your parking position",
                    "Record all your driving data",
                    "Manage your digital logbook",
                    "Control your vehicle’s service needs",
                    "Optimize your fuel costs",
                    "and much more.",
                ),
                type = Type.Active,
                expiresIn = "12/11/2024",
                isSoonExpiration = true,
            ),
        ),
        onBackButtonClick = {},
        onOrderButtonClick = {},
    )
}

@Composable
fun Content(
    model: ServiceDetailModel.Data,
) {
    Spacer(modifier = Modifier.height(height = 44.dp))

    Text(
        text = stringResource(id = R.string.service_detail_section_general_title),
        color = Color(0xFF8E8F90),
        fontSize = 20.sp,
        fontWeight = FontWeight.W700,
        fontFamily = SkodaNext,
        lineHeight = 24.sp,
    )

    Spacer(modifier = Modifier.height(height = 12.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
    ) {
        Box(
            modifier = Modifier.size(size = 24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(size = 10.dp)
                    .background(
                        color = model.type.color,
                        shape = CircleShape,
                    ),
            )
        }

        Column {
            Text(
                text = stringResource(id = model.type.label),
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
                fontFamily = SkodaNext,
                lineHeight = 24.sp,
            )

            if (model.expiresIn != null) {
                if (model.isSoonExpiration) {
                    Text(
                        text = stringResource(id = R.string.common_soon_expire, model.expiresIn),
                        color = OrangeDark,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SkodaNext,
                        lineHeight = 16.sp,
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.common_expires, model.expiresIn),
                        color = Color(0xFF8E8F90),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SkodaNext,
                        lineHeight = 16.sp,
                    )
                }
            }
        }
    }

    HorizontalDivider(color = Divider)

    Spacer(modifier = Modifier.height(height = 32.dp))

    if (Type.Active == model.type) {
        Text(
            text = stringResource(id = R.string.service_detail_section_personal_title),
            color = Color(0xFF8E8F90),
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            fontFamily = SkodaNext,
            lineHeight = 24.sp,
        )

        PersonalDataItem(
            label = "Time Saving",
            subLabel = "Total: 240 h / 2024",
            value = "58 h",
            date = "10/2024",
        )

        HorizontalDivider(color = Divider)

        PersonalDataItem(
            label = "Fuel Saving",
            subLabel = "Total: 159 l / 2024",
            value = "72 l",
            date = "10/2024",
        )

        HorizontalDivider(color = Divider)

        PersonalDataItem(
            label = "Emissions Reduction",
            subLabel = "Total: 180 kg CO₂ / 2024",
            value = "50 kg CO₂",
            date = "10/2024",
        )

        HorizontalDivider(color = Divider)
    }

    Spacer(modifier = Modifier.height(height = 32.dp))

    Text(
        text = stringResource(id = R.string.service_detail_section_description_title),
        color = White,
        fontSize = 20.sp,
        fontWeight = FontWeight.W700,
        fontFamily = SkodaNext,
        lineHeight = 24.sp,
    )

    Spacer(modifier = Modifier.height(height = 14.dp))

    model.bulletPoints.fastForEachIndexed { index, point ->
        if (index > 0) {
            Spacer(modifier = Modifier.height(height = 4.dp))
        }

        Text(
            text = "> $point",
            color = Gray,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            fontFamily = SkodaNext,
            lineHeight = 24.sp,
        )
    }

    Spacer(modifier = Modifier.height(height = 20.dp))

    Text(
        text = model.description,
        color = Gray,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        fontFamily = SkodaNext,
        lineHeight = 24.sp,
    )
}

@Composable
private fun PersonalDataItem(
    label: String,
    subLabel: String,
    value: String,
    date: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                bottom = 8.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(weight = 1f),
        ) {
            Text(
                text = label,
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
                fontFamily = SkodaNext,
                lineHeight = 24.sp,
            )

            Text(
                text = subLabel,
                color = Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                fontFamily = SkodaNext,
                lineHeight = 16.sp,
            )
        }

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Green)) {
                    append(value)
                }
                append(" / ")
                append(date)
            },
            color = White,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            fontFamily = SkodaNext,
            lineHeight = 20.sp,
        )
    }
}

@Preview
@Composable
private fun PersonalDataItemPreview() {
    PersonalDataItem(
        label = "Time Saving",
        subLabel = "Total: 240 h / 2024",
        value = "58 h",
        date = "10/2024",
    )
}
