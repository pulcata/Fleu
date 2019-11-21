package io.pulque.fleu.ui

import android.content.Intent
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.pulque.fleu.R
import kotlinx.android.synthetic.main.activity_profile.*

private const val REQUEST_ADDRESS = 10

class ProfileActivity : AppCompatActivity() {

    private var currentAddress: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        address.setOnClickListener{
            startActivityForResult(Intent(this, PlaceAddressActivity::class.java), REQUEST_ADDRESS)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ADDRESS){
            if (resultCode == RESULT_SAVE){
                currentAddress = data?.getParcelableExtra(EXTRA_ADDRESS) as Address
                address.setText(currentAddress?.getAddressLine(0))
            }
        }
    }
}
