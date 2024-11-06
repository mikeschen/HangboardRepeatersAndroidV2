package com.mikeschen.hangboard_repeaters

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

class TimerActivity : ComponentActivity() {
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
    var buttonchimeId: Int = 0
    var pausechimeId: Int = 0
    var restwarningId: Int = 0
    var endAlarmId: Int = 0
    var fivesecondsId: Int = 0

    //    private static final String TAG = "MyActivity";
    var ourSounds: SoundPool? = null

    var timerHandler: Handler = Handler()
    var timerRunnable: Runnable? = object : Runnable {
        override fun run() {
            val millis = System.currentTimeMillis() - startTime
            val seconds = (millis / 1000).toInt()
            val countdownDisplay = currentTimer - seconds
            val minutes = countdownDisplay / 60
            val secondsDisplay = countdownDisplay % 60
            //            Log.d(TAG, "run: " + countdownDisplay);
            if (countSoundSwitch) {
                if (countdownDisplay == 5) {
                    ourSounds!!.play(fivesecondsId, 0.8f, 0.8f, 1, 0, 1f)
                }
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
                        ourSounds!!.play(restwarningId, 0.8f, 0.8f, 1, 0, 1f)
                    } else if (flipState) {
                        ourSounds!!.play(pausechimeId, 0.8f, 0.8f, 1, 0, 1f)
                    } else {
                        ourSounds!!.play(buttonchimeId, 0.9f, 0.9f, 1, 0, 1f)
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
                    mRoundsText!!.text = "$roundCounter/$rounds"
                    roundCounter++
                }
                if (i == rounds * 2 - 1) {
                    roundCounter = 1
                    mRoundsText!!.text = "$roundCounter/$rounds"
                    currentTimer = rest
                    timerTextView = mRestText
                    timerText = mRestTextView
                    i = -1
                    mSetsText!!.text = "$counter/$sets"
                    counter++
                    flipState = false
                }
                if (counter - 2 == sets) {
                    timerHandler.removeCallbacks(this)
                    if (soundSwitch) {
                        ourSounds!!.play(endAlarmId, 0.7f, 0.7f, 1, 0, 1f)
                    }
                    mSetsText.setText(getString(R.string.done))
                    fade(mRoundTextView)
                    fade(mRoundsText)
                    mStartButton.setText(getString(R.string.logworkout))
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_timer)

        mHangTextView = findViewById(R.id.hangTextView)
        mPauseTextView = findViewById(R.id.pauseTextView)
        mRestTextView = findViewById(R.id.restTextView)
        mRoundTextView = findViewById(R.id.roundTextView)
        mSetsTextView = findViewById(R.id.setTextView)
        mHangText = findViewById(R.id.hangText)
        mPauseText = findViewById(R.id.pauseText)
        mRestText = findViewById(R.id.restText)
        mSetsText = findViewById(R.id.setsText)
        mRoundsText = findViewById(R.id.roundsText)
        mStartButton = findViewById(R.id.startButton)
//        mSoundButton = findViewById(R.id.soundButton)
//        mCountSoundButton = findViewById(R.id.countSoundButton)

//        mStartButton.setOnClickListener(this)
//        mSoundButton.setOnClickListener(this)
//        mCountSoundButton.setOnClickListener(this)

        fade(mHangTextView)
        fade(mPauseTextView)
        fade(mRestTextView)
        fade(mHangText)
        fade(mPauseText)
        fade(mRestText)

//        val intent = Intent.getIntent()
//        hang = intent.getIntExtra("hang", 0)
//        pause = intent.getIntExtra("pause", 0)
//        rounds = intent.getIntExtra("rounds", 0)
//        rest = intent.getIntExtra("rest", 0)
//        sets = intent.getIntExtra("sets", 0)
        //Mike TODO: see if I need to fix below
//        timerText = findViewById(R.id.hangTextView)
//        timerTextView = findViewById(R.id.hangText)
        currentTimer = hang
        mRoundsText.text = "1/$rounds"
        mSetsText.text = "1/$sets"
//        initializeSoundPool()
    }

//    private fun initializeSoundPool() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val audioAttributes = AudioAttributes.Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                .setUsage(AudioAttributes.USAGE_GAME)
//                .build()
//
//            ourSounds = SoundPool.Builder()
//                .setMaxStreams(4)
//                .setAudioAttributes(audioAttributes)
//                .build()
//
//            buttonchimeId = ourSounds.load(this, R.raw.hangchime, 1)
//            pausechimeId = ourSounds.load(this, R.raw.pausechime, 1)
//            restwarningId = ourSounds.load(this, R.raw.restchime, 1)
//            endAlarmId = ourSounds.load(this, R.raw.countdownchime, 1)
//            fivesecondsId = ourSounds.load(this, R.raw.fivesecondschime, 1)
//        } else {
//            ourSounds = SoundPool(3, AudioManager.STREAM_MUSIC, 1)
//            buttonchimeId = ourSounds!!.load(this, R.raw.hangchime, 1)
//            restwarningId = ourSounds!!.load(this, R.raw.restchime, 1)
//            endAlarmId = ourSounds!!.load(this, R.raw.countdownchime, 1)
//            fivesecondsId = ourSounds!!.load(this, R.raw.fivesecondschime, 1)
//        }
//    }

    private fun animateButton() {
        mStartButton.scaleX = 0.96f
        mStartButton.scaleY = 0.96f
        mStartButton.animate().scaleX(1f).scaleY(1f).start()
    }

    private fun fade(fadeText: TextView?) {
        fadeText!!.animate()
            .alpha(0.3f)
            .scaleX(0.8f)
            .scaleY(0.8f)
            .setDuration(500)
    }

//    fun onClick(v: View) {
//        when (v.id) {
//            (R.id.startButton) -> if (mStartButton!!.text == getString(R.string.logworkout)) {
//                val intent1: Intent = Intent(
//                    this,
//                    CreateLogActivity::class.java
//                )
//                intent1.putExtra("hang", hang)
//                intent1.putExtra("pause", pause)
//                intent1.putExtra("rounds", rounds)
//                intent1.putExtra("rest", rest)
//                intent1.putExtra("sets", sets)
//                this.startActivity(intent1)
//            } else {
//                animateButton()
//            }
//
//            (R.id.soundButton) -> if (soundSwitch) {
//                soundSwitch = false
//                mSoundButton!!.setImageResource(R.drawable.ic_volume_off_white_36dp)
//                val soundOff = Toast.makeText(
//                    getApplicationContext(),
//                    "Sound Off",
//                    Toast.LENGTH_SHORT
//                )
//
//                soundOff.show()
//            } else {
//                soundSwitch = true
//                mSoundButton!!.setImageResource(R.drawable.ic_volume_up_white_36dp)
//                val soundOn = Toast.makeText(
//                    getApplicationContext(),
//                    "Sound On",
//                    Toast.LENGTH_SHORT
//                )
//
//                soundOn.show()
//            }
//
//            (R.id.countSoundButton) -> if (countSoundSwitch) {
//                countSoundSwitch = false
//                mCountSoundButton!!.setImageResource(R.drawable.baseline_timer_off_white_36dp)
//                val cntSoundOff = Toast.makeText(
//                    getApplicationContext(),
//                    "Countdown Sounds Off",
//                    Toast.LENGTH_SHORT
//                )
//
//                cntSoundOff.show()
//            } else {
//                countSoundSwitch = true
//                mCountSoundButton!!.setImageResource(R.drawable.baseline_av_timer_white_36dp)
//                val cntSoundOn = Toast.makeText(
//                    getApplicationContext(),
//                    "Countdown Sounds On",
//                    Toast.LENGTH_SHORT
//                )
//
//                cntSoundOn.show()
//            }
//        }
//    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater: MenuInflater = getMenuInflater()
//        inflater.inflate(R.menu.menu_main, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_instruction -> {
//                val instructionIntent: Intent = Intent(
//                    this,
//                    InstructionActivity::class.java
//                )
//                this.startActivity(instructionIntent)
//            }
//
//            R.id.action_log -> {
//                val logIntent: Intent = Intent(
//                    this,
//                    LogActivity::class.java
//                )
//                this.startActivity(logIntent)
//            }
//
//            R.id.action_converter -> {
//                val converterIntent: Intent = Intent(
//                    this,
//                    ConverterActivity::class.java
//                )
//                this.startActivity(converterIntent)
//            }
//
//            R.id.action_tick -> {
//                val browserIntent =
//                    Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mikeschen.com/grypped"))
//                ContextCompat.startActivity(browserIntent)
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

    protected override fun onDestroy() {
        if (timerRunnable != null) timerHandler.removeCallbacks(timerRunnable!!)
        super.onDestroy()
        ourSounds!!.release()
    }

    fun startRunnable() {
        if (newWorkoutSwitch) {
            object : CountDownTimer(5000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    mStartButton.setText(getString(R.string.getready) + " " + millisUntilFinished / 1000)
                }

                override fun onFinish() {
                    if (soundSwitch) {
                        ourSounds!!.play(buttonchimeId, 0.9f, 0.9f, 1, 0, 1f)
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
}
