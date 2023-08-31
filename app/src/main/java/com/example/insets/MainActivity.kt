package com.example.insets

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.webkit.WebView
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val attrib = window.attributes
        attrib.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES*/

        // Obtener los insets de la vista raíz
        val rootView = findViewById<View>(android.R.id.content)
        val btnRojo = findViewById<Button>(R.id.btn_rojo)
        val btnAmarillo = findViewById<Button>(R.id.btn_amarillo)
        val webView = findViewById<WebView>(R.id.webView)
        webView.loadUrl("https://games.espnqa.com/lm-app/lm-qa/#FFL/home")
        /*webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                webView.loadUrl("https://games.espnqa.com/lm-app/lm-qa/#FFL/home")
            }
        }*/
        btnRojo.setOnClickListener {
            rootView.setBackgroundResource(R.color.rojo)
            getBackgroundColor(rootView)
        }
        btnAmarillo.setOnClickListener {
            rootView.setBackgroundResource(R.color.amarillo)
            getBackgroundColor(rootView)
        }

        rootView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val insets = ViewCompat.getRootWindowInsets(rootView)
                val statusBarInsets = insets?.getInsets(WindowInsetsCompat.Type.systemBars())
                println("** StatusBar Insets - Top: ${statusBarInsets?.top}, Bottom: ${statusBarInsets?.bottom}")
                println("** StatusBar Insets - First Top: ${rootView?.paddingTop}")

                /*WindowInsetsControllerCompat(window, rootView).apply {
                    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    show(WindowInsetsCompat.Type.systemBars())
                }*/
            }
        })
    }

    private fun getBackgroundColor(view: View) {
        val background = view.background
        val color = if (background is ColorDrawable) {
            background.color
        } else {
            Color.WHITE // Default color if unable to get background color
        }
        setStatusBarColor(color)
    }

    // This works with LOLLIPOP or higher
    private fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }
}
