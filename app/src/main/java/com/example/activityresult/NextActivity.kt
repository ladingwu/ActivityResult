package com.example.demoapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.activityresult.R
import kotlinx.android.synthetic.main.activity_next.*

class NextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
        var intentContent = intent.getStringExtra("key")
        if (intentContent!=null){
            tv.text = intentContent
        }
        btn_back.setOnClickListener {
            var str = input.text.toString()
            var intent = Intent()
            intent.putExtra("key_back",str)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }
}