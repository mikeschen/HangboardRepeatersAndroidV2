package com.mikeschen.hangboard_repeaters.logging

class Days {
    var id: Long = 0
    var log: String? = null
        private set

    fun setComment(log: String?) {
        this.log = log
    }

    override fun toString(): String {
        return log!!
    }
}