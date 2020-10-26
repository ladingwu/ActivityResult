package com.example.activityresultlib

import android.content.Intent
import java.lang.Exception

interface IActivityResult {
    fun onDataBack(requestCode: Int, resultCode: Int, data: Intent?)
    fun onFail(e:Throwable)
}