package com.mikeschen.hangboard_repeaters.logging

class Days {
    var id: Long = 0
    private var log: String? = null

    fun setComment(log: String?) {
        this.log = log
    }

    override fun toString(): String {
        return log!!
    }
}