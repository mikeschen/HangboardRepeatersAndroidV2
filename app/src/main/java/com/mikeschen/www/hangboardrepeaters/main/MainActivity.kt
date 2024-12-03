package com.mikeschen.www.hangboardrepeaters.main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mikeschen.www.hangboardrepeaters.helpers.AlertDialogHelper
import com.mikeschen.www.hangboardrepeaters.helpers.MenuHelper
import com.mikeschen.www.hangboardrepeaters.R
import com.mikeschen.www.hangboardrepeaters.TimerActivity
import com.mikeschen.www.hangboardrepeaters.helpers.Constants

class MainActivity : AppCompatActivity() {
    private val workoutSettingsManager by lazy {
        WorkoutSettingsManager(this)
    }

    private lateinit var mHangTimeTextView: TextView
    private lateinit var mPauseTimeTextView: TextView
    private lateinit var mRestTimeTextView: TextView
    private lateinit var mRoundNumberTextView: TextView
    private lateinit var mSetsTimeTextView: TextView
    private lateinit var mHangEditText: EditText
    private lateinit var mPauseEditText: EditText
    private lateinit var mRoundsEditText: EditText
    private lateinit var mRestEditText: EditText
    private lateinit var mSetsEditText: EditText
    private lateinit var mStartButton: Button
    private lateinit var mPresetButton: Button
    private lateinit var mPowerButton: Button
    private lateinit var mSaveButton: Button
    private lateinit var mCustButton: Button

    private lateinit var menuHelper: MenuHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        menuHelper = MenuHelper(this)

        mHangTimeTextView = findViewById(R.id.hangTimeTextView)
        mPauseTimeTextView = findViewById(R.id.pauseTimeTextView)
        mRestTimeTextView = findViewById(R.id.restTimeTextView)
        mRoundNumberTextView = findViewById(R.id.roundNumberTextView)
        mSetsTimeTextView = findViewById(R.id.setsTimeTextView)
        mHangEditText = findViewById(R.id.hangEditText)
        mPauseEditText = findViewById(R.id.pauseEditText)
        mRoundsEditText = findViewById(R.id.roundsEditText)
        mRestEditText = findViewById(R.id.restEditText)
        mSetsEditText = findViewById(R.id.setsEditText)
        mStartButton = findViewById(R.id.startButton)
        mPowerButton = findViewById(R.id.powerButton)
        mSaveButton = findViewById(R.id.saveButton)
        mCustButton = findViewById(R.id.custButton)
        mPresetButton = findViewById(R.id.presetButton)

        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString(Constants.SAVED_USER_NAME, "Custom")

        mCustButton.text = name

        WorkoutPresetHandler.setupPresetButton(
            mPresetButton,
            mHangEditText,
            mPauseEditText,
            mRoundsEditText,
            mRestEditText,
            mSetsEditText,
            this
        )

        WorkoutPresetHandler.setupPowerButton(
            mPowerButton,
            mHangEditText,
            mPauseEditText,
            mRoundsEditText,
            mRestEditText,
            mSetsEditText,
            this
        )

        // Load custom settings from SharedPreferences
        workoutSettingsManager.loadWorkoutParameters(
            mHangEditText,
            mPauseEditText,
            mRoundsEditText,
            mRestEditText,
            mSetsEditText
        )

        mStartButton.setOnClickListener {
            try {
                val hangTime = mHangEditText.text.toString().toInt()
                val pauseTime = mPauseEditText.text.toString().toInt()
                val rounds = mRoundsEditText.text.toString().toInt()
                val restTime = mRestEditText.text.toString().toInt()
                val sets = mSetsEditText.text.toString().toInt()

                workoutSettingsManager.saveWorkoutParameters(hangTime, pauseTime, rounds, restTime, sets)

                val intent = TimerActivity.newIntent(
                    this,
                    hangTime,
                    pauseTime,
                    rounds,
                    restTime,
                    sets
                )
                startActivity(intent)
            } catch (e: NumberFormatException) {
                mHangEditText.hint = "Set Sec."
                mPauseEditText.hint = "Set Sec."
                mRoundsEditText.hint = "Set Num."
                mRestEditText.hint = "Set Sec."
                mSetsEditText.hint = "Set Sets"
            }
        }

        mSaveButton.setOnClickListener {
            AlertDialogHelper.showSaveDialog(
                this,
                workoutSettingsManager,
                mHangEditText,
                mPauseEditText,
                mRoundsEditText,
                mRestEditText,
                mSetsEditText,
                mCustButton
            )
        }

        mCustButton.setOnClickListener {
            workoutSettingsManager.loadCustomSettings(
                mHangEditText,
                mPauseEditText,
                mRoundsEditText,
                mRestEditText,
                mSetsEditText
            )

            val custom = Toast.makeText(
                applicationContext,
                "Custom Workout Loaded",
                Toast.LENGTH_SHORT
            )
            custom.show()
        }
    }

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