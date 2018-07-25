package com.diogopires.service_lib.service.server.service

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.JobIntentService
import android.util.Log
import com.diogopires.service_lib.core.data.Order
import com.diogopires.service_lib.core.extension.requestCallback
import com.diogopires.service_lib.core.extension.responsePayload
import com.diogopires.service_lib.core.marshall.ApiMarshaller
import java.math.BigDecimal


class Orchestrator: JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        Log.d(SERVICE_NAME, "HIII")

         intent.requestCallback.run {
             //get a list from a repository ...
             var orderList = ArrayList<Order>()

             IntRange(0,10).forEach {e->
                 orderList.add(Order(0, BigDecimal(Math.random()),"teste $e"))
             }
             //parse the list to a json string and return to client.....
             var bundle = Bundle()
             bundle.responsePayload = ApiMarshaller.unmarshaller(orderList)

             this.send(0,bundle)
         }
    }

    companion object {
        const val SERVICE_NAME : String = "Orchestrator"
    }
}