package com.mikeschen.www.hangboardrepeaters.main

import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

object WorkoutPresetHandler {
    fun setupPresetButton(button: Button, hangEditText: EditText, pauseEditText: EditText, roundsEditText: EditText, restEditText: EditText, setsEditText: EditText, context: Context) {
        button.setOnClickListener {
            button.setScaleX(0.96f);
            button.setScaleY(0.96f);
            button.animate().scaleX(1F).scaleY(1F).start();
            hangEditText.setText("7")
            pauseEditText.setText("3")
            roundsEditText.setText("6")
            restEditText.setText("180")
            setsEditText.setText("5")
            Toast.makeText(context, "Repeaters", Toast.LENGTH_SHORT).show()
        }
    }

    fun setupPowerButton(button: Button, hangEditText: EditText, pauseEditText: EditText, roundsEditText: EditText, restEditText: EditText, setsEditText: EditText, context: Context) {
        button.setOnClickListener {
            button.setScaleX(0.96f);
            button.setScaleY(0.96f);
            button.animate().scaleX(1F).scaleY(1F).start();
            hangEditText.setText("10")
            pauseEditText.setText("180")
            roundsEditText.setText("5")
            restEditText.setText("1")
            setsEditText.setText("1")
            Toast.makeText(context, "Max Hangs", Toast.LENGTH_SHORT).show()
        }
    }
}
