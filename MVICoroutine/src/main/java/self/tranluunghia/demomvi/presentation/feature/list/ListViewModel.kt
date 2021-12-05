package self.tranluunghia.demomvi.presentation.feature.list
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import self.tranluunghia.demomvi.core.basemvi.BaseMVIViewModel
import self.tranluunghia.demomvi.core.entity.DataState
import self.tranluunghia.demomvi.domain.model.Paging
import self.tranluunghia.demomvi.domain.usecase.GetRepoListUseCase
import self.tranluunghia.demomvi.domain.usecase.GetUserDetailUseCase
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getRepoListUseCase: GetRepoListUseCase
) : BaseMVIViewModel<ListContract.ListIntent, ListContract.ListState>() {

    private var searchKey: String = ""
    private var repoListPaging: Paging = Paging()

    override fun handleIntents(viewIntent: ListContract.ListIntent) {
        when (viewIntent) {
            is ListContract.ListIntent.GetList -> {
                getListRepo(viewIntent.searchKey)
            }
        }
    }

    private fun getListRepo(searchKey: String) {
        this.searchKey = searchKey

        ioScope.launch {
            getRepoListUseCase.invoke(GetRepoListUseCase.Params(
                perPage = repoListPaging.perPage, page = repoListPaging.page, keyWork = searchKey
            ))
                .collect { dataState ->
                    when (dataState.status) {
                        DataState.Status.SUCCESS -> {
                            callbackState(ListContract.ListState.ShowRepoList(dataState.data!!))
                        }
                        DataState.Status.ERROR -> {

                        }
                        DataState.Status.LOADING -> {

                        }
                    }
                }
        }
    }

    private fun getUserInfo() {
        ioScope.launch {
            getUserDetailUseCase.invoke(GetUserDetailUseCase.Params("ahmedrizwan"))
                .collect { dataState ->
                    when (dataState.status) {
                        DataState.Status.SUCCESS -> {
                            callbackState(ListContract.ListState.ShowUserInfo(dataState.data!!))
                        }
                        DataState.Status.ERROR -> {

                        }
                        DataState.Status.LOADING -> {

                        }
                    }
                }
        }
    }
}
