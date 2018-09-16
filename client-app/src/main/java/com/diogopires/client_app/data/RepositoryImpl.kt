package com.diogopires.client_app.data

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.ResultReceiver
import io.reactivex.Single

class RepositoryImpl(private val context: Context) : Repository {
    override fun getQuote(): Single<String> {
        return prepareApiCall(ApiCall.QUOTE)
    }

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
                putExtra(API_REQUEST, apiCall.name)
                setComponent(ComponentName(SERVER_APP_PACKAGE, SERVER_APP_SERVICE_CLASS))

                val callback = object : ResultReceiver(null) {
                    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                        super.onReceiveResult(resultCode, resultData)
                        try {
                            val error = resultData!![REQUEST_ERROR] as String?
                            if (!error.isNullOrEmpty()) {
                                e.onError(Exception(error))
                            } else {
                                e.onSuccess(resultData[REQUEST_RESPONSE] as String)
                            }
                        } catch (ex: Exception) {
                            e.onError(ex)
                        }
                    }
                }
                this.setExtrasClassLoader(callback::class.java.classLoader)
                this.putExtra(REQUEST_CALLBACK, prepareCallbackToTransfer(callback))
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

    companion object {
        private const val SERVER_APP_PACKAGE = "com.diogopires.communicator"
        private const val SERVER_APP_SERVICE_CLASS = "$SERVER_APP_PACKAGE.service.Orchestrator"
        private const val API_REQUEST = "api_request"
        private const val REQUEST_ERROR = "request_error"
        private const val REQUEST_RESPONSE = "request_response"
        private const val REQUEST_CALLBACK = "api_callback"
    }
}

enum class ApiCall {
    CHARACTER,
    HOUSE,
    CITY,
    QUOTE
}