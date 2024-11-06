package com.mikeschen.hangboard_repeaters
import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

object AlertDialogHelper {
    fun showSaveDialog(context: Context, settingsManager: WorkoutSettingsManager, hangEditText: EditText, pauseEditText: EditText, roundsEditText: EditText, restEditText: EditText, setsEditText: EditText) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.saveworkouts)
            .setMessage(R.string.prefspopup)
            .setPositiveButton("OK") { _, _ ->
                settingsManager.saveSettings(
                    hangEditText.text.toString(),
                    pauseEditText.text.toString(),
                    roundsEditText.text.toString(),
                    restEditText.text.toString(),
                    setsEditText.text.toString()
                )
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
            .show()
    }
}