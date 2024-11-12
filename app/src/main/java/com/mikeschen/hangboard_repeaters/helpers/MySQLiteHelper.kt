package com.mikeschen.hangboard_repeaters.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MySQLiteHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w(
            MySQLiteHelper::class.java.name,
            ("Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data")
        )
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS)
        onCreate(db)
    }

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(DATABASE_CREATE)
    }

    companion object {
        const val TABLE_LOGS: String = "logs"
        const val COLUMN_ID: String = "_id"
        const val COLUMN_LOG: String = "log"
        private const val DATABASE_NAME = "logs.db"
        private const val DATABASE_VERSION = 1

        private const val DATABASE_CREATE = ("create table "
                + TABLE_LOGS + "( " + COLUMN_ID
                + " integer primary key autoincrement, " + COLUMN_LOG
                + " text not null);")
    }
}