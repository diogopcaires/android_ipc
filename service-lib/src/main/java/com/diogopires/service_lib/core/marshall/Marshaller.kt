package com.diogopires.service_lib.core.marshall

/**
 * Created by diogosilva on 23/07/18.
 */
interface Marshaller<T,R> {
    fun marshaller(t:T):R
    fun unmarshaller(r:R):T
}