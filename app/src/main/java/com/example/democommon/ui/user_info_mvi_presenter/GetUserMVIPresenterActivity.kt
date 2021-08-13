package com.example.democommon.ui.user_info_mvi_presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.democommon.R

// https://www.raywenderlich.com/817602-mvi-architecture-for-android-tutorial-getting-started
class GetUserMVIPresenterActivity : AppCompatActivity() {
    private lateinit var presenter: GetUserMVIPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_user_mvipresenter)

        presenter = GetUserMVIPresenter(this)
        presenter.bind(this)
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

    fun render(state: MainState) {
        when(state) {
            is MainState.DataState -> renderDataState(state)
            is MainState.LoadingState -> renderLoadingState()
        }
    }

    private fun renderDataState(dataState: MainState.DataState) {
        //Render movie list
        Log.e("///", dataState.data.name)
        findViewById<TextView>(R.id.textView).text = dataState.data.name
    }

    private fun renderLoadingState() {
        findViewById<TextView>(R.id.textView).text = "Loading..."
    }
}