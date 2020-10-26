package com.example.activityresultlib

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import java.lang.RuntimeException
import java.lang.ref.WeakReference
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.intercepted
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

class ActivityResult(var activity:FragmentActivity) :IActivityResult{
    companion object{
        const val FRAGMENT_TAG = "tag_"
    }
    private var reference:WeakReference<Activity>? = null
    private var curResultCallback:((data:IntentResult)->Unit)? = null
    private var curFailCllback:(()->Unit)? = null

    private fun startActivity(intent: Intent,requestCode: Int,dataCallback:IActivityResult):Fragment?{
        var manager = activity.supportFragmentManager
        var fragment = activity.supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
        if (fragment!=null){
            (fragment as? HolderFragment)?.dataCallback = dataCallback
        }else{
            fragment = HolderFragment()
            fragment?.dataCallback = dataCallback
            manager.beginTransaction().add(fragment,FRAGMENT_TAG).commitAllowingStateLoss()
        }
        return fragment
    }


    fun startActivityforResultBack(intent: Intent,requestCode: Int,resultCallback:((data:IntentResult)->Unit),failCallback:(()->Unit)? = null){
        this.curResultCallback = resultCallback
        this.curFailCllback = failCallback
        var fragment = startActivity(intent, requestCode,this)
        (fragment as? HolderFragment)?.tryStartActivityForResult(intent,requestCode = requestCode)
    }


    fun startActivityforResultBack(intent: Intent,requestCode: Int,resultCallback:IActivityResult){
        var fragment = startActivity(intent, requestCode,resultCallback)
        (fragment as? HolderFragment)?.tryStartActivityForResult(intent,requestCode = requestCode)
    }

    override fun onDataBack(requestCode: Int, resultCode: Int, data: Intent?) {
        curResultCallback?.invoke(IntentResult(data,requestCode,resultCode))
    }

    override fun onFail(e:Throwable) {
        curFailCllback?.invoke()
    }

    suspend fun startActivityWaitResult(intent: Intent,requestCode: Int) = suspendCoroutine<IntentResult>{coroutinue->
       startActivityforResultBack(intent,requestCode,object :IActivityResult{
           override fun onDataBack(requestCode: Int, resultCode: Int, data: Intent?) {
               coroutinue.resume(IntentResult(data,requestCode,resultCode))
           }

           override fun onFail(e:Throwable) {
                coroutinue.resumeWithException(e)
           }
       })
    }


}
