package self.tranluunghia.mvicoroutine.presentation.feature.list

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import self.tranluunghia.mvicoroutine.R
import self.tranluunghia.mvicoroutine.core.basemvi.BaseMVIFragment
import self.tranluunghia.mvicoroutine.core.helper.extention.logE
import self.tranluunghia.mvicoroutine.core.helper.extention.singleClick
import self.tranluunghia.mvicoroutine.databinding.ListFragmentBinding
import self.tranluunghia.mvicoroutine.presentation.feature.adapter.RepoListAdapter

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