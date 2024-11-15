package com.mikeschen.hangboard_repeaters.logging

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.mikeschen.hangboard_repeaters.helpers.MySQLiteHelper

class DaysDataSource(context: Context?) {
    private var database: SQLiteDatabase? = null
    private val dbHelper = MySQLiteHelper(context)
    private val allColumns = arrayOf(
        MySQLiteHelper.COLUMN_ID,
        MySQLiteHelper.COLUMN_LOG
    )

    @Throws(SQLException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    // Create a new log entry and return the created Days object
    fun createLog(log: String?): Days {
        val values = ContentValues()
        values.put(MySQLiteHelper.COLUMN_LOG, log)
        val insertId = database!!.insert(MySQLiteHelper.TABLE_LOGS, null, values)

        val cursor = database!!.query(
            MySQLiteHelper.TABLE_LOGS,
            allColumns,
            "${MySQLiteHelper.COLUMN_ID} = ?",
            arrayOf(insertId.toString()),
            null, null, null
        )

        cursor.moveToFirst()
        val newLog = cursorToLog(cursor)
        cursor.close()
        return newLog
    }

    // Delete a specific log entry
    fun deleteLog(log: Days) {
        val id = log.id
        println("Log deleted with id: $id")
        database!!.delete(
            MySQLiteHelper.TABLE_LOGS,
            "${MySQLiteHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    // Delete all logs
    fun deleteAllLogs() {
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM ${MySQLiteHelper.TABLE_LOGS}")
    }

    // Retrieve all logs from the database
    val allLogs: List<Days>
        get() {
            val logs: MutableList<Days> = ArrayList()
            val cursor = database!!.query(
                MySQLiteHelper.TABLE_LOGS,
                allColumns, null, null, null, null, null
            )

            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val log = cursorToLog(cursor)
                logs.add(log)
                cursor.moveToNext()
            }
            cursor.close()
            return logs
        }

    // Count the number of logs in the database
    fun count(): Int {
        val sql = "SELECT * FROM ${MySQLiteHelper.TABLE_LOGS}"
        val recordCount = database!!.rawQuery(sql, null).count
        return recordCount
    }

    // Convert cursor data into a Days object
    private fun cursorToLog(cursor: Cursor): Days {
        val log = Days()
        log.id = cursor.getLong(0) // Get ID (long) from the cursor at index 0
        log.setComment(cursor.getString(1)) // Use setComment to set the log string
        return log
    }
}

