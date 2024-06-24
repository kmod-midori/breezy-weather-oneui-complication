package moe.reimu.oneuicomplications

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@Serializable
data class WeatherInfo(
    val timestamp: Long,
    val location: String? = null,
    val currentTemp: Double? = null,
    /**
     * According to the spec this can be any OWM weather code (see https://openweathermap.org/weather-conditions),
     * but in reality, only the following codes are ever used:
     * 800, 801, 803, 500, 600, 771, 741, 751, 611, 511, 210, 211, 3200
     * (see https://github.com/breezy-weather/breezy-weather/blob/main/app/src/main/java/org/breezyweather/sources/gadgetbridge/GadgetbridgeService.kt#L37)
     */
    val currentConditionCode: Int? = null,
    val currentCondition: String? = null,
    val currentHumidity: Int? = null,
    val todayMaxTemp: Int? = null,
    val todayMinTemp: Int? = null,
    val windSpeed: Float? = null,
    val windDirection: Int? = null,
    val uvIndex: Float? = null,
    val precipProbability: Int? = null,
    val dewPoint: Int? = null,
    val pressure: Float? = null,
    val cloudCover: Int? = null,
    val visibility: Float? = null,
    val sunRise: Long? = null,
    val sunSet: Long? = null,
    val moonRise: Int? = null,
    val moonSet: Int? = null,
    val moonPhase: Int? = null,
    val feelsLikeTemp: Int? = null,
) {
    @DrawableRes
    fun getWeatherIconResId(): Int? = when (currentConditionCode) {
        800 -> if (isNight) {
            R.drawable.weather_clear_night
        } else {
            R.drawable.weather_clear_day
        }

        801 -> if (isNight) {
            R.drawable.weather_partly_cloudy_night
        } else {
            R.drawable.weather_partly_cloudy_day
        }

        803 -> R.drawable.weather_cloudy
        500 -> R.drawable.weather_rain
        600 -> R.drawable.weather_snow
        771 -> R.drawable.weather_wind
        741 -> R.drawable.weather_fog
        751 -> R.drawable.weather_haze
        611 -> R.drawable.weather_sleet
        511 -> R.drawable.weather_hail
        210 -> R.drawable.weather_thunder
        211 -> R.drawable.weather_thunderstorm
        else -> null
    }

    val currentTempCelsius = currentTemp?.minus(273.15)?.roundToInt()

    private val isNight = (sunRise != null && timestamp < sunRise) || (sunSet != null && timestamp > sunSet)
}
