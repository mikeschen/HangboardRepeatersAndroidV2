package com.mikeschen.www.hangboardrepeaters.helpers
import android.app.AlertDialog
import android.content.Context
import android.text.InputFilter
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.mikeschen.www.hangboardrepeaters.R
import com.mikeschen.www.hangboardrepeaters.main.WorkoutSettingsManager

object AlertDialogHelper {
    fun showSaveDialog(
        context: Context,
        settingsManager: WorkoutSettingsManager,
        hangEditText: EditText,
        pauseEditText: EditText,
        roundsEditText: EditText,
        restEditText: EditText,
        setsEditText: EditText,
        textViewToUpdate: TextView
    ) {
        val inputLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(46, 16, 46, 16)
        }

        val newTextField = EditText(context).apply {
            hint = "Enter custom workout name"
            filters = arrayOf(InputFilter.LengthFilter(18))
        }

        inputLayout.addView(newTextField)

        val builder = AlertDialog.Builder(context)
        val dialog = builder.setTitle(R.string.saveworkouts)
            .setMessage(R.string.prefspopup)
            .setView(inputLayout)
            .setPositiveButton("OK", null)
            .setNegativeButton(R.string.cancel, null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                var workoutName = newTextField.text.toString()

                if (workoutName.isBlank()) {
                    workoutName = "Custom"
                }

                settingsManager.saveSettings(
                    workoutName,
                    hangEditText.text.toString(),
                    pauseEditText.text.toString(),
                    roundsEditText.text.toString(),
                    restEditText.text.toString(),
                    setsEditText.text.toString()
                )

                textViewToUpdate.text = workoutName

                val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(newTextField.windowToken, 0)

                dialog.dismiss()
            }
        }

        dialog.show()
    }
}
