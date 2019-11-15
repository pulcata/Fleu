package io.pulque.fleu

import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import io.pulque.fleu.helpers.GeofencesHelper
import io.pulque.fleu.ui.ProfileActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var geofencesIntentHelper: GeofencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, ProfileActivity::class.java))
    }
}
