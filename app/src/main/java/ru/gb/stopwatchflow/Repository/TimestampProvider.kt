package ru.gb.stopwatchflow.Repository

interface TimestampProvider {

    fun getMilliseconds(): Long
}