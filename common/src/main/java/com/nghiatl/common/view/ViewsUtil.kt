package com.nghiatl.common.view

import android.content.Context
import androidx.core.content.ContextCompat
import com.nghiatl.common.R
import android.graphics.PorterDuff
import android.widget.TextView
import androidx.annotation.DimenRes
import android.util.TypedValue
import androidx.annotation.ColorRes
import android.os.Build
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.ImageViewCompat

/**
 * Created by Nghia on 2/19/2018.
 */
object ViewsUtil {
    fun changeIconDrawableToGray(context: Context?, drawable: Drawable?) {
        if (drawable != null) {
            drawable.mutate()
            drawable.setColorFilter(
                ContextCompat.getColor(context!!, R.color.dark_gray),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }

    /**
     * Check Position Inside View
     */
    fun isInsideView(containView: View, x: Float, y: Float): Boolean {
        return containView.left + containView.translationX <= x && x <= containView.right + containView.translationX && containView.top + containView.translationY <= y && y <= containView.bottom + containView.translationY
    }

    fun setTextSize(textView: TextView, context: Context, @DimenRes dimenResId: Int) {
        textView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            context.resources.getDimensionPixelSize(dimenResId).toFloat()
        )
    }

    fun setTint(imageView: ImageView, context: Context, @ColorRes colorRes: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // https://stackoverflow.com/questions/20121938/how-to-set-tint-for-an-image-view-programmatically-in-android
            imageView.setColorFilter(context.getColor(colorRes), PorterDuff.Mode.MULTIPLY)
        } else {
            // https://stackoverflow.com/questions/39437254/how-to-use-setimagetintlist-on-android-api-21
            val csl = AppCompatResources.getColorStateList(context, colorRes)
            ImageViewCompat.setImageTintList(imageView, csl)
        }
    }

    /**
     * ex:
    CommonUtils.showLoading(
        isLoading = command.isLoading,
        dataSize = viewModel.moments.size,
        listView = binding.homeContent.rvMoment,
        emptyView = binding.homeContent.tvMomentEmpty,
        firstLoadingView = binding.homeContent.pbMomentLoading,
        loadingMoreView = binding.homeContent.pbMomentLoadMore,
        isRefreshing = binding.homeContent.swipeRefreshLayoutMoment.isRefreshing,
        onRefreshingGone = {
            binding.homeContent.swipeRefreshLayoutMoment.isRefreshing = false
        },
        onShowListViewChange = { isShow ->
            if (isShow) {
                binding.homeContent.rvMoment.addOnScrollListener(momentLoadMoreScroll)
                binding.homeContent.rvMoment.addOnScrollListener(momentShowLoadMoreScroll)
            } else {
                binding.homeContent.rvMoment.removeOnScrollListener(momentLoadMoreScroll)
                binding.homeContent.rvMoment.removeOnScrollListener(momentShowLoadMoreScroll)
            }
        }
    )
     */
    fun showLoading(
        isLoading: Boolean,
        dataSize: Int,
        listView: View?,
        emptyView: View?,
        firstLoadingView: View?,
        loadingMoreView: View?,
        isRefreshing: Boolean = false,
        onRefreshingGone: (()->Unit)? = null,
        onShowListViewChange: ((isShow: Boolean)->Unit)? = null,
    ) {
        if (isLoading) {

            if (!isRefreshing) {
                if (dataSize == 0) {
                    firstLoadingView?.visibility = View.VISIBLE
                    listView?.visibility = View.GONE
                    emptyView?.visibility = View.GONE
                } else {
                    loadingMoreView?.visibility = View.VISIBLE
                    onShowListViewChange?.invoke(true)
                }
            }
        } else {
            firstLoadingView?.visibility = View.GONE
            onRefreshingGone?.invoke()
            loadingMoreView?.visibility = View.GONE

            listView?.visibility = View.VISIBLE
            onShowListViewChange?.invoke(false)
        }
    }
}