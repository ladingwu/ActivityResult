package com.example.activityresultlib

import android.content.Intent
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.startActivityForResultBack(intent: Intent, requestCode: Int, resultCallback:((data:IntentResult)->Unit)){
    var activityResult = ActivityResult(this)
    activityResult.startActivityforResultBack(intent, requestCode, resultCallback)
}
suspend fun FragmentActivity.startActivityWaitResult(intent: Intent, requestCode: Int):IntentResult{
    var activityResult = ActivityResult(this)
    return activityResult.startActivityWaitResult(intent, requestCode)
}