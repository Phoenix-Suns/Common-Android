package com.example.democommon.ui.common_library.samples

import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.democommon.R
import com.nghiatl.common.activity.ActivityUtil
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
            TitleData("ActivityUtil", null),
            RowData("Delay finish Activity", "test 1"),
            RowData("Check Permission", "test 1"),
        )
        adapter = RowsAdapter(null)
        recyclerView.adapter = adapter
        adapter.setData(data)
    }

    private fun setEvents() {
        adapter.onItemClick = { rowData ->
            when (rowData.title) {
                "Delay finish Activity" -> {
                    ActivityUtil.delayFinishActivity(2F, this, CommonLibrarySamplesActivity::class.java)
                }
                "Check Permission" -> addFragment(CheckPermissionsFragment())
            }
        }
    }

    private fun addFragment(fragment: CheckPermissionsFragment) {
        supportFragmentManager.beginTransaction().add(
            R.id.container, fragment, fragment::class.java.name
        ).commit()
    }

}

private class TitleData(
    title: String,
    val navigationClass: String?
) : BaseRowItem(title)

private class RowData(
    title: String,
    val navigationClass: String?
) : BaseRowItem(title)

private abstract class BaseRowItem (val title: String)

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

            parent.textView.text = item.title
            parent.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

    private class TitleViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {

        private lateinit var item: TitleData

        fun bindData(item: TitleData, onItemClick: ((TitleData) -> Unit)?) {
            this.item = item

            parent.textView.text = item.title
            parent.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}
