package com.diogopires.service_lib.client
import com.diogopires.service_lib.core.data.Order

import io.reactivex.Single



interface ApiRequest{
    fun getList(): Single<List<Order>>
}