package io.pulque.fleu.ui

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.android.support.DaggerAppCompatActivity
import io.pulque.fleu.R
import kotlinx.android.synthetic.main.activity_pick_address.*
import javax.inject.Inject

const val DEFAULT_ZOOM = 17.0F
const val EXTRA_ADDRESS = "EXTRA_ADDRESS"
const val RESULT_EDIT = 10
const val RESULT_SAVE = 20

class PickAddressActivity : DaggerAppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    @Inject
    lateinit var locationProvider : FusedLocationProviderClient

    @Inject
    lateinit var geocoder: Geocoder

    private var lastCoordinates : LatLng? = null
    private var lastAddress : Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_address)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        address.setOnClickListener {
            setResult(RESULT_EDIT, getAddressData(lastAddress))
            finish()
        }

        save.setOnClickListener {
            setResult(RESULT_SAVE, getAddressData(lastAddress))
            finish()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        locationProvider.lastLocation.addOnSuccessListener {
            lastCoordinates = LatLng(it.latitude, it.longitude)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastCoordinates, DEFAULT_ZOOM))
            lastCoordinates?.let { coordinates ->
                updateAddressLine(coordinates)
            }
        }

        googleMap.setOnCameraIdleListener {
            lastCoordinates = googleMap.cameraPosition.target
            lastCoordinates?.let { coordinates ->
                updateAddressLine(coordinates)
            }

        }
    }

    private fun updateAddressLine(coordinates : LatLng){
        val locations = geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1)
        if (locations.isNotEmpty()){

            lastAddress = locations.first()
            lastAddress?.let { addressLine ->
                address.text = addressLine.getAddressLine(0)
            }
        }
    }

    private fun getAddressData(address: Address?) : Intent{
        val intent = Intent()
        intent.putExtra(EXTRA_ADDRESS, address)
        return intent
    }
}
