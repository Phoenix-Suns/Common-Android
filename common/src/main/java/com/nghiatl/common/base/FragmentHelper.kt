package com.nghiatl.common.base


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nghiatl.common.R
import java.util.*


class FragmentHelper(private var fragmentAction: FragmentAction?) {
    var pageList: ArrayList<Stack<BaseFragment>>? = null
    var pageIndex: Int = 0
    var pageSize: Int = 0
    var layoutId: Int = 0
    private var childPage = -1
    private var fragmentManager: FragmentManager? = null

    var buildFragment: Array<BaseFragment?>

    val currentPageSize: Int
        get() = pageList!![pageIndex].size

    fun getBuildFragments(): Array<BaseFragment?> {
        return buildFragment
    }

    init {
        this.layoutId = fragmentAction!!.containerLayoutId
        this.fragmentManager = fragmentAction!!.setFragmentManager()
        this.buildFragment = fragmentAction!!.initFragment()
        initFragments(buildFragment)
    }

    private fun initFragments(fragments: Array<BaseFragment?>) {
        this.pageList = ArrayList(fragments.size)
        pageSize = fragments.size

        for (fragment in fragments) {
            val stack = Stack<BaseFragment>()
            stack.push(fragment)
            this.pageList!!.add(stack)
        }

        val fragment = pageList!![pageIndex].peek()
        if (fragment.isAdded || fragment.isDetached) {
            this.showFragment(pageIndex)
        } else {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.add(layoutId, fragment)
            transaction.commit()
        }
    }

    fun peek(): BaseFragment {
        return pageList!![pageIndex].peek()
    }

    fun pushFragment(fragment: BaseFragment) {
        val hideFragment = pageList!![pageIndex].peek()
        pageList!![pageIndex].push(fragment)

        fragment.isCurrentScreen = true
        hideFragment.isCurrentScreen = false

        val transaction = fragmentManager!!.beginTransaction()
        transaction.add(layoutId, fragment)
        transaction.hide(hideFragment)
        transaction.commit()

    }

    fun replaceFragment(self: BaseFragment, fragment: Fragment) {
        val fragmentManager = self.fragmentManager
        fragmentManager!!.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                0, 0
            )
            .replace(layoutId, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun popFragmentToRoot(): Boolean {
        val level = pageList!![pageIndex].size - 1
        return popFragment(level)
    }

    @JvmOverloads
    fun popFragment(position: Int = 1): Boolean {
        var level = position
        if (level <= 0) return false
        if (pageList!![pageIndex].size <= level) return false
        val transaction = fragmentManager!!.beginTransaction()

        while (level >= 1) {
            val fragment = pageList!![pageIndex].pop()
            fragment.isCurrentScreen = true
            transaction.remove(fragment)
            level--
        }
        val showFragment = pageList!![pageIndex].peek()
        showFragment.isCurrentScreen = true
        transaction.show(showFragment)
        transaction.commit()
        return true
    }

    fun <E : BaseFragment> popToFragment(aClass: Class<E>): Boolean {
        val level = findIndexFragmentChild(aClass)
        return popFragment(level)
    }

    fun <E : BaseFragment> findIndexFragmentChild(aClass: Class<E>): Int {
        val size = currentPageSize
        var level = 0
        for (i in size - 1 downTo 0) {
            val BaseFragment = pageList!![pageIndex][i]
            if (aClass.simpleName == BaseFragment.javaClass.simpleName) {
                return level
            } else {
                level++
            }
        }
        return -1
    }

    //TODO popKeepCurrent ?
//    fun <E : BaseFragment> popKeepCurrent(aClass: Class<E>)  {
//        var level = findIndexFragmentChild(aClass) - 1
//        if (level <= 0) return
//        if (pageList!![pageIndex].size <= level) return
//
//        while (level >= 1) {
//            val fragment = pageList!![pageIndex].pop()
//            level--
//        }
//    }

    fun showFragment(index: Int) {
        if (index == pageIndex) return
        val showFragment = pageList!![index].peek()
        val hideFragment = pageList!![pageIndex].peek()
        val transaction = fragmentManager!!.beginTransaction()

        if (pageIndex > index) {
            showFragment.isInLeft = true
            hideFragment.isOutLeft = false
        } else {
            showFragment.isInLeft = false
            hideFragment.isOutLeft = true
        }
        showFragment.isCurrentScreen = true
        hideFragment.isCurrentScreen = false

        // TODO: Khong refresh lai UI, ko refresh lai data
        if (showFragment.isDetached || showFragment.isAdded) {
            transaction.show(showFragment)
        } else {
            transaction.add(layoutId, showFragment)
        }
        transaction.hide(hideFragment)

        // TODO; Refresh lai UI, refresh lai data
        //        if(showFragment.isDetached() || showFragment.isAdded()){
        //            transaction.attach(showFragment);
        //        }else{
        //            transaction.add(layoutId, showFragment);
        //        }
        //        transaction.detach(hideFragment);

        transaction.commit()
        pageIndex = index
    }

    fun getPageSizeByIndex(index: Int): Int {
        return pageList!![index].size
    }

    fun setChildPage(childPage: Int) {
        this.childPage = childPage
    }

    fun remove() {
        buildFragment = arrayOf(null)
        fragmentAction = null
        fragmentManager = null
    }

    interface FragmentAction {
        fun initFragment(): Array<BaseFragment?>
        var containerLayoutId: Int
        fun setFragmentManager(): FragmentManager
    }
}
