package cz.eman.skoda.csd.automotive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cz.eman.skoda.csd.automotive.theme.ConnectivityServicesDevelopmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConnectivityServicesDevelopmentTheme(
                dynamicColor = false,
            ) {
                App()
            }
        }
    }
}
