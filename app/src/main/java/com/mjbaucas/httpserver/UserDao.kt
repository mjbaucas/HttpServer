package com.mjbaucas.httpserver

import java.util.concurrent.atomic.AtomicInteger

class UserDao {
    val users = hashMapOf(
        0 to User(uid = "0001", deviceName = "smartwatch01", publicKey = "212uhgfdsaweq345trhdfsfw45"),
        1 to User(uid = "0002", deviceName = "pacemaker045", publicKey = "afg45yhgaf34yhgfaert5tdedd")
    )

    private val lastId: AtomicInteger = AtomicInteger(users.size -1)

    fun save(uid: String, deviceName: String, publicKey: String) {
        val latest = lastId.incrementAndGet()
        users[latest] = User(uid = uid, deviceName = deviceName, publicKey = publicKey)
    }

    fun findByUID(uid: String, deviceName: String): Boolean {
        if (users.values.find { it.uid == uid && it.deviceName == deviceName} != null) {
            return true
        }
        return false
    }
}