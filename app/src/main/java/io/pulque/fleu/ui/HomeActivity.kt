package io.pulque.fleu.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.pulque.fleu.R
import io.pulque.fleu.di.ViewModelFactory
import io.pulque.fleu.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : FleuBaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var homeViewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeViewModel = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]

        setupAddPlaceButton()
        subscribeUserInfo()
        subscribeErrors()
    }

    private fun setupAddPlaceButton(){
        addPlace.setOnClickListener {
            startActivity(Intent(this, PlaceAddressActivity::class.java))
        }
    }

    private fun subscribeUserInfo(){
        homeViewModel?.userInfo?.observe(this, Observer {
            Glide.with(this)
                .load(it.picture)
                .apply(RequestOptions.circleCropTransform())
                .into(profileImage)

            username.text = getString(R.string.username_template, it.username)
            name.text = it.name

            if (it.places.isNotEmpty()){
                place.visibility = View.VISIBLE
                place.text = it.places.first().nickname
            }else{
                addPlace.visibility = View.VISIBLE
            }
        })

        homeViewModel?.getUserInfo()
    }

    private fun subscribeErrors(){
        homeViewModel?.error?.observe(this, Observer {

        })
    }
}
