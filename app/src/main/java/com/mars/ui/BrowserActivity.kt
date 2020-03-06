package com.mars.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.mars.R
import kotlinx.android.synthetic.main.activity_browser.*

class BrowserActivity : AppCompatActivity() {

    companion object {
        const val URL_TAG = "url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)

        val url = intent.extras?.get(URL_TAG) as String

        val webView = findViewById<WebView>(R.id.webView)
        webView.loadUrl(url)
    }
}
