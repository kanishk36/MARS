package com.mars

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProviders
import com.mars.common.base.AbstractActivity
import com.mars.viewmodels.LoginViewModel

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : AbstractActivity<LoginViewModel>() {

    override val viewModel: LoginViewModel
        get() = ViewModelProviders.of(this).get(LoginViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.FullscreenTheme)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        Handler().postDelayed( {
            startLoginActivity()
        }, 1500)
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}
