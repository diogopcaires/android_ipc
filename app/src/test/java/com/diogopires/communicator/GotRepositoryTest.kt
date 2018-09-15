package com.diogopires.communicator

import com.diogopires.communicator.data.GotRepository
import com.diogopires.communicator.data.GotRepositoryImpl
import com.mooveit.library.Fakeit
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.mockkObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class GotRepositoryTest {

    private lateinit var gotRepository: GotRepository

    @Before
    fun setup() {
        mockkObject(Fakeit)
        var fakeIt = mockkClass(Fakeit::class)
        every { Fakeit.init() } returns fakeIt
        gotRepository = GotRepositoryImpl()
    }

    @Test
    fun when_get_quota_called_should_return_a_correct_string() {
        every { Fakeit.gameOfThrones().quote() } returns "pipitu"

        gotRepository.getQuote().subscribe({ quota ->
            Assert.assertTrue(quota == "pipitu")
        }, { ex ->
            throw ex
        })
    }

    @Test
    fun when_get_quote_and_fakeit_throw_a_exception_should_subscribe_call_exception_block() {
        every { Fakeit.gameOfThrones().quote() } throws Exception("pipitu exception")
        gotRepository.getQuote().subscribe({ quote ->
            Assert.assertTrue(false)
        }, { ex ->
            Assert.assertTrue(true)
        })
    }
}