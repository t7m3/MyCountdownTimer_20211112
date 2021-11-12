package com.example.mycountdowntimer_20211112

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.mycountdowntimer_20211112.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    inner class MyCountDownTimer(millisInFuture: Long , countDownInterval: Long ) : CountDownTimer(millisInFuture, countDownInterval ){
        var isRunning = false

        override  fun onTick(millisUntilFinished: Long){
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000L % 60L
            binding.timerText.text = "%1d:%2$02d".format(minute, second)
        }

        override fun onFinish() {
            binding.timerText.text = "0:00"
            //binding.timerText.text = "*:**"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.timerText.text = "3:00"

        //var timer = MyCountDownTimer(3*60*1000, 100)
        var timer = MyCountDownTimer(1*10*1000, 100)

        binding.playStop.setOnClickListener {
            timer.isRunning = when(timer.isRunning) {
                true -> {
                    timer.cancel()
                    binding.playStop.setImageResource(
                            R.drawable.ic_baseline_play_arrow_24
                    )
                    false
                }
                false -> {
                    timer.start()
                    binding.playStop.setImageResource(
                            R.drawable.ic_baseline_stop_24
                    )
                    true
                }
            }
        }

    }
}