package com.example.activityresultlib

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.lang.Exception

class HolderFragment : Fragment() {
    var dataCallback:IActivityResult? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runnable?.run()
    }
    private var runnable:Runnable? = null
    fun tryStartActivityForResult(intent:Intent,requestCode: Int){
        runnable = Runnable {
            try {
                startActivityForResult(intent, requestCode)
            }catch (e:IllegalStateException){
                dataCallback?.onFail(e)
            }
        }
        if (isAdded){
            runnable?.run()
            runnable = null
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        dataCallback?.onDataBack(requestCode, resultCode, data)
    }
}