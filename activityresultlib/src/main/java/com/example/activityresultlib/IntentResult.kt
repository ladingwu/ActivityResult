package com.example.activityresultlib

import android.app.Activity
import android.content.Intent

class IntentResult (
    var intent:Intent? = null,
    var requestCode:Int = Int.MAX_VALUE,
    var resultCode:Int = Int.MAX_VALUE

){
    fun isOk() = Activity.RESULT_OK == resultCode
    fun isCanceled() = Activity.RESULT_CANCELED == resultCode
}