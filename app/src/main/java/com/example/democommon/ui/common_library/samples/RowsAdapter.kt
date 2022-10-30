package com.example.democommon.ui.common_library.samples

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.example.democommon.R
import com.google.gson.Gson

class RowsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            holder.bindData(item)
        }
        if (holder is TitleViewHolder && item is TitleData) {
            holder.bindData(item)
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

        fun bindData(item: RowData) {
            this.item = item

            parent.findViewById<TextView>(R.id.textView).setText(item.titleId)
            parent.setOnClickListener {
                item.action?.invoke(absoluteAdapterPosition, item)
            }
        }
    }

    private class TitleViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {

        private lateinit var item: TitleData

        fun bindData(item: TitleData) {
            this.item = item

            parent.findViewById<TextView>(R.id.textView).setText(item.titleId)
            parent.setOnClickListener {
                item.action?.invoke(absoluteAdapterPosition, item)
            }
        }
    }


    class TitleData(
        @StringRes titleId: Int,
        val action: ((position: Int, item: TitleData) -> Unit)? = null
    ) : BaseRowItem(titleId)

    class RowData(
        @StringRes titleId: Int,
        val action: ((position: Int, item: RowData) -> Unit)? = null
    ) : BaseRowItem(titleId)

    abstract class BaseRowItem(
        @StringRes val titleId: Int
    )
}