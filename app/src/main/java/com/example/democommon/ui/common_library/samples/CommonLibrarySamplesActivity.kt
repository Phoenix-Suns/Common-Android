package com.example.democommon.ui.common_library.samples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.democommon.R
import com.nghiatl.common.application.ApplicationUtil
import com.nghiatl.common.dialog.ProgressBarDialog
import com.nghiatl.common.extension.delayFinishActivity
import kotlinx.android.synthetic.main.activity_common_library_samples.*


class CommonLibrarySamplesActivity : AppCompatActivity() {
    private lateinit var adapter: RowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_library_samples)

        setupViews()
        setEvents()
    }

    private fun setupViews() {
        adapter = RowsAdapter()
        recyclerView.adapter = adapter


        val data = arrayListOf(
            RowsAdapter.TitleData(R.string.activity_utils),
            RowsAdapter.RowData(R.string.delay_finish_activity) { _, _ ->
                delayFinishActivity(2F, null)
            },
            RowsAdapter.RowData(R.string.restart_application) { _, _ ->
                //ApplicationUtil.restartApplication(this)
                ApplicationUtil.restartApplication2(this)
            },
            RowsAdapter.RowData(R.string.progressbar_dialog) { _, _ ->
                ProgressBarDialog(this).showProgress(true)
            },
            RowsAdapter.RowData(R.string.crash_handle) { _, _ ->
                throw RuntimeException("This is a crash")
            },
            RowsAdapter.RowData(R.string.check_permission) { _, _ ->
                addFragment(CheckPermissionsFragment())
            },
        )
        adapter.setData(data)
    }

    private fun setEvents() {

    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment, fragment::class.java.simpleName)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

}