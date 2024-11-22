package com.mikeschen.www.hangboardrepeaters.logging

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.mikeschen.www.hangboardrepeaters.R
import com.mikeschen.www.hangboardrepeaters.main.WorkoutSettingsManager
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

    private lateinit var workoutSettingsManager: WorkoutSettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_log)
        supportActionBar?.hide()

        // Initialize UI elements
        initUI()
        workoutSettingsManager = WorkoutSettingsManager(this)

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
        workoutSettingsManager.loadLogCustomSettings(
            mSizeEditText, mWeightEditText, mLbsButton, mKgButton
        )
    }

    private fun savePreferences() {
        workoutSettingsManager.saveLogSettings(
            mSizeEditText.text.toString(),
            mWeightEditText.text.toString(),
            mLbsButton.isChecked
        )
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.lbsButton -> {
                weightUnit = "lbs"
                savePreferences()
            }
            R.id.kgButton -> {
                weightUnit = "kg"
                savePreferences()
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
