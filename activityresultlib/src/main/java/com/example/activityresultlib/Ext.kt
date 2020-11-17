package com.example.activityresultlib

import android.content.Intent
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope

fun FragmentActivity.startActivityForResultBack(intent: Intent, requestCode: Int, resultCallback:((data:IntentResult)->Unit)){
    var activityResult = ActivityResult(this)
    activityResult.startActivityforResultBack(intent, requestCode, resultCallback)
}
suspend fun FragmentActivity.startActivityWaitResult(intent: Intent, requestCode: Int):IntentResult{
    var activityResult = ActivityResult(this)
    return activityResult.startActivityWaitResult(intent, requestCode)
}


fun LifecycleCoroutineScope.setViewOnclickAndLaunch(view: View,block: suspend CoroutineScope.() -> Unit){
    view.setOnClickListener {
        launchWhenCreated (block)
    }
}
