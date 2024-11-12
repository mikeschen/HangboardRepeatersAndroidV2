package com.mikeschen.hangboard_repeaters

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

class SoundManager(context: Context) {
    private val ourSounds: SoundPool
    val buttonChimeId: Int
    val pauseChimeId: Int
    val restWarningId: Int
    val endAlarmId: Int
    val threeSecondsId: Int

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)  // Change USAGE_GAME to USAGE_MEDIA
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION) // Change MUSIC to SONIFICATION
            .build()
        ourSounds = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(audioAttributes)
            .build()

        buttonChimeId = ourSounds.load(context, R.raw.hangchime, 1)
        pauseChimeId = ourSounds.load(context, R.raw.pausechime, 1)
        restWarningId = ourSounds.load(context, R.raw.restchime, 1)
        endAlarmId = ourSounds.load(context, R.raw.countdownchime, 1)
        threeSecondsId = ourSounds.load(context, R.raw.threesecondscountdown, 1)

    }

    fun playSound(soundId: Int, volume: Float = 1.0f) {
        ourSounds.play(soundId, volume, volume, 1, 0, 1f)
    }

    fun release() {
        ourSounds.release()
    }
}
