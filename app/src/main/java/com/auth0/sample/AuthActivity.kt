package com.auth0.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.management.ManagementException
import com.auth0.android.management.UsersAPIClient
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.auth0.sample.databinding.ActivityMainBinding
import com.auth0.sample.seller.SellerMainActivity
import com.google.android.material.snackbar.Snackbar

class AuthActivity : AppCompatActivity() {

    private lateinit var account: Auth0
    private lateinit var binding: ActivityMainBinding
    private var cachedCredentials: Credentials? = null
    private var cachedUserProfile: UserProfile? = null
    private var slct = 1
    private var selct=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up the account object with the Auth0 application details
        account = Auth0(
            getString(R.string.com_auth0_client_id),
            getString(R.string.com_auth0_domain)
        )

        // Bind the button click with the login action
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonLogin.setOnClickListener { loginWithBrowser() }
        binding.buttonLogout.setOnClickListener { logout() }
        val i = intent
        val str = i.getStringExtra("UserType")
        slct = if (str.equals("User", ignoreCase = true)) 2 else 1
        val sh = getSharedPreferences("isLogged", MODE_PRIVATE)
        selct = sh.getInt("logged", 1)

    }

    private fun updateUI() {
        binding.buttonLogout.isEnabled = cachedCredentials != null
        binding.buttonLogin.isEnabled = cachedCredentials == null
    }

    private fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope.
        WebAuthProvider.login(account)
            .withScheme(getString(R.string.com_auth0_scheme))
            .withScope("openid profile email read:current_user update:current_user_metadata")
            .withAudience("https://${getString(R.string.com_auth0_domain)}/api/v2/")

            // Launch the authentication passing the callback where the results will be received
            .start(this, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    showSnackBar("Failure: ${exception.getCode()}")
                }

                override fun onSuccess(credentials: Credentials) {
                    cachedCredentials = credentials
                    showSnackBar("Success: ${credentials.accessToken}")
                    updateUI()
                    showUserProfile()
                    if(slct==1){
                        val intent = Intent(this@AuthActivity, SellerMainActivity::class.java)
                        startActivity(intent)
                        finish();
                    }
                    else{
                        if(selct==2) {
                            val intent = Intent(this@AuthActivity, UserRecyclerView::class.java)
                            startActivity(intent)
                            finish();
                        }
                        else{
                            val intent = Intent(this@AuthActivity, UserRegistrationActivity::class.java)
                            startActivity(intent)
                            finish();
                        }
                    }
                }
            })
    }

    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme(getString(R.string.com_auth0_scheme))
            .start(this, object : Callback<Void?, AuthenticationException> {
                override fun onSuccess(payload: Void?) {
                    // The user has been logged out!
                    cachedCredentials = null
                    cachedUserProfile = null
                    updateUI()
                }

                override fun onFailure(exception: AuthenticationException) {
                    updateUI()
                    showSnackBar("Failure: ${exception.getCode()}")
                }
            })
    }

    private fun showUserProfile() {
        val client = AuthenticationAPIClient(account)
        client.userInfo(cachedCredentials!!.accessToken!!)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    showSnackBar("Failure: ${exception.getCode()}")
                }

                override fun onSuccess(profile: UserProfile) {
                    cachedUserProfile = profile;
                    val sharedPreferences = getSharedPreferences("EmailVar", MODE_PRIVATE)
                    val myEdit = sharedPreferences.edit()
                    myEdit.putString("Email",cachedUserProfile?.email)
                    myEdit.commit()
                    updateUI()
                }
            })
    }

    private fun getUserMetadata() {
        // Create the user API client
        val usersClient = UsersAPIClient(account, cachedCredentials!!.accessToken!!)

        // Get the full user profile
        usersClient.getProfile(cachedUserProfile!!.getId()!!)
            .start(object : Callback<UserProfile, ManagementException> {
                override fun onFailure(exception: ManagementException) {
                    showSnackBar("Failure: ${exception.getCode()}")
                }

                override fun onSuccess(userProfile: UserProfile) {
                    cachedUserProfile = userProfile;
                    updateUI()

                    val country = userProfile.getUserMetadata()["country"] as String?
                }
            })
    }
    private fun showSnackBar(text: String) {
        Snackbar.make(
            binding.root,
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }
}