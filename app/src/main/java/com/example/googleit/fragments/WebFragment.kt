package com.example.googleit.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.googleit.R
import com.example.googleit.databinding.FragmentWebBinding

const val URL = "https://www.google.com/"

class WebFragment : Fragment() {

    private lateinit var webBinding: FragmentWebBinding
    private lateinit var webViewState: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        webBinding = FragmentWebBinding.inflate(inflater)

        return webBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webViewSetup()
        activity?.findViewById<View>(R.id.bottomNavigationView)?.visibility = View.VISIBLE
        val callback = object: OnBackPressedCallback(true) {
            @SuppressLint("SetJavaScriptEnabled")
            override fun handleOnBackPressed() {
                if (webBinding.webView.canGoBack()) {
                    webBinding.webView.goBack()
                } else {
                    webBinding.webView.apply {
                        loadUrl(URL)
                        settings.javaScriptEnabled = true
                    }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        webBinding.webView.setOnLongClickListener {
            saveUserURL()
            Toast.makeText(this.context, "URL saved", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        Toast.makeText(this.context, "Just long press for saving URL", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        webViewState = Bundle()
        webBinding.webView.saveState(webViewState)
    }



    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup() = with(webBinding) {
        webView.webViewClient = WebViewClient()
        webView.apply {
            loadUrl(loadUserURL() ?: URL)
            settings.javaScriptEnabled = true
        }
    }

    private fun saveUserURL() {
        val sharedPreferences = activity?.getSharedPreferences("saveURL", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.apply{
            putString("URL", webBinding.webView.url)
        }?.apply()
    }

    private fun loadUserURL(): String? = with(webBinding) {
        val sharedPreferences = activity?.getSharedPreferences("saveURL", Context.MODE_PRIVATE)
        return sharedPreferences?.getString("URL", URL)
    }
}