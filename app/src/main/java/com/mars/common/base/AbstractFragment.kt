package com.mars.common.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mars.network.ErrorInfo

/**
 * An abstract [Fragment] subclass.
 */
abstract class AbstractFragment<V : BaseViewModel>: Fragment() {

    var baseActivity: AbstractActivity<*>? = null
        private set

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AbstractActivity<*>) {
            this.baseActivity = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.errorResponse().observe(this, Observer {
            showError(it)
        })
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    fun showProgressDialog(msg: String? = null) {
        baseActivity!!.showProgressDialog(msg)
    }

    fun hideProgressDialog() {
        baseActivity!!.hideProgressDialog()
    }

    fun showError(errorInfo: ErrorInfo?) {
        baseActivity!!.showError(errorInfo)
    }

    fun showSuccess(message: String) {
        baseActivity!!.showSuccess(message)
    }

    fun logOut() {
        baseActivity!!.logOut()
    }
}