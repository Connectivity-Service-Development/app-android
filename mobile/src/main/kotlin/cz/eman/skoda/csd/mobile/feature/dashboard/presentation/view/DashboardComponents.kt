package cz.eman.skoda.csd.mobile.feature.dashboard.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.eman.skoda.csd.mobile.R
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.RangeItem
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.ServicesItem
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.TemperatureItem
import cz.eman.skoda.csd.mobile.feature.dashboard.presentation.model.VehicleItem
import cz.eman.skoda.csd.shared.presentation.theme.Background
import cz.eman.skoda.csd.shared.presentation.theme.BackgroundGray
import cz.eman.skoda.csd.shared.presentation.theme.Divider
import cz.eman.skoda.csd.shared.presentation.theme.Gray
import cz.eman.skoda.csd.shared.presentation.theme.Green
import cz.eman.skoda.csd.shared.presentation.theme.Orange
import cz.eman.skoda.csd.shared.presentation.theme.SkodaNext
import cz.eman.skoda.csd.shared.presentation.theme.White

@Composable
internal fun VehicleCard(
    model: VehicleItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = BackgroundGray,
            contentColor = White,
        ),
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.dashboard_item_vehicle_label),
                color = Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontFamily = SkodaNext,
                lineHeight = 20.sp,
            )

            Spacer(modifier = Modifier.height(height = 8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock_closed),
                    contentDescription = null,
                    tint = Green,
                )

                Text(
                    text = stringResource(id = R.string.dashboard_item_vehicle_status),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = SkodaNext,
                    lineHeight = 24.sp,
                )
            }

            Spacer(modifier = Modifier.height(height = 10.dp))

            HorizontalDivider(color = Divider)

            Spacer(modifier = Modifier.height(height = 10.dp))

            Text(
                text = "Windows closed",
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontFamily = SkodaNext,
                lineHeight = 20.sp,
            )
        }
    }
}

@Preview
@Composable
private fun VehicleCardPreview() {
    VehicleCard(
        model = VehicleItem,
        onClick = {},
    )
}

@Composable
internal fun ServicesCard(
    model: ServicesItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = BackgroundGray,
            contentColor = White,
        ),
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.dashboard_item_services_label),
                color = Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontFamily = SkodaNext,
                lineHeight = 20.sp,
            )

            Spacer(modifier = Modifier.height(height = 8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_card),
                    contentDescription = null,
                    tint = Green,
                )

                Text(
                    text = stringResource(
                        id = R.string.dashboard_item_services_status_template,
                        model.active,
                        model.total,
                    ),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = SkodaNext,
                    lineHeight = 24.sp,
                )
            }

            Spacer(modifier = Modifier.height(height = 10.dp))

            if (model.expired > 0) {
                HorizontalDivider(color = Divider)

                Spacer(modifier = Modifier.height(height = 10.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Orange,
                        contentColor = Background,
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(all = 2.dp)
                                .sizeIn(
                                    minWidth = 20.dp,
                                    minHeight = 20.dp,
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = model.expired.toString(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W700,
                                fontFamily = SkodaNext,
                                lineHeight = 12.sp,
                            )
                        }
                    }

                    Text(
                        text = stringResource(id = R.string.dashboard_item_services_warning),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SkodaNext,
                        lineHeight = 20.sp,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ServicesCardPreview() {
    ServicesCard(
        model = ServicesItem(
            active = 5,
            total = 6,
            expired = 2,
        ),
        onClick = {},
    )
}

@Composable
internal fun RangeCard(
    model: RangeItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = BackgroundGray,
            contentColor = White,
        ),
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.dashboard_item_range_label),
                color = Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontFamily = SkodaNext,
                lineHeight = 20.sp,
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_fast_gage),
                    contentDescription = null,
                    tint = Color.White,
                )

                Text(
                    text = "20 % / 60 km",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = SkodaNext,
                    lineHeight = 24.sp,
                )

                Spacer(modifier = Modifier.weight(weight = 1f))

                Text(
                    modifier = Modifier.align(alignment = Alignment.Bottom),
                    text = stringResource(id = R.string.dashboard_item_range_status),
                    color = Color(0xFFC3C5C7),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = SkodaNext,
                    lineHeight = 20.sp,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RangeCardPreview() {
    RangeCard(
        model = RangeItem,
        onClick = {},
    )
}

@Composable
internal fun TemperatureCard(
    model: TemperatureItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = BackgroundGray,
            contentColor = White,
        ),
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.dashboard_item_temperature_label),
                color = Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontFamily = SkodaNext,
                lineHeight = 20.sp,
            )

            Spacer(modifier = Modifier.height(height = 16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "24 °C",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = SkodaNext,
                    lineHeight = 24.sp,
                )

                Spacer(modifier = Modifier.weight(weight = 1f))

                Text(
                    modifier = Modifier.align(alignment = Alignment.Bottom),
                    text = "Off",
                    color = Color(0xFFC3C5C7),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = SkodaNext,
                    lineHeight = 20.sp,
                )
            }
        }
    }
}

@Preview
@Composable
private fun TemperatureCardPreview() {
    TemperatureCard(
        model = TemperatureItem,
        onClick = {},
    )
}

@Composable
internal fun InfoBanner(
    modifier: Modifier = Modifier,
    onRemindButtonClick: () -> Unit,
    onDiscoverButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = BackgroundGray)
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_exclamation),
                contentDescription = null,
                tint = Orange,
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W700,
                        ),
                    ) {
                        append("You will soon lose access to your prepaid services.")
                    }
                    append(' ')
                    append("Please check the validity of your licenses.")
                },
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontFamily = SkodaNext,
                lineHeight = 20.sp,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.End,
            ),
        ) {
            TextButton(
                onClick = onRemindButtonClick,
            ) {
                Text(
                    text = "Remind",
                    color = Color(0xFFDADADA),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = SkodaNext,
                    lineHeight = 20.sp,
                )
            }

            TextButton(
                onClick = onDiscoverButtonClick,
            ) {
                Text(
                    text = "Extend Access",
                    color = Green,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = SkodaNext,
                    lineHeight = 20.sp,
                )
            }
        }
    }
}

@Preview
@Composable
private fun InfoBannerPreview() {
    InfoBanner(
        onRemindButtonClick = {},
        onDiscoverButtonClick = {},
    )
}
