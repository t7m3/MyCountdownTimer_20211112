package com.example.mycountdowntimer_20211112

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.mycountdowntimer_20211112.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private lateinit var soundPool: SoundPool
    private var soundResId = 0

    inner class MyCountDownTimer(millisInFuture: Long , countDownInterval: Long ) : CountDownTimer(millisInFuture, countDownInterval ){
        var isRunning = false

        override  fun onTick(millisUntilFinished: Long){
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000L % 60L
            binding.timerText.text = "%1d:%2$02d".format(minute, second)
        }

        override fun onFinish() {
            binding.timerText.text = "0:00"
            //binding.timerText.text = "＊:＊＊"
            soundPool.play(soundResId, 1.0f, 1.0f, 0, 0, 1.0f)
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

    override fun onResume() {
        super.onResume()

        soundPool =
                SoundPool.Builder().run {
                    val audioAttributes = AudioAttributes.Builder().run {
                        setUsage(AudioAttributes.USAGE_ALARM)
                        build()
                    }
                    setMaxStreams(1)
                    setAudioAttributes(audioAttributes)
                    build()
                }
        soundResId = soundPool.load(this, R.raw.bellsound, 1)
    }

    override fun onPause() {
        super.onPause()

        soundPool.release()
    }
}