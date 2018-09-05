package com.diogopires.communicator.data

import io.reactivex.Single


interface GotRepository {

    fun getQuote(): Single<String>
    fun getCharacter(): Single<String>
    fun getHouse(): Single<String>
    fun getCity(): Single<String>
}