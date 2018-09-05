package com.diogopires.communicator.data

import com.mooveit.library.Fakeit
import io.reactivex.Single

class GotRepositoryImpl : GotRepository {

    init {
        Fakeit.init()
    }

    override fun getQuote(): Single<String> {
        return Single.just(Fakeit.gameOfThrones().quote())
    }

    override fun getCharacter(): Single<String> {
        return Single.just(Fakeit.gameOfThrones().character())
    }

    override fun getHouse(): Single<String> {
        return Single.just(Fakeit.gameOfThrones().house())
    }

    override fun getCity(): Single<String> {
        return Single.just(Fakeit.gameOfThrones().city())
    }
}