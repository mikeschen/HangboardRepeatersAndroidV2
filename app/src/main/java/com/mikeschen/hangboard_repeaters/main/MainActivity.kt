package com.mikeschen.hangboard_repeaters.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mikeschen.hangboard_repeaters.helpers.AlertDialogHelper
import com.mikeschen.hangboard_repeaters.helpers.MenuHelper
import com.mikeschen.hangboard_repeaters.R
import com.mikeschen.hangboard_repeaters.TimerActivity

class MainActivity : AppCompatActivity() {
    private val workoutSettingsManager by lazy {
        WorkoutSettingsManager(this)
    }

    private lateinit var mHangEditText: EditText
    private lateinit var mPauseEditText: EditText
    private lateinit var mRoundsEditText: EditText
    private lateinit var mRestEditText: EditText
    private lateinit var mSetsEditText: EditText

    private lateinit var menuHelper: MenuHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        menuHelper = MenuHelper(this)

        val mHangTimeTextView = findViewById<TextView>(R.id.hangTimeTextView)
        val mPauseTimeTextView = findViewById<TextView>(R.id.pauseTimeTextView)
        val mRestTimeTextView = findViewById<TextView>(R.id.restTimeTextView)
        val mRoundNumberTextView = findViewById<TextView>(R.id.roundNumberTextView)
        val mSetsTimeTextView = findViewById<TextView>(R.id.setsTimeTextView)
        val mHangEditText = findViewById<EditText>(R.id.hangEditText)
        val mPauseEditText = findViewById<EditText>(R.id.pauseEditText)
        val mRoundsEditText = findViewById<EditText>(R.id.roundsEditText)
        val mRestEditText = findViewById<EditText>(R.id.restEditText)
        val mSetsEditText = findViewById<EditText>(R.id.setsEditText)
        val mStartButton = findViewById<Button>(R.id.startButton)
        val mPresetButton = findViewById<Button>(R.id.presetButton)
        val mPowerButton = findViewById<Button>(R.id.powerButton)
        val mSaveButton = findViewById<Button>(R.id.saveButton)
        val mCustButton = findViewById<Button>(R.id.custButton)

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

        mStartButton.setOnClickListener {
            try {
                val hangTime = mHangEditText.text.toString().toInt()
                val pauseTime = mPauseEditText.text.toString().toInt()
                val rounds = mRoundsEditText.text.toString().toInt()
                val restTime = mRestEditText.text.toString().toInt()
                val sets = mSetsEditText.text.toString().toInt()

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
                mHangEditText, mPauseEditText,
                mRoundsEditText,
                mRestEditText,
                mSetsEditText
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