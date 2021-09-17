package self.tranluunghia.demomvi.domain.repository

import kotlinx.coroutines.flow.Flow
import self.tranluunghia.demomvi.core.entity.DataState
import self.tranluunghia.demomvi.data.model.response.GithubUserResponse
import self.tranluunghia.demomvi.domain.model.GithubRepo
import self.tranluunghia.demomvi.domain.model.GithubUser

interface GithubUserRepository {
    fun getUserDetail(username: String): Flow<DataState<GithubUser>>
    fun getRepoList(keyWork: String, page: Int, perPage: Int): Flow<DataState<List<GithubRepo>>>
}