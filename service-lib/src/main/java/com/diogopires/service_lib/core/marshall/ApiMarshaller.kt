package com.diogopires.service_lib.core.marshall

import com.diogopires.service_lib.core.data.Order
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken




object ApiMarshaller : Marshaller<String,MutableList<Order>>{
    private val gson = Gson()

    override fun unmarshaller(t: MutableList<Order>): String {
        return gson.toJson(t).toString()
     }

    override fun marshaller(r: String): MutableList<Order> {
        val listType = object : TypeToken<ArrayList<Order>>() {

        }.type
        return gson.fromJson(r,listType)
    }

}