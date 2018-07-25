package com.diogopires.service_lib.core.extension

import android.os.Bundle


private val REQUEST_PAYLOAD = "REQUEST_PAYLOAD"

var Bundle.responsePayload: String
    get()= this.getString(REQUEST_PAYLOAD)
    set(value) { this.putString(REQUEST_PAYLOAD, value) }