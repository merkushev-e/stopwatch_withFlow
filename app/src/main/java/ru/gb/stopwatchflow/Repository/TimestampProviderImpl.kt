package ru.gb.stopwatchflow.Repository

class TimestampProviderImpl: TimestampProvider {
    override fun getMilliseconds(): Long {
        return System.currentTimeMillis()
    }
}