package com.nghiatl.common.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log

private const val EXTRA_COUNTDOWN_TIME = "COUNTDOWNTIME"
private const val COUNTDOWN_INTERVAL = (1000 - 1).toLong()

class CountdownService : Service() {
    private var countDownTimer : CountDownTimer? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val action = intent.action
        if (ACTION_COUNTDOWN == action) {
            val countDownTime = intent.getLongExtra(EXTRA_COUNTDOWN_TIME, 0)
            handleActionCountdown(countDownTime)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

    /**
     * Bắt được thời gian gởi lên
     * @param countDownTime
     */
    private fun handleActionCountdown(countDownTime: Long) {
        Log.i(TAG, "Timer start...")
        countDownTimer = object : CountDownTimer(countDownTime, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                publishResult(millisUntilFinished) // gởi xuống client
            }

            override fun onFinish() {
                Log.i(TAG, "Timer finish...")
                publishResult(0)
            }
        }
        countDownTimer?.start()
    }

    /**
     * Trả kết quả về Client
     * @param millisUntilFinished thời gian còn lại
     */
    private fun publishResult(millisUntilFinished: Long) {
        val intent = Intent(ACTION_COUNTDOWN)
        intent.putExtra(EXTRA_MILLISECOND_UNTIL_FINISH, millisUntilFinished)
        sendBroadcast(intent)
    }

    companion object {
        const val EXTRA_MILLISECOND_UNTIL_FINISH = "MILLISECOND_UNTIL_FINISH"
        const val ACTION_COUNTDOWN = "COUNTDOWN"
        private val TAG = this::class.java.name

        /**
         * Bắt đầu service Countdown
         * @param context
         * @param countDownTime thời gian đếm ngược (milisecond)
         */
        fun startActionCountdown(context: Context, countDownTime: Long) {
            val intent = Intent(context, CountdownService::class.java)
            intent.action = ACTION_COUNTDOWN
            intent.putExtra(EXTRA_COUNTDOWN_TIME, countDownTime)
            context.startService(intent)
        }

        fun getResult(listener: (millisUntilFinished: Long) -> (Unit)): BroadcastReceiver {
            return object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val remainTime = intent?.getLongExtra(CountdownService.EXTRA_MILLISECOND_UNTIL_FINISH, 0) ?: 0
                    listener.invoke(remainTime)
                }

            }
        }
    }
}