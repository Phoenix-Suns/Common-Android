package com.nghiatl.common.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.nghiatl.common.*
import com.nghiatl.common.dialog.*
import kotlinx.android.synthetic.main.activity_demo_list.*


class DemoListActivity : AppCompatActivity() {

    val pickImageDlg: PickImageDialog = PickImageDialog("Chọn nguồn: ")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_list)
    }

    fun pickImage(v: View) {
        LoadingDialog.show(this)
    }

    fun oneClick(v: View) {
        //AnimationUtil.runXML(imageView, R.anim.enter)
        AnimationUtil.runXML(imageView, R.anim.fade_slide_out_top)
    }

    fun twoClick(v: View) {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = pickImageDlg.getPickImageResultUri(data)
        Log.e("Nghia", result.path)

        imageView.setImageURI(result)
    }
}
