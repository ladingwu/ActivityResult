package com.example.demoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.activityresult.R
import com.example.activityresultlib.setViewOnclickAndLaunch
import com.example.activityresultlib.startActivityForResultBack
import com.example.activityresultlib.startActivityWaitResult
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        tv.setOnClickListener {
//            lifecycleScope.launchWhenCreated{
//                var intent = Intent(this@MainActivity,NextActivity::class.java)
//                intent.putExtra("key",edit.text.toString())
//
//                var result = this@MainActivity.startActivityWaitResult(intent,1001)
//
//
//                var resultStr = result.intent?.getStringExtra("key_back")
//                if (result.isOk() && resultStr != null) {
//                    tv.text = "is ok  \n"+ resultStr+"\n req:  "+result.requestCode+" resultCode: "+result.resultCode
//                }
//                if (result.isCanceled()){
//                    tv.text = "is canceled "
//                }
//            }
//        }

        lifecycleScope.setViewOnclickAndLaunch(tv){
            var intent = Intent(this@MainActivity,NextActivity::class.java)
            intent.putExtra("key",edit.text.toString())

            var result = this@MainActivity.startActivityWaitResult(intent,1001)


            var resultStr = result.intent?.getStringExtra("key_back")
            if (result.isOk() && resultStr != null) {
                tv.text = "is ok  \n"+ resultStr+"\n req:  "+result.requestCode+" resultCode: "+result.resultCode
            }
            if (result.isCanceled()){
                tv.text = "is canceled "
            }
        }
        tv2.setOnClickListener {

            var intent = Intent(this@MainActivity,NextActivity::class.java)

            intent.putExtra("key",edit.text.toString())

            this@MainActivity.startActivityForResultBack(intent,1001){result->

                var resultStr = result.intent?.getStringExtra("key_back")
                if (result.isOk() && resultStr != null) {
                    tv2.text = "is ok  \n"+ resultStr+"\n req:  "+result.requestCode+" resultCode: "+result.resultCode
                }
                if (result.isCanceled()){
                    tv2.text = "is canceled "
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
