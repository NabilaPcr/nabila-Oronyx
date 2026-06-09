package com.example.bila_oronyx

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bila_oronyx.databinding.ActivityBeritaWebBinding

class BeritaWebActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeritaWebBinding
    private var newsUrl: String = ""
    private var newsTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBeritaWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsUrl = intent.getStringExtra("url") ?: "http://nabila-kebencanaan.alwaysdata.net/"
        newsTitle = intent.getStringExtra("title") ?: "Sistem Informasi SIDA"

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = newsTitle
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow)
        }

        setupWebView()
    }

    private fun setupWebView() {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress < 100) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }

            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progressBar.visibility = View.GONE
                }
            }

            loadUrl(newsUrl)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            finish()
        }
        return true
    }
}