package com.diogopires.client_app.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.diogopires.client_app.R
import com.diogopires.client_app.data.Repository
import com.diogopires.client_app.data.RepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.java.standalone.KoinJavaComponent.inject


class MainActivity : AppCompatActivity() {

    private val repository: Repository  by inject(RepositoryImpl::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sender_btn.setOnClickListener({

            repository.getQuote()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ value ->
                        System.out.println(value)
                        client_textBox.text = value
                    }, {
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    })
        })
    }
}
