package com.diogopires.communicator.service

import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.app.JobIntentService
import com.diogopires.communicator.data.GotRepository
import com.diogopires.communicator.data.GotRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


open class Orchestrator : JobIntentService() {

    private val repository: GotRepository = GotRepositoryImpl()

    override fun onHandleWork(intent: Intent) {

        val callback: ResultReceiver? = intent.getParcelableExtra("api_callback")

        callback?.run {
            repository.getQuote()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ character ->
                        performCallback(this, character)
                    }, { error ->
                        performCallbackError(this, error)
                    })
        }
    }

    private fun performCallback(resultReceiver: ResultReceiver, item: String) {
        val bundle = Bundle()
        bundle.putString("request_response", item)
        resultReceiver.send(0, bundle)
    }

    private fun performCallbackError(resultReceiver: ResultReceiver, throwable: Throwable) {
        val bundle = Bundle()
        bundle.putString("request_error", throwable.message ?: "request error")
        resultReceiver.send(0, bundle)
    }

    companion object {
        const val SERVICE_NAME: String = "Orchestrator"
    }
}