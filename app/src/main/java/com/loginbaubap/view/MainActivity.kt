package com.loginbaubap.view

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.TransitionManager.beginDelayedTransition
import com.google.android.material.snackbar.Snackbar
import com.loginbaubap.R
import com.loginbaubap.databinding.ActivityMainBinding
import com.loginbaubap.model.enums.ErrorCode
import com.loginbaubap.model.enums.ErrorCode.NOTHING_HAPPENED
import com.loginbaubap.utils.AppSession
import com.loginbaubap.utils.LoginBaubapException
import com.loginbaubap.utils.ext.launchWithDelay
import com.loginbaubap.utils.ext.showToast
import com.loginbaubap.view.login.LoginFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var errorSnackBar: Snackbar
    private var doubleBackToExitPressedOnce: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHost.navController
        setUpErrorSnackBar()
        observeUiEvents()
    }

    private fun setUpErrorSnackBar() {
        @ColorInt
        val bgColor = ContextCompat.getColor(this@MainActivity, R.color.red)
        val textColor = ContextCompat.getColor(this@MainActivity, R.color.white)
        errorSnackBar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        errorSnackBar.setBackgroundTint(bgColor)
        errorSnackBar.setTextColor(textColor)
    }

    private fun observeUiEvents() {
        AppSession.run {
            globalProgressBar.observe(this@MainActivity) {
                beginDelayedTransition(binding.layoutActivityMain)
                binding.pgbMain.visibility = if (it) View.VISIBLE else View.GONE
                binding.bgProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                binding.blockerViewsScreen.visibility = if (it) View.VISIBLE else View.GONE
            }
            globalError.observe(this@MainActivity) { error ->
                if (error.errorCode != NOTHING_HAPPENED) {
                    when (error.errorCode) {
                        ErrorCode.SOMETHING_WENT_WRONG -> {
                            errorSnackBar.setText(R.string.something_went_wrong).show()
                        }
                        ErrorCode.ERROR_INVALID_CREDENTIALS -> {
                            errorSnackBar.setText(R.string.invalid_credentials).show()
                        }
                        else -> {
                            errorSnackBar.setText(R.string.something_went_wrong).show()
                        }
                    }
                    showGlobalError(LoginBaubapException(NOTHING_HAPPENED))
                }
            }
        }
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.label) {
            LoginFragment::class.simpleName -> pressAgainToExit()
            else -> super.onBackPressed()
        }
    }

    private fun pressAgainToExit() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        } else {
            doubleBackToExitPressedOnce = true
            showToast(getString(R.string.press_again_to_exit))
            lifecycleScope.launchWithDelay(2000) { doubleBackToExitPressedOnce = false }
        }
    }
}