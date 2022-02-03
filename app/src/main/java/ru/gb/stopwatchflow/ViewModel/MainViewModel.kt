package ru.gb.stopwatchflow.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.gb.stopwatchflow.Repository.*

class MainViewModel() : ViewModel() {

    private val stopwatchStateHolder: StopwatchStateHolder =  StopwatchStateHolder(
        StopwatchStateCalculator(
            TimestampProviderImpl(),
            ElapsedTimeCalculator(TimestampProviderImpl())
        ),
        ElapsedTimeCalculator(TimestampProviderImpl()),
        TimestampMillisecondsFormatter()
    )
    private var job: Job? = null
    private val mutableTicker = MutableStateFlow(DEFAULT_VALUE)
    val ticker: StateFlow<String> = mutableTicker

    fun start() {
        if (job == null) startJob()
        stopwatchStateHolder.start()
    }

    private fun startJob() {
        viewModelScope.launch {
            while (isActive) {
                mutableTicker.value = stopwatchStateHolder.getStringTimeRepresentation()
                delay(TIME_REFRESH)
            }
        }
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun stopJob() {
        viewModelScope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = DEFAULT_VALUE
    }

    companion object {
        private const val TIME_REFRESH = 20L
        private const val DEFAULT_VALUE = ""
    }
}