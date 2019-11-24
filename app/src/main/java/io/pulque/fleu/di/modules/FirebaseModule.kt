package io.pulque.fleu.di.modules

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import io.pulque.fleu.R

/*
 * @author savirdev on 2019-11-24
 */

@Module
class FirebaseModule {

    @Provides
    fun providesGoggleSignIndClient(application: Application): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(application, gso)
    }

    @Provides
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}