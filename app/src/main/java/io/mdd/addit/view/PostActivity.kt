package io.mdd.addit.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import io.mdd.addit.R
import io.mdd.addit.util.isValid
import io.mdd.addit.webkit.AdditWebChromeClient
import io.mdd.addit.webkit.AdditWebview

class PostActivity : AppCompatActivity() {
    var webView: AdditWebview? = null
    var btnSubmit: FloatingActionButton? = null
    var inputText: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        initWebview()

        val action = intent.action
        val type = intent.type
        var url = intent.getStringExtra("url")
        var comment = "comment"

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
                url = sharedText;

            }
        }

        inputText = findViewById<TextInputEditText>(R.id.input_comtent)
        btnSubmit = findViewById<FloatingActionButton>(R.id.btn_submit)
        btnSubmit!!.setOnClickListener {
            Log.d("test", "클릭@")

            if(isValid(url)) {
                comment = inputText!!.text.toString()
                webView!!.evaluateJavascript("javascript: " + "testFuncCall(\"" + url + "\", \"" + comment + "\")", null)
            } else {
                Toast.makeText(this@PostActivity, "유효하지 않은 url입니다.", Toast.LENGTH_SHORT).show()

            }

        }
        //      인텐트 얻어옴, 액션  MIME타입 가져오기



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
        webView!!.webChromeClient = AdditWebChromeClient(this@PostActivity)

        webView!!.loadUrl("file:///android_asset/www/index.html")

    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)
        }

    }
}
