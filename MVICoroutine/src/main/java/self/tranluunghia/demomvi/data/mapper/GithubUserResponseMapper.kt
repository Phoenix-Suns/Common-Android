package self.tranluunghia.demomvi.data.mapper

import self.tranluunghia.demomvi.core.basemvi.Mapper
import self.tranluunghia.demomvi.data.model.response.GithubUserResponse
import self.tranluunghia.demomvi.domain.model.GithubUser
import javax.inject.Inject

class GithubUserResponseMapper @Inject constructor() : Mapper<GithubUserResponse, GithubUser> {
    override fun map(from: GithubUserResponse) = GithubUser(
        role = from.role,
        isActive = from.isActive,
        dialCode = from.dialCode,
        sex = from.sex,
        name = from.name,
        createdAt = from.createdAt,
        id = from.id,
        type = from.type,
        email = from.email,
        phone = from.phone,
        picture = from.picture,
        dob = from.dob,
        totalFollower = from.totalFollower,
        totalFollowing = from.totalFollowing,
        isFollow = from.isFollow
    )
}