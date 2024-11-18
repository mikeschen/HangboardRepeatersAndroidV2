package com.mikeschen.hangboard_repeaters

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.widget.NumberPicker
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mikeschen.hangboard_repeaters.helpers.Constants

class ConverterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mGradeConverterTextView: TextView
    private lateinit var mHuecoButton: TextView
    private lateinit var mFontButton: TextView
    private lateinit var mYdsButton: TextView
    private lateinit var mFrenchButton: TextView
    private lateinit var mUiaaButton: TextView

    private val gradeMapping = mapOf(
        GradeType.HUECO to Pair(Constants.hueco, Constants.huecoMenu),
        GradeType.FONT to Pair(Constants.font, Constants.fontMenu),
        GradeType.YDS to Pair(Constants.yds, Constants.yds),
        GradeType.FRENCH to Pair(Constants.french, Constants.french),
        GradeType.UIAA to Pair(Constants.uiaa, Constants.uiaa)
    )

    private val universalGrades = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        initViews()
        setButtonListeners()
    }

    private fun initViews() {
        mGradeConverterTextView = findViewById(R.id.gradeConverterTextView)
        mHuecoButton = findViewById(R.id.huecoButton)
        mFontButton = findViewById(R.id.fontButton)
        mYdsButton = findViewById(R.id.ydsButton)
        mFrenchButton = findViewById(R.id.frenchButton)
        mUiaaButton = findViewById(R.id.uiaaButton)
    }

    private fun setButtonListeners() {
        mHuecoButton.setOnClickListener(this)
        mFontButton.setOnClickListener(this)
        mYdsButton.setOnClickListener(this)
        mFrenchButton.setOnClickListener(this)
        mUiaaButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.huecoButton -> handleGradeConversion(GradeType.HUECO, mHuecoButton)
            R.id.fontButton -> handleGradeConversion(GradeType.FONT, mFontButton)
            R.id.ydsButton -> handleGradeConversion(GradeType.YDS, mYdsButton)
            R.id.frenchButton -> handleGradeConversion(GradeType.FRENCH, mFrenchButton)
            R.id.uiaaButton -> handleGradeConversion(GradeType.UIAA, mUiaaButton)
        }
    }

    private fun handleGradeConversion(gradeType: GradeType, button: TextView) {
        animate(button)
        val (gradeArray, gradeMenu) = gradeMapping[gradeType] ?: return
        showGradePicker(gradeType, gradeArray, gradeMenu)
    }

    private fun animate(view: View) {
        view.scaleX = 0.96f
        view.scaleY = 0.96f
        view.animate().scaleX(1f).scaleY(1f).start()
    }

    private fun showGradePicker(gradeType: GradeType, gradeArray: Array<String>, gradeMenu: Array<String>) {
        val numberPicker = createNumberPicker(gradeMenu)
        val dialogView = createDialogLayout(numberPicker)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.grade))
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> updateGrades(gradeType, numberPicker.value) }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun createNumberPicker(values: Array<String>): NumberPicker {
        return NumberPicker(this).apply {
            minValue = 0
            maxValue = values.size - 1
            displayedValues = values
            wrapSelectorWheel = true
        }
    }

    private fun createDialogLayout(numberPicker: NumberPicker): RelativeLayout {
        return RelativeLayout(this).apply {
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            params.addRule(RelativeLayout.CENTER_HORIZONTAL)
            addView(numberPicker, params)
        }
    }

    private fun updateGrades(gradeType: GradeType, selectedIndex: Int) {
        universalGrades.clear()
        val gradeMenu = gradeMapping[gradeType]?.second ?: return
        gradeFinder(gradeMenu[selectedIndex], gradeMapping[gradeType]?.first ?: return)

        // Update UI based on the universalGrades
        updateGradeText()
    }

    private fun gradeFinder(selectedGrade: String, gradeArray: Array<String>) {
        universalGrades.clear()
        gradeArray.forEachIndexed { index, grade ->
            if (grade == selectedGrade) universalGrades.add(index)
        }
    }

    private fun updateGradeText() {
        val displayValues = { array: Array<String>, indexes: List<Int> ->
            if (indexes.size == 1) array[indexes[0]] else "${array[indexes.first()]} - ${array[indexes.last()]}"
        }

        mHuecoButton.text = "Hueco: ${displayValues(Constants.hueco, universalGrades)}"
        mFontButton.text = "Font: ${displayValues(Constants.font, universalGrades)}"
        mYdsButton.text = "YDS: ${displayValues(Constants.yds, universalGrades)}"
        mFrenchButton.text = "French: ${displayValues(Constants.french, universalGrades)}"
        mUiaaButton.text = "UIAA: ${displayValues(Constants.uiaa, universalGrades)}"
    }

    enum class GradeType { HUECO, FONT, YDS, FRENCH, UIAA }
}
