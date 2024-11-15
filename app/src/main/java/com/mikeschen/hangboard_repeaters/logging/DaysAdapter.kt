package com.mikeschen.hangboard_repeaters.logging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikeschen.hangboard_repeaters.R

class DaysAdapter(
    private val context: Context,
    private val daysList: MutableList<Days>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<DaysAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val deleteButton: View = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.white_text, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = daysList[position]
        holder.textView.text = day.toString()  // Adjust based on `Days` properties
        holder.deleteButton.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int = daysList.size

    fun removeAt(position: Int) {
        daysList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getDaysList(): List<Days> = daysList
}
