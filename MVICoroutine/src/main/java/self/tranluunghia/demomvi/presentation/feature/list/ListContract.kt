package self.tranluunghia.demomvi.presentation.feature.list

import self.tranluunghia.demomvi.core.basemvi.BaseMVIContract
import self.tranluunghia.demomvi.domain.model.GithubRepo
import self.tranluunghia.demomvi.domain.model.GithubUser

sealed class ListContract {
    sealed class ListIntent: BaseMVIContract.BaseIntent {
        class GetList(val searchKey: String) : ListIntent()
    }

    sealed class ListState: BaseMVIContract.BaseState {
        class ShowUserInfo(val userInfo: GithubUser) : ListState()
        class ShowRepoList(val repoList: List<GithubRepo>) : ListState()

    }
}