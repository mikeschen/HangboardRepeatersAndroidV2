//package com.mikeschen.hangboard_repeaters.logging
//
//import android.app.ListActivity
//import android.content.Context
//import android.content.DialogInterface
//import android.content.Intent
//import android.graphics.Typeface
//import android.os.Bundle
//import android.support.v7.app.AlertDialog
//import android.view.Menu
//import android.view.View
//import android.widget.AdapterView.OnItemClickListener
//import android.widget.ArrayAdapter
//import android.widget.ListView
//import android.widget.TextView
//import butterknife.BindView
//import butterknife.ButterKnife
//import com.mikeschen.hangboard_repeaters.R
//import com.mikeschen.hangboard_repeaters.main.MainActivity
//import com.mikeschen.www.hangboardrepeaters.DataSources.DaysDataSource
//
//class LogActivity : ListActivity() {
//    private var datasource: DaysDataSource? = null
//    var mContext: Context? = null
//    var recordCount: Int = 0
//
//    @BindView(R.id.completedTextView)
//    var mCompletedTextView: TextView? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_log)
//        ButterKnife.bind(this)
//        mContext = this
//        val custom_font = Typeface.createFromAsset(
//            assets, "BebasNeue.ttf"
//        )
//        mCompletedTextView!!.setTypeface(custom_font)
//        datasource = DaysDataSource(this)
//        datasource.open()
//        recordCount = datasource.count()
//        mCompletedTextView!!.text = getString(R.string.completedworkouts, "", recordCount)
//        val lv = listView
//        val values: List<Days> = datasource.getAllLogs()
//        val adapter: ArrayAdapter<Days> = ArrayAdapter<Any?>(this, R.layout.white_text, values)
//        lv.adapter = adapter
//        lv.onItemClickListener =
//            OnItemClickListener { l, v, position, id -> deleteOneButton(lv, position) }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.menu_main, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    fun deleteOneButton(lv: ListView, position: Int) {
//        val builder: AlertDialog.Builder = Builder(mContext)
//        builder.setTitle(getString(R.string.deleteworkout))
//        builder.setMessage(getString(R.string.workoutpopup))
//        builder.setPositiveButton(getString(R.string.ok),
//            DialogInterface.OnClickListener { dialog, which ->
//                val adapter: ArrayAdapter<Days> = lv.adapter as ArrayAdapter<Days>
//                if (lv.adapter.count > 0) {
//                    val log: Days = lv.adapter.getItem(position) as Days
//                    datasource.deleteLog(log)
//                    adapter.remove(log)
//                    recordCount = datasource.count()
//                    mCompletedTextView!!.text =
//                        getString(R.string.completedworkouts, "", recordCount)
//                }
//                adapter.notifyDataSetChanged()
//            })
//
//        builder.setNegativeButton(getString(R.string.cancel),
//            DialogInterface.OnClickListener { dialog, which -> })
//        builder.show()
//    }
//
//    fun deleteButton(target: View?) {
//        val builder: AlertDialog.Builder = Builder(mContext)
//        builder.setTitle(getString(R.string.deleteworkouts))
//        builder.setMessage(getString(R.string.workoutspopup))
//        builder.setPositiveButton(getString(R.string.ok),
//            DialogInterface.OnClickListener { dialog, which ->
//                datasource.deleteAllLogs()
//                datasource.close()
//                refresh()
//            })
//
//        builder.setNegativeButton(getString(R.string.cancel),
//            DialogInterface.OnClickListener { dialog, which -> })
//        builder.show()
//    }
//
//    fun homeButton(target: View?) {
//        startActivity(Intent(this@LogActivity, MainActivity::class.java))
//    }
//
//    fun refresh() {
//        val intent = Intent(
//            this@LogActivity,
//            LogActivity::class.java
//        )
//        startActivity(intent)
//        finish()
//    }
//
//    override fun onDestroy() {
//        datasource.close()
//        super.onDestroy()
//    }
//}
