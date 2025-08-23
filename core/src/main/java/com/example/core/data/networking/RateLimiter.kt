package com.example.core.data.networking

object RateLimiter {
    private var lastRequestTime = 0L
    private val minInterval = 500L // 2 seconds

    fun canRequest(): Boolean {
        val now = System.currentTimeMillis()
        return if (now - lastRequestTime > minInterval) {
            lastRequestTime = now
            true
        } else {
            false
        }
    }
}
