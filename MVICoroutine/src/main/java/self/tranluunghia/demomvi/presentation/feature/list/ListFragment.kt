package self.tranluunghia.demomvi.presentation.feature.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import self.tranluunghia.demomvi.R
import self.tranluunghia.demomvi.core.basemvi.BaseMVIFragment
import self.tranluunghia.demomvi.core.helper.extention.logE
import self.tranluunghia.demomvi.core.helper.extention.singleClick
import self.tranluunghia.demomvi.databinding.ListFragmentBinding
import self.tranluunghia.demomvi.presentation.feature.adapter.RepoListAdapter

@AndroidEntryPoint
class ListFragment : BaseMVIFragment<ListViewModel, ListFragmentBinding>() {

    companion object {
        fun newInstance() = ListFragment()
    }

    override val viewModel: ListViewModel by viewModels()
    override fun layout(): Int = R.layout.list_fragment
    override fun viewModelClass(): Class<ListViewModel> = ListViewModel::class.java

    val repoListAdapter by lazy { RepoListAdapter(ArrayList()) }

    override fun setUpViews() {
        super.setUpViews()
        binding.recyclerViewRepo.adapter = repoListAdapter
    }

    override fun setEvents() {
        super.setEvents()

        binding.buttonSearch.singleClick {
            val searchKey = binding.editTextSearchKey.text.toString().trim()
            viewModel.sendIntent(ListContract.ListIntent.GetList(searchKey))
        }
    }

    override fun subscribeUI() {
        super.subscribeUI()

        lifecycleScope.launchWhenCreated {
            viewModel.callbackState.collect {
                handleStates(it)
            }
        }
    }

    private fun handleStates(state: ListContract.ListState) {
        when (state) {
            is ListContract.ListState.ShowUserInfo -> {
                logE(state.userInfo.name)
            }
            is ListContract.ListState.ShowRepoList -> {
                repoListAdapter.updateItems(state.repoList)
            }
        }
    }
}