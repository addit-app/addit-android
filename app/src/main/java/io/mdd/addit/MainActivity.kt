package io.mdd.addit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Html
import android.webkit.WebView
import android.webkit.WebViewClient


class MainActivity : AppCompatActivity() {

    var webView: WebView? = null

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
    }

    fun initWebview() {


        webView = findViewById<WebView>(R.id.webView)
        webView!!.webViewClient = object : WebViewClient(){
            override  fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }

        webView!!.loadUrl("file:///android_asset/www/getItem.html")
    }
}
