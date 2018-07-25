package com.diogopires.service_lib

import android.app.Application
import android.content.Context

class LibApp : Application() {
    /**
     * Constructor.
     */
    init {
        instance = this
    }

    companion object {

        /** Instance of the current application.  */
        private lateinit var instance: LibApp

        /**
         * Gets the application context.
         *
         * @return the application context
         */
        val context: Context
            get() = instance
    }

}