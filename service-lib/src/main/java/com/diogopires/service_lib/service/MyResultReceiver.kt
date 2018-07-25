package com.diogopires.service_lib.service

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver

class MyResultReceiver(handler: Handler)// TODO Auto-generated constructor stub
    : ResultReceiver(handler) {

    private var mReceiver: Receiver? = null



    fun setReceiver(receiver: Receiver) {
        mReceiver = receiver
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {

        if (mReceiver != null) {
            mReceiver!!.onReceiveResult(resultCode, resultData)
        }
    }

    interface Receiver {
        fun onReceiveResult(resultCode: Int, resultData: Bundle)

    }

}