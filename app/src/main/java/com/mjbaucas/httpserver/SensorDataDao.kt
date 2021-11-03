package com.mjbaucas.httpserver

import java.util.concurrent.atomic.AtomicInteger

class SensorDataDao {
    val sensorData = hashMapOf(
        0 to SensorData(uid = "0001", deviceName = "smartwatch01", data = 10.10, timestamp = "00000000"),
        1 to SensorData(uid = "0002", deviceName = "pacemaker045", data = 10.10, timestamp = "00000001")
    )

    private val lastId: AtomicInteger = AtomicInteger(sensorData.size -1)

    fun save(uid: String, deviceName: String, data: Double, timestamp: String) {
        val latest = lastId.incrementAndGet()
        sensorData[latest] = SensorData(uid = uid, deviceName = deviceName, data = data, timestamp = timestamp)
    }

    fun findByUID(uid: String): SensorData? {
        return sensorData.values.find { it.uid == uid}
    }
}