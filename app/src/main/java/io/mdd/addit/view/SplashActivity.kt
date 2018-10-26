package io.mdd.addit.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.mdd.addit.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java).apply {
            //            data = Uri.parse(fileUrl)
        }
        startActivity(intent)

        finish()

    }
}
