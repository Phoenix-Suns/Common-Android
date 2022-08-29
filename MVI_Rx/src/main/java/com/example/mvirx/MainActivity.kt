package com.example.mvirx

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.nghiatl.common.view.ontouch.SlideVerticalDragTouchListener


class MainActivity : AppCompatActivity() {
    lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val container = findViewById<ConstraintLayout>(R.id.container)
        container.setOnTouchListener(SlideVerticalDragTouchListener(container, container.rootView))

//        val textView = findViewById<TextView>(R.id.textView)
//        textView.setOnTouchListener(SlideVerticalDragTouchListener(textView))
    }
}
