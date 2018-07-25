package com.diogopires.service_lib.core.data

import java.math.BigDecimal

//example of order data class
data class Order(val statusCode:Int,val value:BigDecimal,val description:String)
