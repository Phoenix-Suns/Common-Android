package self.tranluunghia.demomvi.domain.usecase

import kotlinx.coroutines.flow.Flow
import self.tranluunghia.demomvi.core.basemvi.UseCase
import self.tranluunghia.demomvi.core.entity.DataState
import self.tranluunghia.demomvi.domain.model.GithubRepo
import self.tranluunghia.demomvi.domain.repository.GithubUserRepository
import javax.inject.Inject

class GetRepoListUseCase @Inject constructor(
    private val repository: GithubUserRepository
) : UseCase<GetRepoListUseCase.Params, Flow<DataState<List<GithubRepo>>>> {

    override fun invoke(params: Params): Flow<DataState<List<GithubRepo>>> =
        repository.getRepoList(params.keyWork, params.page, params.perPage)

    class Params(
        val perPage: Int,
        val page: Int,
        val keyWork: String
    )
}