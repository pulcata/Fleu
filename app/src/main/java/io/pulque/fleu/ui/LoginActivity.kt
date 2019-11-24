package io.pulque.fleu.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.android.support.DaggerAppCompatActivity
import io.pulque.fleu.R
import io.pulque.fleu.di.ViewModelFactory
import io.pulque.fleu.utils.errors.FleuDataError
import io.pulque.fleu.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject

const val FIREBASE_LOGIN_REQUEST_CODE = 1000

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var googleAuth: FirebaseAuth

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var loginViewModel: LoginViewModel

    override fun onStart() {
        super.onStart()

        val currentAccount = GoogleSignIn.getLastSignedInAccount(this)

        currentAccount?.let {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]

        subscribeUserInfo()

        subscribeErrors()

        googleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, FIREBASE_LOGIN_REQUEST_CODE)
        }
    }

    private fun subscribeUserInfo() {
        loginViewModel.userInfo.observe(this, Observer {
            Timber.d(it.email)
        })
    }

    private fun subscribeErrors() {
        loginViewModel.error.observe(this, Observer {
            when (it) {
                is FleuDataError.UnknownError -> {
                    showErrorMessage("An unexpected error ocurred")
                }

                is FleuDataError.UserNotFound -> {
                    showErrorMessage("User not found in Firebase")
                }

                is FleuDataError.GoogleLoginError -> {
                    showErrorMessage("Error retrieving token from Firebase")
                }
            }
        })
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        googleAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                loginViewModel.retrieveGoogleTokenFromcCurrentSession(googleAuth.currentUser, task)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            FIREBASE_LOGIN_REQUEST_CODE -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    account?.let {

                        firebaseAuthWithGoogle(it)

                    }
                } catch (e: ApiException) {
                    Timber.e(e)
                }
            }
        }
    }
}
