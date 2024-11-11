package com.mikeschen.hangboard_repeaters.helpers

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.mikeschen.hangboard_repeaters.R

class MenuHelper(private val context: Context) {

    fun createOptionsMenu(menu: Menu?, inflater: MenuInflater): Boolean {
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun handleOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_instruction -> {
                // Uncomment and replace with actual intent if needed
                // val instructionIntent = Intent(context, InstructionActivity::class.java)
                // context.startActivity(instructionIntent)
                Toast.makeText(context, "Instructions selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_log -> {
                // Uncomment and replace with actual intent if needed
                // val logIntent = Intent(context, LogActivity::class.java)
                // context.startActivity(logIntent)
                Toast.makeText(context, "Log selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_converter -> {
                // Uncomment and replace with actual intent if needed
                // val converterIntent = Intent(context, ConverterActivity::class.java)
                // context.startActivity(converterIntent)
                Toast.makeText(context, "Converter selected", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }
    }
}
