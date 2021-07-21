package com.example.democommon.ui.common_library.samples

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.democommon.R
import com.nghiatl.common.activity.ActivityUtil
import com.nghiatl.common.application.ApplicationUtil
import kotlinx.android.synthetic.main.activity_common_library_samples.*
import kotlinx.android.synthetic.main.item_sample_row.view.*


class CommonLibrarySamplesActivity : AppCompatActivity() {
    private lateinit var adapter: RowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_library_samples)

        setupViews()
        setEvents()
    }

    private fun setupViews() {
        val data = arrayListOf(
            TitleData(R.string.common_sample_activity_utils, null),
            RowData(R.string.common_sample_delay_finish_activity, null),
            RowData(R.string.common_sample_check_permission, null),
            RowData(R.string.common_sample_restart_application, null),
            RowData(R.string.app_name, null),
        )
        adapter = RowsAdapter(null)
        recyclerView.adapter = adapter
        adapter.setData(data)
    }

    private fun setEvents() {
        adapter.onItemClick = { rowData ->
            when (rowData.titleId) {
                R.string.common_sample_delay_finish_activity -> {
                    ActivityUtil.delayFinishActivity(2F, this, CommonLibrarySamplesActivity::class.java)
                }
                R.string.common_sample_check_permission -> addFragment(CheckPermissionsFragment())
                R.string.common_sample_restart_application -> {
                    ApplicationUtil.restartApplication2(this)
                }

            }
        }
    }

    private fun addFragment(fragment: CheckPermissionsFragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment, fragment::class.java.simpleName)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

}

private class TitleData(
    @StringRes titleId: Int,
    navigationClass: String?
) : BaseRowItem(titleId, navigationClass)

private class RowData(
    @StringRes titleId: Int,
    navigationClass: String?
) : BaseRowItem(titleId, navigationClass)

private abstract class BaseRowItem (@StringRes val titleId: Int, val navigationClass: String?)

private class RowsAdapter(
    var onItemClick: ((BaseRowItem) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data: ArrayList<BaseRowItem> = ArrayList()
    val type_title = 1
    val type_row = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == type_row) {
            val itemView = inflater.inflate(R.layout.item_sample_row, parent, false)
            return RowViewHolder(itemView)
        } else {
            val itemView = inflater.inflate(R.layout.item_sample_title, parent, false)
            return TitleViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        if (holder is RowViewHolder && item is RowData) {
            holder.bindData(item, onItemClick)
        }
        if (holder is TitleViewHolder && item is TitleData) {
            holder.bindData(item, onItemClick)
        }
    }

    fun setData(newData: ArrayList<BaseRowItem>) {
        this.data.clear()
        this.data.addAll(newData)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]
        if (item is RowData) return type_row
        else if (item is TitleData) return type_title
        return super.getItemViewType(position)
    }

    private class RowViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {

        private lateinit var item: RowData

        fun bindData(item: RowData, onItemClick: ((RowData) -> Unit)?) {
            this.item = item

            parent.textView.setText(item.titleId)
            parent.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

    private class TitleViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {

        private lateinit var item: TitleData

        fun bindData(item: TitleData, onItemClick: ((TitleData) -> Unit)?) {
            this.item = item

            parent.textView.setText(item.titleId)
            parent.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}
