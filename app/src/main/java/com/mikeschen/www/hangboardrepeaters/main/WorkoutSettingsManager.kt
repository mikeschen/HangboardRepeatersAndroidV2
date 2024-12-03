package com.mikeschen.www.hangboardrepeaters.main

import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText
import android.widget.RadioButton
import com.mikeschen.www.hangboardrepeaters.helpers.Constants
import java.util.Locale

class WorkoutSettingsManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    fun saveLogSettings(size: String, weight: String, isLbs: Boolean) {
        sharedPreferences.edit().apply {
            putString(Constants.KEY_USER_SIZE, size)
            putString(Constants.KEY_USER_WEIGHT, weight)
            putBoolean("Lbs", isLbs)
            putBoolean("Kg", !isLbs)
            apply()
        }
    }

    fun loadLogCustomSettings(
        sizeEditText: EditText,
        weightEditText: EditText,
        lbsButton: RadioButton,
        kgButton: RadioButton
    ) {
        sizeEditText.setText(sharedPreferences.getString(Constants.KEY_USER_SIZE, ""))
        weightEditText.setText(sharedPreferences.getString(Constants.KEY_USER_WEIGHT, ""))
        lbsButton.isChecked = sharedPreferences.getBoolean("Lbs", true)
        kgButton.isChecked = sharedPreferences.getBoolean("Kg", false)
    }

    fun saveWorkoutParameters(
        hangTime: Int,
        pauseTime: Int,
        rounds: Int,
        restTime: Int,
        sets: Int
    ) {
        sharedPreferences.edit().apply {
            putInt(Constants.KEY_USER_HANG, hangTime)
            putInt(Constants.KEY_USER_PAUSE, pauseTime)
            putInt(Constants.KEY_USER_ROUNDS, rounds)
            putInt(Constants.KEY_USER_REST, restTime)
            putInt(Constants.KEY_USER_SETS, sets)
            apply()
        }
    }

    fun loadWorkoutParameters(
        hangEditText: EditText,
        pauseEditText: EditText,
        roundsEditText: EditText,
        restEditText: EditText,
        setsEditText: EditText
    ) {
        hangEditText.setText(String.format(Locale.US, "%d", sharedPreferences.getInt(Constants.KEY_USER_HANG, 0)))
        pauseEditText.setText(String.format(Locale.US, "%d", sharedPreferences.getInt(Constants.KEY_USER_PAUSE, 0)))
        roundsEditText.setText(String.format(Locale.US, "%d", sharedPreferences.getInt(Constants.KEY_USER_ROUNDS, 0)))
        restEditText.setText(String.format(Locale.US, "%d", sharedPreferences.getInt(Constants.KEY_USER_REST, 0)))
        setsEditText.setText(String.format(Locale.US, "%d", sharedPreferences.getInt(Constants.KEY_USER_SETS, 0)))
    }

    fun saveSettings(name: String, hang: String, pause: String, rounds: String, rest: String, sets: String) {
        sharedPreferences.edit().apply {
            putString(Constants.SAVED_USER_NAME, name)
            putString(Constants.SAVED_USER_HANG, hang)
            putString(Constants.SAVED_USER_PAUSE, pause)
            putString(Constants.SAVED_USER_ROUNDS, rounds)
            putString(Constants.SAVED_USER_REST, rest)
            putString(Constants.SAVED_USER_SETS, sets)
            apply()
        }
    }

    fun loadCustomSettings(
        hangEditText: EditText,
        pauseEditText: EditText,
        roundsEditText: EditText,
        restEditText: EditText,
        setsEditText: EditText
    ) {
        hangEditText.setText(sharedPreferences.getString(Constants.SAVED_USER_HANG, ""))
        pauseEditText.setText(sharedPreferences.getString(Constants.SAVED_USER_PAUSE, ""))
        roundsEditText.setText(sharedPreferences.getString(Constants.SAVED_USER_ROUNDS, ""))
        restEditText.setText(sharedPreferences.getString(Constants.SAVED_USER_REST, ""))
        setsEditText.setText(sharedPreferences.getString(Constants.SAVED_USER_SETS, ""))
    }
}