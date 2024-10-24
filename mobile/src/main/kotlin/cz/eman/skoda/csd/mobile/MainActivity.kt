package cz.eman.skoda.csd.mobile

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cz.eman.skoda.csd.mobile.ui.theme.ConnectivityServicesDevelopmentTheme

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
                MainContainer()
            }
        }
    }
}
