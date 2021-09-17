package self.tranluunghia.demomvi.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import self.tranluunghia.demomvi.core.entity.DataState
import self.tranluunghia.demomvi.core.helper.extention.repositoryExecutor
import self.tranluunghia.demomvi.data.api.service.GithubWS
import self.tranluunghia.demomvi.data.mapper.GithubRepoResponseMapper
import self.tranluunghia.demomvi.data.mapper.GithubUserResponseMapper
import self.tranluunghia.demomvi.domain.model.GithubRepo
import self.tranluunghia.demomvi.domain.model.GithubUser
import self.tranluunghia.demomvi.domain.repository.GithubUserRepository
import javax.inject.Inject

class GithubUserRepositoryImpl @Inject constructor(
    private val context: Context,
    private val retrofit: Retrofit,
    private val githubWS: GithubWS,
    private val githubUserResponseMapper: GithubUserResponseMapper,
    private val githubRepoResponseMapper: GithubRepoResponseMapper
) : GithubUserRepository {

    /*override fun getUserDetail(username: String): Flow<DataState<GithubUser>> = flow {
        val githubUserResponse = githubWS.getGitHubUserDetail(username)
        githubUserResponseMapper.map(githubUserResponse)
    }*/

    override fun getUserDetail(username: String): Flow<DataState<GithubUser>> =
        repositoryExecutor(
            context = context,
            retrofit = retrofit,
            apiCall = {
                githubWS.getGitHubUserDetail(username)
            },
            transform = {
                githubUserResponseMapper.map(it)
            }
        )

    override fun getRepoList(
        keyWork: String,
        page: Int,
        perPage: Int
    ): Flow<DataState<List<GithubRepo>>> =
        repositoryExecutor(
            context = context,
            retrofit = retrofit,
            apiCall = {
                githubWS.getRepoList(keyWork, page, perPage)
            },
            transform = { response ->
                val listRepo = ArrayList<GithubRepo>()
                response.items.forEach {
                    listRepo.add(githubRepoResponseMapper.map(it))
                }
                listRepo
            }
        )
}