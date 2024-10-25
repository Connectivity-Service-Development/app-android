package cz.eman.skoda.csd.mobile

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cz.eman.skoda.csd.mobile.ui.theme.ConnectivityServicesDevelopmentTheme
import cz.eman.skoda.csd.shared.presentation.theme.Background

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )
        setContent {
            ConnectivityServicesDevelopmentTheme(
                dynamicColor = false,
            ) {
                Surface(
                    color = Background,
                    contentColor = androidx.compose.ui.graphics.Color.Unspecified,
                ) {
                    App(
                        modifier = Modifier.safeDrawingPadding(),
                    )
                }
            }
        }
    }
}
