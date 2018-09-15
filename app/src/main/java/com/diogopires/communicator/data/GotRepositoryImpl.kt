package com.diogopires.communicator.data

import com.mooveit.library.Fakeit
import io.reactivex.Single

class GotRepositoryImpl : GotRepository {

    init {
        Fakeit.init()
    }

    override fun getQuote(): Single<String> {
        return Single.create { em ->
            try {
                val result = Fakeit.gameOfThrones().quote()
                em.onSuccess(result)
            } catch (ex: Exception) {
                em.onError(ex)
            }

        }
    }


    override fun getCharacter(): Single<String> {
        return Single.create { em ->
            try {
                val result = Fakeit.gameOfThrones().character()
                em.onSuccess(result)
            } catch (ex: Exception) {
                em.onError(ex)
            }

        }
    }

    override fun getHouse(): Single<String> {
        return Single.create { em ->
            try {
                val result = Fakeit.gameOfThrones().house()
                em.onSuccess(result)
            } catch (ex: Exception) {
                em.onError(ex)
            }

        }
    }

    override fun getCity(): Single<String> {
        return Single.create { em ->
            try {
                val result = Fakeit.gameOfThrones().city()
                em.onSuccess(result)
            } catch (ex: Exception) {
                em.onError(ex)
            }

        }
    }


}