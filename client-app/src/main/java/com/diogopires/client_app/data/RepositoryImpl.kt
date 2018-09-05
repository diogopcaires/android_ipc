package com.diogopires.client_app.data

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.ResultReceiver
import io.reactivex.Single

class RepositoryImpl(private val context: Context) : Repository {
    override fun getCharacter(): Single<String> {
        return prepareApiCall(ApiCall.CHARACTER)
    }

    override fun getHouse(): Single<String> {
        return prepareApiCall(ApiCall.HOUSE)
    }

    override fun getCity(): Single<String> {
        return prepareApiCall(ApiCall.CITY)
    }


    private fun prepareApiCall(apiCall: ApiCall): Single<String> {
        return Single.create { e ->
            val intent = Intent()
            with(intent) {
                putExtra("api_request", apiCall.name)
                setComponent(ComponentName("com.diogopires.communicator", "com.diogopires.communicator.service.Orchestrator"))

                val callback = object : ResultReceiver(null) {
                    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                        super.onReceiveResult(resultCode, resultData)
                        try {
                            val error = resultData!!["request_error"] as String?
                            if (!error.isNullOrEmpty()) {
                                e.onError(Exception(error))
                            } else {
                                e.onSuccess(resultData!!["request_response"] as String)
                            }
                        } catch (ex: Exception) {
                            e.onError(ex)
                        }
                    }
                }
                this.setExtrasClassLoader(callback::class.java.classLoader)
                this.putExtra("api_callback", prepareCallbackToTransfer(callback))
            }

            context.startService(intent)
        }
    }

    private fun prepareCallbackToTransfer(resultReceiver: ResultReceiver): ResultReceiver {
        val parcel = Parcel.obtain()
        resultReceiver.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)
        val auxReceiver = ResultReceiver.CREATOR.createFromParcel(parcel)
        parcel.recycle()
        return auxReceiver
    }
}

enum class ApiCall {
    CHARACTER,
    HOUSE,
    CITY
}