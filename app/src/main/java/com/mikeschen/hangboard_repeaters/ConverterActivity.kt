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

    val hueco = Constants.hueco
    val huecoMenu = Constants.huecoMenu
    val fontMenu = Constants.fontMenu
    val font = Constants.font
    val yds = Constants.yds
    val french = Constants.french
    val uiaa = Constants.uiaa

    private var universalGrades: ArrayList<Int> = ArrayList()
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)
        mContext = this

        mGradeConverterTextView = findViewById(R.id.gradeConverterTextView)
        mHuecoButton = findViewById(R.id.huecoButton)
        mFontButton = findViewById(R.id.fontButton)
        mYdsButton = findViewById(R.id.ydsButton)
        mFrenchButton = findViewById(R.id.frenchButton)
        mUiaaButton = findViewById(R.id.uiaaButton)

        mHuecoButton.setOnClickListener(this)
        mFontButton.setOnClickListener(this)
        mYdsButton.setOnClickListener(this)
        mFrenchButton.setOnClickListener(this)
        mUiaaButton.setOnClickListener(this)
    }

    private fun gradeFinder(grade: String, gradeArray: Array<String>) {
        for (i in gradeArray.indices) {
            if (grade === gradeArray[i]) {
                universalGrades.add(i)
            }
        }
    }

    private fun animate(view: View) {
        view.scaleX = 0.96f
        view.scaleY = 0.96f
        view.animate().scaleX(1f).scaleY(1f).start()
    }

    override fun onClick(v: View) {
        when (v.id) {
            (R.id.huecoButton) -> {
                animate(mHuecoButton)
                gradeConverter(huecoMenu)
            }

            (R.id.fontButton) -> {
                animate(mFontButton)
                gradeConverter(fontMenu)
            }

            (R.id.ydsButton) -> {
                animate(mYdsButton)
                gradeConverter(yds)
            }

            (R.id.frenchButton) -> {
                animate(mFrenchButton)
                gradeConverter(french)
            }

            (R.id.uiaaButton) -> {
                animate(mUiaaButton)
                gradeConverter(uiaa)
            }
        }
    }

    private fun gradeConverter(gradeMenu: Array<String>) {
        val linearLayout = RelativeLayout(mContext)
        val mGradeNumberPicker = NumberPicker(mContext)
        mGradeNumberPicker.maxValue = gradeMenu.size - 1
        mGradeNumberPicker.minValue = 0
        mGradeNumberPicker.displayedValues = gradeMenu
        mGradeNumberPicker.wrapSelectorWheel = true
        mGradeNumberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            universalGrades.clear()
            if (gradeMenu.contentEquals(huecoMenu)) {
                mHuecoButton.text = "Hueco: " + huecoMenu[newVal]
                gradeFinder(huecoMenu[newVal], hueco)
                if (universalGrades.size == 1) {
                    mYdsButton.text = "Yds: " + yds[universalGrades[0]]
                    mFontButton.text = "Font: " + font[universalGrades[0]]
                    mFrenchButton.text = "French: " + french[universalGrades[0]]
                    mUiaaButton.text = "UIAA: " + uiaa[universalGrades[0]]
                } else {
                    mYdsButton.text =
                        "Yds: " + yds[universalGrades[0]] + " - " + yds[universalGrades[universalGrades.size - 1]]
                    mFontButton.text =
                        "Font: " + font[universalGrades[0]] + " - " + font[universalGrades[universalGrades.size - 1]]
                    mFrenchButton.text =
                        "French: " + french[universalGrades[0]] + " - " + french[universalGrades[universalGrades.size - 1]]
                    mUiaaButton.text = "UIAA: " + uiaa[universalGrades[0]]
                }
            } else if (gradeMenu.contentEquals(fontMenu)) {
                mFontButton.text = "Font: " + fontMenu[newVal]
                gradeFinder(fontMenu[newVal], font)
                if (universalGrades.size == 1) {
                    mYdsButton.text = "Yds: " + yds[universalGrades[0]]
                    mHuecoButton.text = "Hueco: " + hueco[universalGrades[0]]
                    mFrenchButton.text = "French: " + french[universalGrades[0]]
                    mUiaaButton.text = "UIAA: " + uiaa[universalGrades[0]]
                } else {
                    mYdsButton.text =
                        "Yds: " + yds[universalGrades[0]] + " - " + yds[universalGrades[universalGrades.size - 1]]
                    mHuecoButton.text =
                        "Hueco: " + hueco[universalGrades[0]] + " - " + hueco[universalGrades[universalGrades.size - 1]]
                    mFrenchButton.text =
                        "French: " + french[universalGrades[0]] + " - " + french[universalGrades[universalGrades.size - 1]]
                    mUiaaButton.text = "UIAA: " + uiaa[universalGrades[0]]
                }
            } else if (gradeMenu.contentEquals(yds)) {
                mYdsButton.text = "YDS: " + yds[newVal]
                gradeFinder(yds[newVal], yds)
                if (universalGrades.size == 1) {
                    mHuecoButton.text = "Hueco: " + hueco[universalGrades[0]]
                    mFontButton.text = "Font: " + font[universalGrades[0]]
                    mFrenchButton.text = "French: " + french[universalGrades[0]]
                    mUiaaButton.text = "UIAA: " + uiaa[universalGrades[0]]
                } else {
                    mHuecoButton.text =
                        "Hueco: " + hueco[universalGrades[0]] + " - " + hueco[universalGrades[universalGrades.size - 1]]
                    mFontButton.text =
                        "Font: " + font[universalGrades[0]] + " - " + font[universalGrades[universalGrades.size - 1]]
                    mFrenchButton.text =
                        "French: " + french[universalGrades[0]] + " - " + french[universalGrades[universalGrades.size - 1]]
                    mUiaaButton.text = "UIAA: " + uiaa[universalGrades[0]]
                }
            } else if (gradeMenu.contentEquals(uiaa)) {
                mUiaaButton.text = "UIAA: " + uiaa[newVal]
                gradeFinder(uiaa[newVal], uiaa)
                if (universalGrades.size == 1) {
                    mHuecoButton.text = "Hueco: " + hueco[universalGrades[0]]
                    mFontButton.text = "Font: " + font[universalGrades[0]]
                    mFrenchButton.text = "French: " + french[universalGrades[0]]
                    mYdsButton.text = "Yds: " + yds[universalGrades[0]]
                } else {
                    mHuecoButton.text =
                        "Hueco: " + hueco[universalGrades[0]] + " - " + hueco[universalGrades[universalGrades.size - 1]]
                    mFontButton.text =
                        "Font: " + font[universalGrades[0]] + " - " + font[universalGrades[universalGrades.size - 1]]
                    mFrenchButton.text =
                        "French: " + french[universalGrades[0]] + " - " + french[universalGrades[universalGrades.size - 1]]
                    mYdsButton.text =
                        "Yds: " + yds[universalGrades[0]] + " - " + yds[universalGrades[universalGrades.size - 1]]
                }
            } else {
                mFrenchButton.text = "French: " + french[newVal]
                gradeFinder(french[newVal], french)
                if (universalGrades.size == 1) {
                    mYdsButton.text = "Yds: " + yds[universalGrades[0]]
                    mFontButton.text = "Font: " + font[universalGrades[0]]
                    mHuecoButton.text = "Hueco: " + hueco[universalGrades[0]]
                    mUiaaButton.text = "UIAA: " + uiaa[universalGrades[0]]
                } else {
                    mYdsButton.text =
                        "Yds: " + yds[universalGrades[0]] + " - " + yds[universalGrades[universalGrades.size - 1]]
                    mFontButton.text =
                        "Font: " + font[universalGrades[0]] + " - " + font[universalGrades[universalGrades.size - 1]]
                    mHuecoButton.text =
                        "Hueco: " + hueco[universalGrades[0]] + " - " + hueco[universalGrades[universalGrades.size - 1]]
                    mUiaaButton.text = "UIAA: " + uiaa[universalGrades[0]]
                }
            }
        }

        val params = RelativeLayout.LayoutParams(gradeMenu.size - 1, gradeMenu.size - 1)
        val numPickerParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        numPickerParams.addRule(RelativeLayout.CENTER_HORIZONTAL)

        linearLayout.layoutParams = params
        linearLayout.addView(mGradeNumberPicker, numPickerParams)

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle(getString(R.string.grade))
        alertDialogBuilder.setView(linearLayout)
        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)
            ) { dialog, id -> }
            .setNegativeButton(getString(R.string.cancel)
            ) { dialog, id -> dialog.cancel() }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}