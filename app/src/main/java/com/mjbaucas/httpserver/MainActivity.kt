package com.mjbaucas.httpserver

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.format.Formatter
import kotlinx.android.synthetic.main.activity_main.*
import io.javalin.Javalin


@Suppress("DEPRECATION", "DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val app = Javalin.create()
    private val sensorDataDao = SensorDataDao()
    private val userDao = UserDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val port = 3000

        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ipAddress: String = Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)

        try {
            app.start(ipAddress, port)
            app.get("/data/sensor") { ctx -> ctx.json(sensorDataDao.sensorData) }
            app.get("/data/user") { ctx -> ctx.json(userDao.users) }
            app.post("/send"){ctx ->
                val sensorData = ctx.bodyAsClass<SensorData>()
                if (userDao.findByUID(sensorData.uid, sensorData.deviceName)) {
                    sensorDataDao.save(uid = sensorData.uid, deviceName = sensorData.deviceName, data = sensorData.data, timestamp = sensorData.timestamp)
                    ctx.status(204)
                } else {
                    ctx.status(504)
                }
            }
        } catch (e: Exception) {

        }

        serverButton.setOnClickListener {
            serverText.text = ipAddress
        }
    }


}