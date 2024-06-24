package moe.reimu.oneuicomplications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.edit
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.FileNotFoundException
import java.nio.charset.StandardCharsets

class WeatherReceiver : BroadcastReceiver() {
    val TAG = WeatherReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        val weatherJson = intent.getStringExtra("WeatherJson")
        if (weatherJson == null) {
            Log.e(TAG, "WeatherJson extra is null")
            return
        }

        context.openFileOutput("data.json", Context.MODE_PRIVATE).writer().use {
            it.write(weatherJson)
        }
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE).edit {
            putLong("lastUpdate", System.currentTimeMillis())
        }

        GlobalScope.launch(Dispatchers.IO) {
            MyAppWidget().updateAll(context)
        }
    }

    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        fun getWeatherData(context: Context): WeatherInfo? {
            return try {
                context.openFileInput("data.json").use {
                    val j = Json {
                        ignoreUnknownKeys = true
                    }
                    j.decodeFromStream(it)
                }
            } catch (e: FileNotFoundException) {
                null
            }
        }
    }
}