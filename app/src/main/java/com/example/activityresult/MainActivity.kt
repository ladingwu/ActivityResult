package com.example.demoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.activityresult.R
import com.example.activityresultlib.startActivityWaitResult
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv.setOnClickListener {
            lifecycleScope.launchWhenCreated{
                var intent = Intent(this@MainActivity,NextActivity::class.java)
                intent.putExtra("key","send message")

                var result = this@MainActivity.startActivityWaitResult(intent,1001)

                
                var resultStr = result.intent?.getStringExtra("key_back")
                if (resultStr != null) {
                    tv.text = resultStr+" req:  "+result.requestCode+" resultCode: "+result.resultCode
                }
            }
        }
    }

}
fun print(s:String){
    Log.w("Test",s)
}
interface IDeveloper {
    fun writeCode()
}


/**
 * 目标对象实现类
 */
class Developer(private val name: String) : IDeveloper {
    override fun writeCode() {
        print("Developer $name writes code")
    }

}
