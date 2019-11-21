package io.pulque.fleu.utils

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import io.pulque.fleu.R

fun EditText.afterTextChangedDelayed(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        var timer : CountDownTimer? = null

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            timer?.cancel()
            timer = object : CountDownTimer(1000, 1500){
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    afterTextChanged.invoke(s.toString())
                }
            }.start()
        }
    })
}

fun EditText.setupClearButtonWithAction() {

    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            val clearIcon = if (editable?.isNotEmpty() == true) R.drawable.ic_clear_black_24dp else 0
            setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    })

    setOnTouchListener(View.OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                this.setText("")
                return@OnTouchListener true
            }
        }
        return@OnTouchListener false
    })
}
