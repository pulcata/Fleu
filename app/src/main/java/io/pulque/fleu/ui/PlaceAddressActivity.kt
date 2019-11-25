package io.pulque.fleu.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.pulque.fleu.R
import io.pulque.fleu.di.ViewModelFactory
import io.pulque.fleu.ui.adapters.AddressAdapter
import io.pulque.fleu.utils.disable
import io.pulque.fleu.utils.enable
import io.pulque.fleu.utils.errors.FleuDataError
import io.pulque.fleu.utils.setupClearButtonWithAction
import io.pulque.fleu.viewmodel.PlaceAddressViewModel
import kotlinx.android.synthetic.main.activity_place_address.*
import javax.inject.Inject

private const val PERMISSIONS_REQUEST_LOCATION = 99
private const val REQUEST_MAP = 10

class PlaceAddressActivity : FleuBaseActivity() {

    @Inject
    lateinit var geocoder: Geocoder

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var placeAddressViewModel: PlaceAddressViewModel

    private var addressAdapter: AddressAdapter? = null

    private var currentAddress: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_address)

        placeAddressViewModel = ViewModelProviders.of(this, viewModelFactory)[PlaceAddressViewModel::class.java]

        subscribeSavePlace()
        subscribeAddressList()
        setupAddressInput()
        setupNicknameInput()
        setupList()
        setupSaveButton()
        subscribeErrors()

    }

    private fun setupSaveButton(){
        save.setOnClickListener {
            currentAddress?.let {
                placeAddressViewModel.saveAddress(nickname.text.toString(), it.latitude, it.longitude)
            } ?: showErrorMessage("Error processing the place")
        }
    }

    private fun setupList() {

        addressAdapter = AddressAdapter(chooseOnMapListener = {
            if (checkForLocationPermissions()) {
                startActivityForResult(Intent(this, PickAddressActivity::class.java), REQUEST_MAP)
            }
        }, onAddressSelected = {
            currentAddress = it
            address.setText(currentAddress?.getAddressLine(0))
            address.clearFocus()
            toggleSaveButton()
        })

        addresses.apply {
            layoutManager =
                LinearLayoutManager(this@PlaceAddressActivity, RecyclerView.VERTICAL, false)
            adapter = addressAdapter
        }
    }

    private fun subscribeErrors(){
        placeAddressViewModel.error.observe(this, Observer {
            when(it){
                is FleuDataError.PlaceNotAddedError ->{
                    showErrorMessage("Error adding new place")
                }
                is FleuDataError.RequestNotSuccesful ->{
                    showErrorMessage("Request not performed")
                }
            }
        })
    }

    private fun subscribeAddressList() {

        placeAddressViewModel.addressList.observe(this, Observer { addressList ->
            addressList?.let {
                addressAdapter?.addressList = it
            }
        })
    }

    private fun subscribeSavePlace() {
        placeAddressViewModel.savePlace.observe(this, Observer {
            Toast.makeText(this, "Place added", Toast.LENGTH_SHORT).show()
            finish()
        })
    }

    private fun setupAddressInput() {
        address.setupClearButtonWithAction()
        address.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().isNotEmpty()) {
                    placeAddressViewModel.fetchAddress(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun setupNicknameInput() {
        nickname.setupClearButtonWithAction()
        nickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                toggleSaveButton()
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

    private fun toggleSaveButton() {
        currentAddress?.let {
            if (nickname.text.isNotEmpty()) {
                save.enable()
            } else {
                save.disable()
            }
        } ?: save.disable()
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
            val tempAddress = data?.extras?.getParcelable(EXTRA_ADDRESS) as Address
            when (resultCode) {
                RESULT_EDIT -> {
                    address.setText(tempAddress.getAddressLine(0))
                    address.requestFocus()
                    address.setSelection(tempAddress.getAddressLine(0).count())
                }

                RESULT_SAVE -> {
                    currentAddress = tempAddress
                    address.setText(tempAddress.getAddressLine(0))
                    toggleSaveButton()
                }
            }
        }
    }
}
