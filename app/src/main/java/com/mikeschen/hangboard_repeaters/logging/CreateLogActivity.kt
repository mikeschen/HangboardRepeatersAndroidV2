package com.mikeschen.hangboard_repeaters.logging

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.mikeschen.hangboard_repeaters.R
import com.mikeschen.hangboard_repeaters.helpers.Constants
import java.text.SimpleDateFormat
import java.util.*

class CreateLogActivity : AppCompatActivity(), View.OnClickListener {

    // UI Elements
    private lateinit var mSizeEditText: EditText
    private lateinit var mWeightEditText: EditText
    private lateinit var mNotesEditText: EditText
    private lateinit var mLogButton: Button
    private lateinit var mLbsButton: RadioButton
    private lateinit var mKgButton: RadioButton

    // Workout Parameters
    private var hang: Int = 0
    private var pause: Int = 0
    private var rounds: Int = 0
    private var rest: Int = 0
    private var sets: Int = 0

    private var weightUnit: String = "lbs"

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_log)

        // Initialize UI elements
        initUI()
        loadWorkoutParameters()
        loadPreferences()

        mLogButton.setOnClickListener(this)
        mLbsButton.setOnClickListener(this)
        mKgButton.setOnClickListener(this)
    }

    private fun initUI() {
        mSizeEditText = findViewById(R.id.sizeEditText)
        mWeightEditText = findViewById(R.id.weightEditText)
        mNotesEditText = findViewById(R.id.notesEditText)
        mLogButton = findViewById(R.id.logButton)
        mLbsButton = findViewById(R.id.lbsButton)
        mKgButton = findViewById(R.id.kgButton)
    }

    private fun loadWorkoutParameters() {
        hang = intent.getIntExtra("hang", 0)
        pause = intent.getIntExtra("pause", 0)
        rounds = intent.getIntExtra("rounds", 0)
        rest = intent.getIntExtra("rest", 0)
        sets = intent.getIntExtra("sets", 0)
    }

    private fun loadPreferences() {
        sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        mSizeEditText.setText(sharedPreferences.getString(Constants.KEY_USER_SIZE, ""))
        mWeightEditText.setText(sharedPreferences.getString(Constants.KEY_USER_WEIGHT, ""))
        loadRadioButtonState()
    }

    private fun savePreferences() {
        sharedPreferences.edit().apply {
            putString(Constants.KEY_USER_SIZE, mSizeEditText.text.toString())
            putString(Constants.KEY_USER_WEIGHT, mWeightEditText.text.toString())
            apply()
        }
    }

    private fun loadRadioButtonState() {
        mLbsButton.isChecked = sharedPreferences.getBoolean("Lbs", true)
        mKgButton.isChecked = sharedPreferences.getBoolean("Kg", false)
    }

    private fun saveRadioButtonState() {
        sharedPreferences.edit().apply {
            putBoolean("Lbs", mLbsButton.isChecked)
            putBoolean("Kg", mKgButton.isChecked)
            apply()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.lbsButton -> {
                weightUnit = "lbs"
                saveRadioButtonState()
            }
            R.id.kgButton -> {
                weightUnit = "kg"
                saveRadioButtonState()
            }
            R.id.logButton -> handleLogCreation()
        }
    }

    private fun handleLogCreation() {
        savePreferences()

        val logs = generateLog()
        DaysDataSource(this).apply {
            open()
            createLog(logs)
            close()
        }

        startActivity(Intent(this, LogActivity::class.java))
    }

    private fun generateLog(): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy - hh:mm a -", Locale.getDefault())
        val date = dateFormat.format(Date())

        val sizeLog = if (!mSizeEditText.text.isNullOrBlank()) {
            " Hold Size: ${mSizeEditText.text}mm,"
        } else ""

        val weightLog = if (!mWeightEditText.text.isNullOrBlank()) {
            " Weight: ${mWeightEditText.text}$weightUnit,"
        } else ""

        val noteLog = if (!mNotesEditText.text.isNullOrBlank()) {
            " - Notes: ${mNotesEditText.text}"
        } else ""

        val workoutStats = " Hang: $hang, Pause: $pause, Rounds: $rounds, Rest: $rest, Sets: $sets"
        return "$date$sizeLog$weightLog$workoutStats$noteLog"
    }
}
