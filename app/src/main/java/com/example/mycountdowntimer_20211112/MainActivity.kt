package com.example.mycountdowntimer_20211112

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.example.mycountdowntimer_20211112.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private lateinit var soundPool: SoundPool
    private var soundResId = 0

    private var step = 5  //アニメーション用の変数の宣言
    private var screenWidth = 0  //スクリーンの幅を格納する変数の宣言
    private var screenHeight = 0   //スクリーンの高さ格納する変数の宣言

    inner class MyCountDownTimer(millisInFuture: Long , countDownInterval: Long ) : CountDownTimer(millisInFuture, countDownInterval ){
        var isRunning = false

        override  fun onTick(millisUntilFinished: Long){
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000L % 60L
            binding.timerText.text = "%1d:%2$02d".format(minute, second)

            // imageViewEnemyを左右に移動する
            binding.imageViewEnemy.x = binding.imageViewEnemy.x + step

            if( binding.imageViewEnemy.x > screenWidth - binding.imageViewEnemy.width){  // 右端で移動する向きを左に変える
                step = -5
            }
            else if( binding.imageViewEnemy.x < 0){  // 左端で移動する向きを右に変える
                step = +5
            }
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
        var timer = MyCountDownTimer(10*60*1000, 10)  // ※インターバルを10ミリ秒に変更した

        // スクリーンの幅と高さを取得する
        val dMetrics = DisplayMetrics()  //DisplayMetrics のインスタンスを生成する
        windowManager.defaultDisplay.getMetrics(dMetrics)  //スクリーンサイズを取得しているらしい
        screenWidth = dMetrics.widthPixels  //スクリーンの幅を取得
        screenHeight = dMetrics.heightPixels  //スクリーンの高さを取得

        // imageViewEnemyの初期位置の設定
        binding.imageViewEnemy.x = 10F
        binding.imageViewEnemy.y = 100F

        // mageViewPlayer の初期位置の設定
        //binding.imageViewPlayer.x = 50F
        //binding.imageViewPlayer.y = screenHeight.toFloat() * 0.6F

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