package self.tranluunghia.demomvi.domain.usecase

import com.nghiatl.common.drawer.DrawerMenuItem
import kotlinx.coroutines.flow.Flow
import self.tranluunghia.demomvi.core.basemvi.UseCase
import self.tranluunghia.demomvi.core.entity.DataState
import self.tranluunghia.demomvi.data.model.response.GithubUserResponse
import self.tranluunghia.demomvi.domain.model.GithubUser
import self.tranluunghia.demomvi.domain.repository.GithubUserRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val githubUserRepository: GithubUserRepository
) : UseCase<GetUserDetailUseCase.Params, Flow<DataState<GithubUser>>> {

    override fun invoke(params: Params): Flow<DataState<GithubUser>> =
        githubUserRepository.getUserDetail(params.username)

    class Params(val username: String)
}