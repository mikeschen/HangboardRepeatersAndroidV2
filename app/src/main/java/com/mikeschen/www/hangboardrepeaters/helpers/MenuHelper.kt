package com.mikeschen.www.hangboardrepeaters.helpers

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.mikeschen.www.hangboardrepeaters.R
import com.mikeschen.www.hangboardrepeaters.InstructionActivity
import com.mikeschen.www.hangboardrepeaters.logging.LogActivity
import com.mikeschen.www.hangboardrepeaters.ConverterActivity

class MenuHelper(private val context: Context) {

    fun createOptionsMenu(menu: Menu?, inflater: MenuInflater): Boolean {
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun handleOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_instruction -> {
                 val instructionIntent = Intent(context, InstructionActivity::class.java)
                 context.startActivity(instructionIntent)
                true
            }
            R.id.action_log -> {
                 val logIntent = Intent(context, LogActivity::class.java)
                 context.startActivity(logIntent)
                true
            }
            R.id.action_converter -> {
                 val converterIntent = Intent(context, ConverterActivity::class.java)
                 context.startActivity(converterIntent)
                true
            }
            else -> false
        }
    }
}
