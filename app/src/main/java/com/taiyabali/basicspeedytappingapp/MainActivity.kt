package com.taiyabali.basicspeedytappingapp

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.taiyabali.basicspeedytappingapp.databinding.ActivityMainBinding
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Techpass Master
 * Website - https://techpassmaster.com/
 * Email id - hello@techpassmaster.com
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownTimer: CountDownTimer
    private val countDownTimerMillis: Long = 30000
    private var timeLeftMillis: Long = 0
    private var currentTaps: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            ivTapCircle.isEnabled = false

            ivTapCircle.setOnClickListener {

                currentTaps++                   // increment current tabs

                //  generate random color
                //  rgb (red green blue)
                //  alpha(a) use for color transparency (Value must be ≤ 255 (was 600))
                val rnd = Random()
                val color = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255))
                binding.container.setBackgroundColor(color)

                tvTaps.text=("Taps: $currentTaps")           //  set taps count

            }

            btnStartTapping.setOnClickListener {
                timeLeftMillis = countDownTimerMillis
                startTimer()
                btnStartTapping.visibility = View.GONE
                ivTapCircle.isEnabled = true
            }
        }

    }

    private fun startTimer() {
        // override object functions here, do it quicker by setting cursor on object, then type alt + enter ; implement members
        countDownTimer = object : CountDownTimer(timeLeftMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                timeLeftMillis = millisUntilFinished
                val second = TimeUnit.MILLISECONDS.toSeconds(timeLeftMillis).toInt()

                // %02d format the integer with 2 digit
                val timer = String.format(Locale.getDefault(), "Time: %02d", second)
                binding.tvTime.text = timer
            }

            override fun onFinish() {
                tapsScoreAlertDialog()
                currentTaps = 0
                countDownTimer.cancel()
                binding.ivTapCircle.isEnabled = false

            }
        }.start()
    }

    private fun tapsScoreAlertDialog() {
        val builder = AlertDialog.Builder(this)
        val view: View = LayoutInflater.from(this).inflate(R.layout.score_dialog, null)
        builder.setView(view)

        val tvTotalTaps: TextView = view.findViewById(R.id.tv_totalScore_result)
        val tvTryAgain: Button = view.findViewById(R.id.btn_tryAgain)

        tvTotalTaps.text = ("Total Taps: $currentTaps")

        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)

        tvTryAgain.setOnClickListener {
            alertDialog.dismiss()
            binding.btnStartTapping.visibility = View.VISIBLE
        }
        alertDialog.show()
    }
}