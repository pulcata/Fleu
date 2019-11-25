package io.pulque.fleu.utils

import android.widget.Button

/*
 * @author savirdev on 2019-11-24
 */

fun Button.disable(){
    alpha = 0.5F
    isEnabled = false
    isClickable = false
}

fun Button.enable(){
    alpha = 1F
    isEnabled = true
    isClickable = true
}