package com.example.democommon.ui.common_library.samples

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.example.democommon.R

private const val TYPE_TITLE = 1
private const val TYPE_ROW = 2

class RowsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data: ArrayList<BaseRowItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == TYPE_ROW) {
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
        if (item is RowData) return TYPE_ROW
        else if (item is TitleData) return TYPE_TITLE
        return super.getItemViewType(position)
    }

    private class RowViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {

        private lateinit var item: RowData

        fun bindData(item: RowData) {
            this.item = item


            item.iconRes?.let {
                parent.findViewById<ImageView>(R.id.imageViewIcon).setImageResource(it)
            }

            val titleTextView = parent.findViewById<TextView>(R.id.textView)
            titleTextView.text = item.title


            // Events
            parent.setOnClickListener {
                item.action?.invoke(absoluteAdapterPosition, item)
            }
        }
    }

    private class TitleViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {

        private lateinit var item: TitleData

        fun bindData(item: TitleData) {
            this.item = item


            item.iconRes?.let {
                parent.findViewById<ImageView>(R.id.imageViewIcon).setImageResource(it)
            }

            val titleTextView = parent.findViewById<TextView>(R.id.textView)
            titleTextView.text = item.title


            // Events
            parent.setOnClickListener {
                item.action?.invoke(absoluteAdapterPosition, item)
            }
        }
    }


    class TitleData(
        @DrawableRes iconRes: Int? = null,
        title: String?,
        val action: ((position: Int, item: TitleData) -> Unit)? = null
    ) : BaseRowItem(iconRes, title)

    class RowData(
        @DrawableRes iconRes: Int? = null,
        title: String?,
        val action: ((position: Int, item: RowData) -> Unit)? = null
    ) : BaseRowItem(iconRes, title)

    abstract class BaseRowItem(
        @DrawableRes val iconRes: Int?,
        val title: String?,
    )
}