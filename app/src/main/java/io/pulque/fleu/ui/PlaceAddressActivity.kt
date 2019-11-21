package io.pulque.fleu.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerAppCompatActivity
import io.pulque.fleu.R
import io.pulque.fleu.di.ViewModelFactory
import io.pulque.fleu.ui.adapters.AddressAdapter
import io.pulque.fleu.utils.setupClearButtonWithAction
import io.pulque.fleu.viewmodel.PlaceAddressViewModel
import kotlinx.android.synthetic.main.activity_place_address.*
import javax.inject.Inject

private const val PERMISSIONS_REQUEST_LOCATION = 99
private const val REQUEST_MAP = 10

class PlaceAddressActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var geocoder: Geocoder

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var placeOrdersViewModel: PlaceAddressViewModel

    private var addressAdapter: AddressAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_address)

        subscribeAddressList()
        setupAddressInput()
        setupList()

    }

    private fun setupList() {

        addressAdapter = AddressAdapter(chooseOnMapListener = {
            if (checkForLocationPermissions()) {
                startActivityForResult(Intent(this, PickAddressActivity::class.java), REQUEST_MAP)
            }
        }, onAddressSelected = {
            val intent = Intent()
            intent.putExtra(EXTRA_ADDRESS, it)
            setResult(RESULT_SAVE, intent)
            finish()
        })

        addresses.apply {
            layoutManager =
                LinearLayoutManager(this@PlaceAddressActivity, RecyclerView.VERTICAL, false)
            adapter = addressAdapter
        }
    }

    private fun subscribeAddressList() {
        placeOrdersViewModel =
            ViewModelProviders.of(this, viewModelFactory)[PlaceAddressViewModel::class.java]

        placeOrdersViewModel.addressList.observe(this, Observer { addressList ->
            addressList?.let {
                addressAdapter?.addressList = it
            }
        })
    }

    private fun setupAddressInput() {
        address.setupClearButtonWithAction()
        address.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().isNotEmpty()) {
                    placeOrdersViewModel.fetchAddress(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun checkForLocationPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {

            AlertDialog.Builder(this)
                .setTitle(R.string.title_location_permission)
                .setMessage(R.string.text_location_permission)
                .setPositiveButton(R.string.ok) { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSIONS_REQUEST_LOCATION
                    )
                }
                .create()
                .show()


        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_LOCATION
            )
        }

        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.count() > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        startActivity(Intent(this, PickAddressActivity::class.java))
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_MAP) {
            when (resultCode) {
                RESULT_EDIT -> {
                    val tempAddress = data?.extras?.getParcelable(EXTRA_ADDRESS) as Address
                    address.setText(tempAddress.getAddressLine(0))
                    address.requestFocus()
                    address.setSelection(tempAddress.getAddressLine(0).count())
                }

                RESULT_SAVE -> {
                    setResult(RESULT_SAVE, data)
                    finish()
                }
            }
        }
    }
}
