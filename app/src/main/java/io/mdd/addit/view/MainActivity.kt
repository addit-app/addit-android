package io.mdd.addit.view

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import io.mdd.addit.R
import io.mdd.addit.util.isValid
import io.mdd.addit.webkit.AdditWebChromeClient
import io.mdd.addit.webkit.AdditWebview


class MainActivity : AppCompatActivity() {

    var webView: AdditWebview? = null

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWebview()


        // get reference to button
        val btn_click_me = findViewById(R.id.btn_callWebFun) as Button

// set on-click listener
        btn_click_me.setOnClickListener {
            Log.d("test", "클릭@")


            val intent = Intent(this, PostActivity::class.java).apply {
                //                putExtra("url", url)
                //            data = Uri.parse(fileUrl)
            }
            startActivity(intent)
        }
    }

    fun initWebview() {
        webView = findViewById<AdditWebview>(R.id.webView)
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView!!.init()
        webView!!.webChromeClient = AdditWebChromeClient(this@MainActivity)
        webView!!.loadUrl("file:///android_asset/www/build/index.html")


    }

    override fun onResume() {
        super.onResume()

        //clipboard check
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var url = clipboard.text.toString()
        if(isValid(url)){
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setMessage(url + "로 코멘트를 남기시겠습니까?")
            builder.setPositiveButton("예") { dialog, which ->
                val intent = Intent(this, PostActivity::class.java).apply {
                    putExtra("url", url)
                }
                clipboard.text = ""
                startActivity(intent)
            }
            builder.setNegativeButton("아니오") { dialog, which ->
                dialog.dismiss()
            }
            builder.setOnCancelListener {
                clipboard.text = ""
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }
    }
}
