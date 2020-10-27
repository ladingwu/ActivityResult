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
    private var job :Job? = null
    private var curRequestCode = Int.MAX_VALUE

    private fun startActivity(intent: Intent,requestCode: Int,dataCallback:IActivityResult):Fragment?{
        curRequestCode = requestCode
        var manager = activity.supportFragmentManager
        var fragment = activity.supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
        if (fragment!=null){
            (fragment as? HolderFragment)?.dataCallback = dataCallback
        }else{
            fragment = HolderFragment()
            fragment?.dataCallback = dataCallback
            manager.beginTransaction().add(fragment,FRAGMENT_TAG).commitAllowingStateLoss()
        }
        (fragment as? HolderFragment)?.tryStartActivityForResult(intent,requestCode = requestCode)
        return fragment
    }


    fun startActivityforResultBack(intent: Intent,requestCode: Int,resultCallback:((data:IntentResult)->Unit),failCallback:(()->Unit)? = null){
        this.curResultCallback = resultCallback
        this.curFailCllback = failCallback
        startActivity(intent, requestCode,this)
    }


    fun startActivityforResultBack(intent: Intent,requestCode: Int,resultCallback:IActivityResult){
        startActivity(intent, requestCode,resultCallback)
    }

    override fun onDataBack(requestCode: Int, resultCode: Int, data: Intent?) {
        curResultCallback?.invoke(IntentResult(data,requestCode,resultCode))
    }

    override fun onFail(e:Throwable) {
        curFailCllback?.invoke()
    }

    suspend fun startActivityWaitResult(intent: Intent,requestCode: Int) = suspendCancellableCoroutine<IntentResult>{coroutinue->
       startActivityforResultBack(intent,requestCode,object :IActivityResult{
           override fun onDataBack(requestCode: Int, resultCode: Int, data: Intent?) {
               if (coroutinue.isActive){
                   coroutinue.resume(IntentResult(data,requestCode,resultCode))
               }
           }

           override fun onFail(e:Throwable) {
               if (coroutinue.isActive) {
                   coroutinue.resumeWithException(e)
               }
           }
       })
    }


}
