package com.diogopires.client_app.ui

import com.diogopires.client_app.data.Repository
import com.diogopires.client_app.data.RepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowActivity


@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @MockK
    private lateinit var repository: RepositoryImpl
    private lateinit var activity: MainActivity
    private lateinit var shadowActivity: ShadowActivity

    @Before
    fun setup() {
        // create and injects mocks into object annotated with @InjectMocks
        MockKAnnotations.init(this, relaxUnitFun = true) //

        val activityController = Robolectric.buildActivity(MainActivity::class.java)
        activity = activityController.get()

        activityController.setup()
    }

    @Test
    fun when_call_repository_input_result_in_text_box() {
        every{RepositoryImpl(any())} returns repository
        every { repository.getQuote() } returns Single.just("pipitu")

        activity.sender_btn.performClick()

        Assert.assertTrue(activity.client_textBox.text.equals("pipitu"))
    }


}