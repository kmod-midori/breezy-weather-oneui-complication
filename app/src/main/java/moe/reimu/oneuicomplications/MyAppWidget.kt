package moe.reimu.oneuicomplications

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle

class MyAppWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                MyContent(context)
            }
        }
    }

    @Composable
    private fun MyContent(context: Context) {
        val weatherInfo = WeatherReceiver.getWeatherData(context)

        Column(
            modifier = GlanceModifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (weatherInfo != null) {
                val iconId = weatherInfo.getWeatherIconResId()
                if (iconId != null) {
                    Image(
                        provider = ImageProvider(iconId),
                        contentDescription = "",
                        modifier = GlanceModifier.size(20.dp)
                    )
                }
                val temperature = weatherInfo.currentTempCelsius ?: 0
                Text(
                    "${temperature}°",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily("sec")
                    )
                )
            } else {
                Text("Unknown")
            }
        }
    }
}