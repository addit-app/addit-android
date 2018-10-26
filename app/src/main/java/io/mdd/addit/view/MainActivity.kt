package io.mdd.addit.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Log
import android.webkit.JsResult
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import io.mdd.addit.R
import io.mdd.addit.webkit.AdditWebChromeClient
import io.mdd.addit.webkit.AdditWebview


class MainActivity : AppCompatActivity() {

    var webView: AdditWebview? = null

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//      인텐트 얻어옴, 액션  MIME타입 가져오기
        val action = intent.action
        val type = intent.type

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
                val builder = AlertDialog.Builder(this)
                builder.setTitle("test")
                builder.setMessage(Html.fromHtml(sharedText))
                builder.setPositiveButton("ok") { dialog, which ->
                    //Toast.makeText(applicationContext,"continuar",Toast.LENGTH_SHORT).show()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

        initWebview()

        // get reference to button
        val btn_click_me = findViewById(R.id.btn_callWebFun) as Button

// set on-click listener
        btn_click_me.setOnClickListener {
            Log.d("test", "클릭@")

            val url = "url"
            val comment = "comment"
            webView!!.evaluateJavascript("javascript: " + "testFuncCall(\"" + url + "\", \"" + comment +"\")",
                    null)
        }
    }

    fun initWebview() {


        webView = findViewById<AdditWebview>(R.id.webView)
        webView!!.webViewClient = object : WebViewClient(){
            override  fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView!!.init()
//        webView!!.webChromeClient = AdditWebChromeClient(this@MainActivity)
        webView!!.webChromeClient = object : AdditWebChromeClient(this@MainActivity){
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                Log.d("test", "얼럿!!")
                return super.onJsAlert(view, url, message, result)
            }

        }

        webView!!.loadUrl("file:///android_asset/www/index.html")

    }
}
