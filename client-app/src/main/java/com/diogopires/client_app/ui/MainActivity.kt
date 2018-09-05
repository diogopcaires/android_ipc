package com.diogopires.client_app.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.diogopires.client_app.R
import com.diogopires.client_app.data.RepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sender_btn.setOnClickListener({
            var api = RepositoryImpl(applicationContext)

            api.getCharacter()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ value ->
                        client_textBox.text = value
                    }, { error ->
                        throw error
                    })
        })

    }


}
