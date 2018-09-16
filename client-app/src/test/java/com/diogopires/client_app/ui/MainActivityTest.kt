package com.diogopires.client_app.ui

import com.diogopires.client_app.data.RepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @MockK
    private lateinit var repository: RepositoryImpl
    private lateinit var activity: MainActivity
    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Before
    fun setup() {
        // create and injects mocks into object annotated with @InjectMocks
        MockKAnnotations.init(this, relaxUnitFun = true) //


        stopKoin()

        startKoin(listOf(module {
            factory { repository }
        }))


        val activityController = Robolectric.buildActivity(MainActivity::class.java)
        activity = activityController.get()

        activityController.setup()


    }

    @Test
    fun when_call_repository_should_input_result_in_text_box() {

        every { repository.getQuote() } answers { Single.defer({ Single.just("pipitu") }) }

        activity.sender_btn.performClick()

        assert(activity.client_textBox.text == "pipitu")
    }


    @Test
    fun when_call_repository_and_throws_a_exception_should_show_a_toast() {

        every { repository.getQuote() } answers { Single.error(Exception("pipitu error")) }

        activity.sender_btn.performClick()

        Assert.assertEquals("Error", ShadowToast.getTextOfLatestToast())

    }

    class RxImmediateSchedulerRule : TestRule {

        override fun apply(base: Statement, d: Description): Statement {
            return object : Statement() {
                @Throws(Throwable::class)
                override fun evaluate() {
                    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                    RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                    RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                    try {
                        base.evaluate()
                    } finally {
                        RxJavaPlugins.reset()
                        RxAndroidPlugins.reset()
                    }
                }
            }
        }
    }


}