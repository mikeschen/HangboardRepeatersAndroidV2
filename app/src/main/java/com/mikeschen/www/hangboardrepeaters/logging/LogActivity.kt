package com.mikeschen.www.hangboardrepeaters.logging

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.widget.TextView
import com.mikeschen.www.hangboardrepeaters.R
import com.mikeschen.www.hangboardrepeaters.main.MainActivity

class LogActivity : AppCompatActivity() {
    private var datasource: DaysDataSource? = null
    private lateinit var mCompletedTextView: TextView
    private var mContext: Context? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DaysAdapter
    private var recordCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        mContext = this
        mCompletedTextView = findViewById(R.id.completedTextView)
        val deleteButton = findViewById<View>(R.id.deleteButton)
        val homeButton = findViewById<View>(R.id.homeButton)
        recyclerView = findViewById(R.id.recyclerView)
        datasource = DaysDataSource(this)
        datasource?.open()
        recordCount = datasource?.count() ?: 0
        mCompletedTextView.text = getString(R.string.completedworkouts, "", recordCount)

        val values: MutableList<Days> = datasource?.allLogs?.toMutableList() ?: mutableListOf()
        values.reverse()
        adapter = DaysAdapter(this, values) { position -> deleteOneItem(position) }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        deleteButton.setOnClickListener {
            deleteButton(it)
        }

        homeButton.setOnClickListener {
            homeButton(it)
        }
    }

    private fun deleteOneItem(position: Int) {
        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.deleteworkout))
            .setMessage(getString(R.string.workoutpopup))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                val log = adapter.getDaysList()[position]
                datasource?.deleteLog(log)
                adapter.removeAt(position)
                recordCount = datasource?.count() ?: 0
                mCompletedTextView.text = getString(R.string.completedworkouts, "", recordCount)
            }
            .setNegativeButton(getString(R.string.cancel), null)

        builder.show()
    }

    private fun deleteButton(target: View?) {
        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.deleteworkouts))
            .setMessage(getString(R.string.workoutspopup))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                datasource?.deleteAllLogs()
                datasource?.close()
                refresh()
            }
            .setNegativeButton(getString(R.string.cancel), null)

        builder.show()
    }

    private fun homeButton(target: View?) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun refresh() {
        startActivity(Intent(this, LogActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        datasource?.close()
        super.onDestroy()
    }
}
