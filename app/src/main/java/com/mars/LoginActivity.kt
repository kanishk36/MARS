package com.mars

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mars.common.base.AbstractActivity
import com.mars.ui.HomeActivity
import com.mars.utils.AppCache
import com.mars.viewmodels.LoginViewModel

class LoginActivity : AbstractActivity<LoginViewModel>() {

    lateinit var txtUsername: EditText
    lateinit var txtPassword: EditText

    override val viewModel: LoginViewModel
        get() = ViewModelProviders.of(this).get(LoginViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUsername = findViewById(R.id.txtUsername)
        txtPassword = findViewById(R.id.txtPassword)

        findViewById<Button>(R.id.btnLogin).setOnClickListener(object: View.OnClickListener{

            override fun onClick(p0: View?) {
                validateFields()
            }

        })

        registerObservers()
    }

    override fun markoutAttendance() {
    }

    private fun registerObservers() {
        viewModel.showLoading().observe(this, Observer {
            showProgressDialog(getString(R.string.loading))
        })

        viewModel.dismissLoading().observe(this, Observer {
            hideProgressDialog()
        })

        viewModel.loginResponse.observe(this, Observer {
            startHomeActivity()
        })
    }

    private fun validateFields() {
        if(TextUtils.isEmpty(txtUsername.text)) {
            txtUsername.error = getString(R.string.username_field_empty_error)

        } else if (TextUtils.isEmpty(txtPassword.text)) {
            txtPassword.error = getString(R.string.password_field_empty_error)

        } else {
            login()
        }
    }

    private fun login() {
        viewModel.login(txtUsername.text.toString(), txtPassword.text.toString())
    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}
