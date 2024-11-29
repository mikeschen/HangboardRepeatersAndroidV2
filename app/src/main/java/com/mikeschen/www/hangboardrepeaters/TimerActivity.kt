package com.mikeschen.www.hangboardrepeaters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mikeschen.www.hangboardrepeaters.helpers.MenuHelper
import com.mikeschen.www.hangboardrepeaters.helpers.SoundManager
import com.mikeschen.www.hangboardrepeaters.logging.CreateLogActivity

class TimerActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mHangTextView: TextView
    private lateinit var mPauseTextView: TextView
    private lateinit var mRestTextView: TextView
    private lateinit var mRoundTextView: TextView
    private lateinit var mSetsTextView: TextView
    private lateinit var mHangText: TextView
    private lateinit var mPauseText: TextView
    private lateinit var mRestText: TextView
    private lateinit var mSetsText: TextView
    private lateinit var mRoundsText: TextView
    private lateinit var mStartButton: Button
    private lateinit var mSoundButton: ImageButton
    private lateinit var mCountSoundButton: ImageButton
    private lateinit var menuHelper: MenuHelper
    private lateinit var soundManager: SoundManager

    var timerText: TextView? = null
    var timerTextView: TextView? = null
    var startTime: Long = 0
    var hang: Int = 0
    var pause: Int = 0
    var rounds: Int = 0
    var rest: Int = 0
    var sets: Int = 0
    var currentTimer: Int = 0
    var i: Int = 0
    var counter: Int = 2
    var roundCounter: Int = 2
    var newWorkoutSwitch: Boolean = true
    var flipState: Boolean = true
    var soundSwitch: Boolean = true
    var countSoundSwitch: Boolean = false
    private var hasPlayedSound = false

    var timerHandler: Handler = Handler(Looper.getMainLooper())

    var timerRunnable: Runnable? = object : Runnable {
        override fun run() {
            val millis = System.currentTimeMillis() - startTime
            val seconds = (millis / 1000).toInt()
            val countdownDisplay = currentTimer - seconds
            val minutes = countdownDisplay / 60
            val secondsDisplay = countdownDisplay % 60
            val countdownStart = 5;

            if (countSoundSwitch && countdownDisplay == countdownStart  && !hasPlayedSound) {
                soundManager.playSound(soundManager.fiveSecondsId, 0.9f)
                hasPlayedSound = true
            }
            if (countdownDisplay != countdownStart) {
                hasPlayedSound = false
            }
            if (seconds == currentTimer) {
                timerTextView!!.text = String.format("%d:%02d", 0, 0)
                fade(timerText)
                fade(timerTextView)
                timerHandler.postDelayed(this, 500)
                startTime = System.currentTimeMillis()
                i++
                if (soundSwitch) {
                    if (i == rounds * 2 - 1) {
                        soundManager.playSound(soundManager.restWarningId, 0.8f)
                    } else if (flipState) {
                        soundManager.playSound(soundManager.pauseChimeId, 0.8f)
                    } else {
                        soundManager.playSound(soundManager.buttonChimeId, 0.9f)
                    }
                }
                if (flipState) {
                    currentTimer = pause
                    timerText = mPauseTextView
                    timerTextView = mPauseText
                    flipState = false
                } else {
                    currentTimer = hang
                    timerText = mHangTextView
                    timerTextView = mHangText
                    flipState = true
                    mRoundsText.text = "$roundCounter/$rounds"
                    roundCounter++
                }
                if (i == rounds * 2 - 1) {
                    roundCounter = 1
                    mRoundsText.text = "$roundCounter/$rounds"
                    currentTimer = rest
                    timerTextView = mRestText
                    timerText = mRestTextView
                    i = -1
                    mSetsText.text = "$counter/$sets"
                    counter++
                    flipState = false
                }
                if (counter - 2 == sets) {
                    timerHandler.removeCallbacks(this)
                    if (soundSwitch) {
                        soundManager.playSound(soundManager.endAlarmId, 0.7f)
                    }
                    mSetsText.text = getString(R.string.done)
                    fade(mRoundTextView)
                    fade(mRoundsText)
                    mStartButton.text = getString(R.string.logworkout)
                }
            } else {
                timerText!!.animate()
                    .alpha(1f)
                    .scaleX(1.1f)
                    .scaleY(1.1f)
                timerTextView!!.animate()
                    .alpha(1f)
                    .scaleX(1.1f)
                    .scaleY(1.1f)
                timerTextView!!.text = String.format("%d:%02d", minutes, secondsDisplay)
                timerHandler.postDelayed(this, 500)
            }
        }
    }

    companion object {
        fun newIntent(context: Context, hang: Int, pause: Int, rounds: Int, rest: Int, sets: Int): Intent {
            return Intent(context, TimerActivity::class.java).apply {
                putExtra("hang", hang)
                putExtra("pause", pause)
                putExtra("rounds", rounds)
                putExtra("rest", rest)
                putExtra("sets", sets)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_timer)
        menuHelper = MenuHelper(this)
        soundManager = SoundManager(this)

        mHangTextView = findViewById(R.id.hangTextView)
        timerText = mHangTextView
        mHangText = findViewById(R.id.hangText)
        timerTextView = mHangText
        mPauseTextView = findViewById(R.id.pauseTextView)
        mRestTextView = findViewById(R.id.restTextView)
        mRoundTextView = findViewById(R.id.roundTextView)
        mSetsTextView = findViewById(R.id.setTextView)
        mPauseText = findViewById(R.id.pauseText)
        mRestText = findViewById(R.id.restText)
        mSetsText = findViewById(R.id.setsText)
        mRoundsText = findViewById(R.id.roundsText)
        mStartButton = findViewById(R.id.startButton)
        mSoundButton = findViewById(R.id.soundButton)
        mCountSoundButton = findViewById(R.id.countSoundButton)

        mStartButton.setOnClickListener(this)
        mSoundButton.setOnClickListener(this)
        mCountSoundButton.setOnClickListener(this)

        fade(mHangTextView)
        fade(mPauseTextView)
        fade(mRestTextView)
        fade(mHangText)
        fade(mPauseText)
        fade(mRestText)

        hang = intent.getIntExtra("hang", 0)
        pause = intent.getIntExtra("pause", 0)
        rounds = intent.getIntExtra("rounds", 0)
        rest = intent.getIntExtra("rest", 0)
        sets = intent.getIntExtra("sets", 0)
        //Mike TODO: see if I need to fix below
//        timerText = findViewById(R.id.hangTextView)
//        timerTextView = findViewById(R.id.hangText)
        currentTimer = hang
        mRoundsText.text = "1/$rounds"
        mSetsText.text = "1/$sets"
    }

    private fun animateButton() {
        mStartButton.scaleX = 0.96f
        mStartButton.scaleY = 0.96f
        mStartButton.animate().scaleX(1f).scaleY(1f).start()
    }

    private fun fade(fadeText: TextView?) {
        fadeText!!.animate()
            .alpha(0.4f)
            .scaleX(0.8f)
            .scaleY(0.8f)
            .setDuration(500)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.startButton -> {
                if (mStartButton.text == getString(R.string.logworkout)) {
                    val intent = Intent(this, CreateLogActivity::class.java)
                    intent.putExtra("hang", hang)
                    intent.putExtra("pause", pause)
                    intent.putExtra("rounds", rounds)
                    intent.putExtra("rest", rest)
                    intent.putExtra("sets", sets)
                    startActivity(intent)
                } else {
                    animateButton()
                    startRunnable()
                }
            }

            (R.id.soundButton) -> if (soundSwitch) {
                soundSwitch = false
                mSoundButton.setImageResource(R.drawable.ic_volume_off_white_36dp)
                val soundOff = Toast.makeText(
                    applicationContext,
                    "Sound Off",
                    Toast.LENGTH_SHORT
                )

                soundOff.show()
            } else {
                soundSwitch = true
                mSoundButton.setImageResource(R.drawable.ic_volume_up_white_36dp)
                val soundOn = Toast.makeText(
                    getApplicationContext(),
                    "Sound On",
                    Toast.LENGTH_SHORT
                )

                soundOn.show()
            }

            (R.id.countSoundButton) -> if (countSoundSwitch) {
                countSoundSwitch = false
                mCountSoundButton.setImageResource(R.drawable.baseline_timer_off_white_36dp)
                val cntSoundOff = Toast.makeText(
                    getApplicationContext(),
                    "Countdown Sounds Off",
                    Toast.LENGTH_SHORT
                )

                cntSoundOff.show()
            } else {
                countSoundSwitch = true
                mCountSoundButton.setImageResource(R.drawable.baseline_av_timer_white_36dp)
                val cntSoundOn = Toast.makeText(
                    getApplicationContext(),
                    "Countdown Sounds On",
                    Toast.LENGTH_SHORT
                )

                cntSoundOn.show()
            }
        }
    }

    override fun onDestroy() {
        if (timerRunnable != null) timerHandler.removeCallbacks(timerRunnable!!)
        super.onDestroy()
        soundManager.release()
    }

    fun startRunnable() {
        if (newWorkoutSwitch) {
            object : CountDownTimer(5000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    mStartButton.setText(getString(R.string.getready) + " " + millisUntilFinished / 1000)
                }

                override fun onFinish() {
                    if (soundSwitch) {
                        soundManager.playSound(soundManager.buttonChimeId, 0.9f)
                    }
                    startTime = System.currentTimeMillis()
                    timerHandler.postDelayed(timerRunnable!!, 0)
                    mStartButton.setText(getString(R.string.stop))
                    newWorkoutSwitch = false
                }
            }.start()
        }
        if (!newWorkoutSwitch) {
            if (mStartButton.text == getString(R.string.stop)) {
                timerHandler.removeCallbacks(timerRunnable!!)
                mStartButton.setText(getString(R.string.start))
            } else {
                startTime = System.currentTimeMillis()
                timerHandler.postDelayed(timerRunnable!!, 0)
                mStartButton.setText(getString(R.string.stop))
            }
        }
    }

    // Dropdown Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return menuHelper.createOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (menuHelper.handleOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
