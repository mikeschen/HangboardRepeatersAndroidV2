package com.mikeschen.hangboard_repeaters.logging

import DaysDataSource
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import com.mikeschen.hangboard_repeaters.R
import com.mikeschen.hangboard_repeaters.helpers.Constants

class CreateLogActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mSizeEditText: EditText
    private lateinit var mSizeTextView: TextView
    private lateinit var mWeightEditText: EditText
    private lateinit var mWeightTextView: TextView
    private lateinit var mNotesEditText: EditText
    private lateinit var mNotesTextView: TextView
    private lateinit var mLogButton: Button
    private lateinit var mLbsButton: RadioButton
    private lateinit var mKgButton: RadioButton

    var hang: Int = 0
    var pause: Int = 0
    var rounds: Int = 0
    var rest: Int = 0
    var sets: Int = 0
    private var datasource: DaysDataSource? = null
    var weightUnit: String = "lbs"
    var size: String? = null
    var weight: String? = null
    var sizeLog: String = ""
    var weightLog: String = ""
    var noteLog: String = ""
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var mSharedPreferencesEditor: SharedPreferences.Editor

    val tag = "Log tag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_log)

        mSizeEditText = findViewById(R.id.sizeEditText)
        mSizeTextView = findViewById(R.id.sizeTextView)
        mWeightEditText = findViewById(R.id.weightEditText)
        mWeightTextView = findViewById(R.id.weightTextView)
        mNotesEditText = findViewById(R.id.notesEditText)
        mNotesTextView = findViewById(R.id.notesTextView)
        mLogButton = findViewById(R.id.logButton)
        mLbsButton = findViewById(R.id.lbsButton)
        mKgButton = findViewById(R.id.kgButton)

        mSharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        mSharedPreferencesEditor = mSharedPreferences.edit()
        mLogButton.setOnClickListener(this)
        mLbsButton.setOnClickListener(this)
        mKgButton.setOnClickListener(this)
        hang = intent.getIntExtra("hang", 0)
        pause = intent.getIntExtra("pause", 0)
        rounds = intent.getIntExtra("rounds", 0)
        rest = intent.getIntExtra("rest", 0)
        sets = intent.getIntExtra("sets", 0)
        size = mSharedPreferences.getString(Constants.KEY_USER_SIZE, null)
        weight = mSharedPreferences.getString(Constants.KEY_USER_WEIGHT, null)
        if (size != null) {
            mSizeEditText!!.setText(size)
        }
        if (weight != null) {
            mWeightEditText!!.setText(weight)
        }
        loadRadioButtons()
    }

    override fun onClick(v: View) {
        when (v.id) {
            (R.id.lbsButton) -> {
                weightUnit = "lbs"
                saveRadioButtons()
            }

            (R.id.kgButton) -> {
                weightUnit = "kg"
                saveRadioButtons()
            }

            (R.id.logButton) -> {
                datasource = DaysDataSource(this@CreateLogActivity)
                datasource!!.open()
                val dateFormat: DateFormat = SimpleDateFormat("MM/dd/yyyy - hh:mm a -")
                val date = Date()
                val formattedDate = dateFormat.format(date)
                mSharedPreferencesEditor!!.putString(
                    Constants.KEY_USER_SIZE,
                    mSizeEditText!!.text.toString()
                ).apply()
                mSharedPreferencesEditor!!.putString(
                    Constants.KEY_USER_WEIGHT,
                    mWeightEditText!!.text.toString()
                ).apply()
                if (!mSizeEditText!!.text.toString().isEmpty()) {
                    sizeLog = " Hold Size: " + mSizeEditText!!.text.toString() + "mm,"
                }
                if (!mWeightEditText!!.text.toString().isEmpty()) {
                    weightLog = " Weight: " + mWeightEditText!!.text.toString() + weightUnit + ","
                }
                if (!mNotesEditText!!.text.toString().isEmpty()) {
                    noteLog = " - Notes: " + mNotesEditText!!.text.toString()
                }
                val workOutStats =
                    " Hang: $hang, Pause: $pause, Rounds: $rounds, Rest: $rest, Sets: $sets"
                val logs = formattedDate + sizeLog + weightLog + workOutStats + noteLog
                datasource!!.createLog(logs)
                Log.d(tag, "the text 🌝♀️: " + datasource)
                datasource!!.close()
//                val intent1 = Intent(
//                    this,
//                    LogActivity::class.java
//                )
//                this.startActivity(intent1)
            }
        }
    }

    fun saveRadioButtons() {
        mSharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        val editor = mSharedPreferences.edit()
        editor.putBoolean("Lbs", mLbsButton!!.isChecked)
        editor.putBoolean("Kg", mKgButton!!.isChecked)
        editor.apply()
    }

    fun loadRadioButtons() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        mLbsButton!!.isChecked = mSharedPreferences.getBoolean("Lbs", false)
        mKgButton!!.isChecked = mSharedPreferences.getBoolean("Kg", false)
    }
}
