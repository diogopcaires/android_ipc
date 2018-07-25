package com.diogopires.service_lib.client

import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import com.diogopires.service_lib.LibApp
import com.diogopires.service_lib.core.ApiConstants
import com.diogopires.service_lib.core.data.Order
import com.diogopires.service_lib.core.extension.requestCallback
import com.diogopires.service_lib.core.extension.responsePayload
import com.diogopires.service_lib.core.marshall.ApiMarshaller
import io.reactivex.Single


class ApiRequestImpl : ApiRequest {
    override fun getList(): Single<List<Order>> {
        return Single.create { e ->
            val intent = Intent()
            with(intent) {
                setClassName(ApiConstants.API_PACKAGE, ApiConstants.SERVICE_CLASS)
                requestCallback = object: ResultReceiver(null) {
                    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                        super.onReceiveResult(resultCode, resultData)
                        try{
                            var response = ApiMarshaller.marshaller(resultData!!.responsePayload)
                            e.onSuccess(response)
                        }catch (ex : Exception){
                            e.onError(ex)
                        }
                    }
                }
            }
            //wrong way to do it, sorry =( inject the context or extract this call to another class
            LibApp.context.startService(intent)
        }
    }

}