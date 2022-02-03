package ru.gb.stopwatchflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

import ru.gb.stopwatchflow.ViewModel.MainViewModel


class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by lazy {
       ViewModelProvider(this).get(MainViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val textView = findViewById<TextView>(R.id.text_time)
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        ).launch {
            viewModel.ticker.collect {
                textView.text = it
            }
        }

        findViewById<Button>(R.id.button_start).setOnClickListener {
            viewModel.start()
        }
        findViewById<Button>(R.id.button_pause).setOnClickListener {
            viewModel.pause()
        }
        findViewById<Button>(R.id.button_stop).setOnClickListener {
            viewModel.stop()
        }

    }
}
