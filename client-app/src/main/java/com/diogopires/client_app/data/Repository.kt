package com.diogopires.client_app.data


import io.reactivex.Single



interface Repository{
    fun getCharacter(): Single<String>
    fun getHouse(): Single<String>
    fun getCity(): Single<String>
}