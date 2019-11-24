package io.pulque.fleu.ui

import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity

/*
 * @author savirdev on 2019-11-24
 */

abstract class FleuBaseActivity : DaggerAppCompatActivity(){

    protected fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}