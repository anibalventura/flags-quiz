package com.anibalventura.flagsquiz.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anibalventura.flagsquiz.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Hide the notification bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        btnStart.setOnClickListener {
            // Show a Toast message when don't have a user name
            if (etName.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.please_enter_name), Toast.LENGTH_SHORT)
                    .show()
            } else {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}