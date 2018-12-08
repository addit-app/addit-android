package io.mdd.addit.view

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import io.mdd.addit.R
import io.mdd.addit.util.isValid
import io.mdd.addit.webkit.AdditWebChromeClient
import io.mdd.addit.webkit.AdditWebview


class MainActivity : AppCompatActivity() {

    var webView: AdditWebview? = null
    val url = "file:///android_asset/www/build/index.html"

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWebview()


        // get reference to button
        val btn_click_me = findViewById(R.id.btn_callWebFun) as FloatingActionButton

// set on-click listener
        btn_click_me.setOnClickListener {
            Log.d("test", "클릭@")

        showInputUrl()

        }

        btn_click_me.hide()
    }

    @JavascriptInterface
    fun onData(adress: String, nickName: String, key: String) {
        Log.d("test", adress + " " + nickName + " " + key)
    }


    fun showInputUrl() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("URL을 입력해주세요.")

// Set up the input
        val input = EditText(this)
//        var txtUrl= ""
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.requestFocus()
        input.setText("http://")
        input.setSelection(input.text.length)

        builder.setView(input)

// Set up the buttons
        builder.setPositiveButton("OK") { dialog, which ->

            val intent = Intent(this, PostActivity::class.java).apply {
                                putExtra("url", input.text.toString())
                //            data = Uri.parse(fileUrl)
            }
            startActivity(intent)
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        builder.show()
    }

    fun initWebview() {
        webView = findViewById<AdditWebview>(R.id.webView)
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                Log.d("test2", "url: " + request!!.url.toString())
                view!!.loadUrl(request!!.url.toString())
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d("test2", "url:finish " + url)
                view!!.loadUrl("javascript:(function() { " +
                        "document.getElementById('open-newtab').style.display='none'; " +
                        "})()");


//                view!!.evaluateJavascript("javascript: " + "openNewTab.parentNode.removeChild(openNewTab)", null)


                super.onPageFinished(view, url)
            }

        }

        webView!!.init()
        webView!!.webChromeClient = AdditWebChromeClient(this@MainActivity)
        webView!!.loadUrl(url)
//        webView!!.settings.


    }

    override fun onResume() {
        super.onResume()

        //clipboard check
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var url = ""//clipboard.text.toString()
        if (isValid(url)) {
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
