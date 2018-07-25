package com.diogopires.service_lib.core.extension

import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import javax.security.auth.callback.Callback

/**
 * Created by diogosilva on 23/07/18.
 */
private val REQUEST_CALLBACK = "REQUEST_CALLBACK"

var Intent.requestCallback: ResultReceiver
    get()= this.getParcelableExtra(REQUEST_CALLBACK)
    set(value) { this.putExtra(REQUEST_CALLBACK, value) }

