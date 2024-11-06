package com.mikeschen.hangboard_repeaters

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    private val mSharedPreferences: SharedPreferences? by lazy {
        getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    private lateinit var mHangEditText: EditText
    private lateinit var mPauseEditText: EditText
    private lateinit var mRoundsEditText: EditText
    private lateinit var mRestEditText: EditText
    private lateinit var mSetsEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

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

        mPresetButton.setOnClickListener {
            Log.d(TAG, "MelloW!!!!! = world")
            mPresetButton.setScaleX(0.96f);
            mPresetButton.setScaleY(0.96f);
            mPresetButton.animate().scaleX(1F).scaleY(1F).start();
            mHangEditText.setText("7");
            mPauseEditText.setText("3");
            mRoundsEditText.setText("6");
            mRestEditText.setText("180");
            mSetsEditText.setText("5");
            val preset = Toast.makeText(
                applicationContext,
                "Repeaters",
                Toast.LENGTH_SHORT
            )

            preset.show()
        }

        mStartButton.setOnClickListener {
            try {
//                presenter.launchOtherActivityButtonClicked(TimerActivity::class.java)
            } catch (e: NumberFormatException) {
                mHangEditText.hint = "Set Sec."
                mPauseEditText.hint = "Set Sec."
                mRoundsEditText.hint = "Set Num."
                mRestEditText.hint = "Set Sec."
                mSetsEditText.hint = "Set Sets"
            }
        }

        mPowerButton.setOnClickListener {
            mPowerButton.setScaleX(0.96f);
            mPowerButton.setScaleY(0.96f);
            mPowerButton.animate().scaleX(1F).scaleY(1F).start();
            mHangEditText.setText("10")
            mPauseEditText.setText("180")
            mRoundsEditText.setText("5")
            mRestEditText.setText("1")
            mSetsEditText.setText("1")
            val power = Toast.makeText(
                applicationContext,
                "Max Hangs",
                Toast.LENGTH_SHORT
            )
            power.show()
        }

        mSaveButton.setOnClickListener {
            val editor = mSharedPreferences?.edit()

            showAlertDialog(this, mHangEditText, mPauseEditText, mRoundsEditText, mRestEditText, mSetsEditText)

            if (editor != null) {
                editor.apply()
            }

        }

        mCustButton.setOnClickListener {
            val hang = mSharedPreferences!!.getString(Constants.SAVED_USER_HANG, null)
            val pause = mSharedPreferences!!.getString(Constants.SAVED_USER_PAUSE, null)
            val rounds = mSharedPreferences!!.getString(Constants.SAVED_USER_ROUNDS, null)
            val rest = mSharedPreferences!!.getString(Constants.SAVED_USER_REST, null)
            val sets = mSharedPreferences!!.getString(Constants.SAVED_USER_SETS, null)

            // Update EditText fields with the retrieved values
            if (hang != null) {
                mHangEditText.setText(hang)
            }
            if (pause != null) {
                mPauseEditText.setText(pause)
            }
            if (rounds != null) {
                mRoundsEditText.setText(rounds)
            }
            if (rest != null) {
                mRestEditText.setText(rest)
            }
            if (sets != null) {
                mSetsEditText.setText(sets)
            }

            val custom = Toast.makeText(
                applicationContext,
                "Custom Workout Loaded",
                Toast.LENGTH_SHORT
            )
            custom.show()
        }
    }

    private fun showAlertDialog(
        context: Context,
        hangEditText: EditText,
        pauseEditText: EditText,
        roundsEditText: EditText,
        restEditText: EditText,
        setsEditText: EditText
    ) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(R.string.saveworkouts)
            .setMessage(R.string.prefspopup)
            .setPositiveButton("OK") { _, _ ->
                val editor = mSharedPreferences?.edit()
                editor?.putString(Constants.SAVED_USER_HANG, hangEditText.getText().toString())?.apply();
                editor?.putString(Constants.SAVED_USER_PAUSE, pauseEditText.getText().toString())?.apply();
                editor?.putString(Constants.SAVED_USER_ROUNDS, roundsEditText.getText().toString())?.apply();
                editor?.putString(Constants.SAVED_USER_REST, restEditText.getText().toString())?.apply();
                editor?.putString(Constants.SAVED_USER_SETS, setsEditText.getText().toString())?.apply();
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                // Handle the negative button action
            }

        val dialog = builder.create()
        dialog.show()
    }

}