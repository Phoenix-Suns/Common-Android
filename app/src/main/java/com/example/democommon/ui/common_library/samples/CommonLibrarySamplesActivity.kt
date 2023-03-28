package com.example.democommon.ui.common_library.samples

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.democommon.R
import com.example.democommon.databinding.ActivityCommonLibrarySamplesBinding
import com.nghiatl.common.application.ApplicationUtil
import com.nghiatl.common.dialog.*
import com.nghiatl.common.extension.delayFinishActivity
import com.nghiatl.common.extension.showLongToast
import java.util.*


class CommonLibrarySamplesActivity : AppCompatActivity() {
    private lateinit var adapter: RowsAdapter
    protected lateinit var binding: ActivityCommonLibrarySamplesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_common_library_samples)

        setupViews()
        setEvents()
    }

    private fun setupViews() {
        adapter = RowsAdapter()
        binding.recyclerView.adapter = adapter


        val data = arrayListOf(
            RowsAdapter.RowData(title = getString(R.string.app_name)) { _, _ ->

            },
            RowsAdapter.TitleData(title = getString(R.string.activity_utils)),
            RowsAdapter.RowData(title = getString(R.string.delay_finish_activity)) { _, _ ->
                delayFinishActivity(2F, null)
            },
            RowsAdapter.RowData(title = getString(R.string.restart_application)) { _, _ ->
                //ApplicationUtil.restartApplication(this)
                ApplicationUtil.restartApplication2(this)
            },
            RowsAdapter.RowData(title = getString(R.string.crash_handle)) { _, _ ->
                throw RuntimeException("This is a crash")
            },
            RowsAdapter.RowData(title = getString(R.string.check_permission)) { _, _ ->
                addFragment(CheckPermissionsFragment())
            },

            RowsAdapter.TitleData(title = getString(R.string.dialog_utils)),
            RowsAdapter.RowData(title = getString(R.string.open_location_setting)) { _, _ ->
                DialogUtil.showLocationSettingAlert(
                    context = this,
                    title = "We need LOCATION",
                    message = "Do you want open LOCATION SETTING?",
                    positiveButtonText = "Yes",
                    negativeButtonText = "No"
                )
            },
            RowsAdapter.RowData(title = getString(R.string.open_mobile_network_setting)) { _, _ ->
                DialogUtil.showNetworkSettingAlert(
                    context = this,
                    title = "We need NETWORK",
                    message = "Do you want open MOBILE NETWORK SETTING?",
                    positiveButtonText = "Yes",
                    negativeButtonText = "No"
                )
            },
            RowsAdapter.RowData(title = getString(R.string.progressbar_dialog)) { _, _ ->
                ProgressBarDialog(this).showProgress(true)
            },
            RowsAdapter.RowData(title = getString(R.string.simple_progressbar_dialog)) { _, _ ->
                DialogUtil.showLoadingDialog(this, true)
            },
            RowsAdapter.RowData(title = getString(R.string.message_progressbar_dialog)) { _, _ ->
                DialogUtil.showMessageProgressDialog(
                    progressDialog = ProgressDialog(this),
                    showDialog = true,
                    allowCancel = true,
                    title = "Progress title. Hello",
                    message = "Progress message. Wellcome to new world"
                )
            },
            RowsAdapter.RowData(title = getString(R.string.dialog_by_add_view)) { _, _ ->
                val loadingDlg = LoadingDialogView(findViewById(R.id.container))
                loadingDlg.show(true)
            },
            RowsAdapter.RowData(title = getString(R.string.date_picker_dialog)) { _, _ ->
                val dlg = DatePickerFragment.newInstance(Calendar.getInstance())
                dlg.style = android.R.style.Theme_Material_Dialog_Alert
                dlg.listener = { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    showLongToast("$dayOfMonth - $monthOfYear - $year")
                }
                dlg.show(supportFragmentManager, "nghia")
            },
            RowsAdapter.RowData(title = getString(R.string.time_picker_dialog)) { _, _ ->
                val dlg = TimePickerFragment.newInstance(Calendar.getInstance())
                dlg.setStyle(DatePickerDialog.THEME_TRADITIONAL)
                dlg.setListener { view, hour, minute ->
                    showLongToast("$hour - $minute")
                }

                dlg.show(supportFragmentManager, "nghia")
            },
            // todo datepicker dialog inline
            // todo timepicker dialog inline

            RowsAdapter.RowData(title = getString(R.string.edittext_dialog)) { _, _ ->
                val dlg = EditTextDialogFragment.newInstance("What your name?", "Black Adam")
                dlg.listener = {
                    showLongToast(it)
                }
                dlg.show(supportFragmentManager, dlg::class.simpleName)
            },
            RowsAdapter.RowData(title = getString(R.string.persent_dialog)) { _, _ ->
                val dlg = PercentDialogFragment.newInstance(
                    title = "Working start",
                    allowCancel = true
                )
                dlg.listener = object : PercentDialogFragment.IListener {
                    override fun onCancelClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }
                }
                dlg.show(supportFragmentManager, dlg::class.simpleName)

                dlg.setProgress(50)
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