package com.mikeschen.hangboard_repeaters.main

import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText

class WorkoutSettingsManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    fun saveSettings(hang: String, pause: String, rounds: String, rest: String, sets: String) {
        sharedPreferences.edit()
            .putString(Constants.SAVED_USER_HANG, hang)
            .putString(Constants.SAVED_USER_PAUSE, pause)
            .putString(Constants.SAVED_USER_ROUNDS, rounds)
            .putString(Constants.SAVED_USER_REST, rest)
            .putString(Constants.SAVED_USER_SETS, sets)
            .apply()
    }

    fun loadCustomSettings(hangEditText: EditText, pauseEditText: EditText, roundsEditText: EditText, restEditText: EditText, setsEditText: EditText) {
        hangEditText.setText(sharedPreferences.getString(Constants.SAVED_USER_HANG, ""))
        pauseEditText.setText(sharedPreferences.getString(Constants.SAVED_USER_PAUSE, ""))
        roundsEditText.setText(sharedPreferences.getString(Constants.SAVED_USER_ROUNDS, ""))
        restEditText.setText(sharedPreferences.getString(Constants.SAVED_USER_REST, ""))
        setsEditText.setText(sharedPreferences.getString(Constants.SAVED_USER_SETS, ""))
    }
}