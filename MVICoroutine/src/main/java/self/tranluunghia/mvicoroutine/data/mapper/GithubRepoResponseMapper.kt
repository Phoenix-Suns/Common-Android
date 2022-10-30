package self.tranluunghia.mvicoroutine.data.mapper

import self.tranluunghia.mvicoroutine.core.basemvi.Mapper
import self.tranluunghia.mvicoroutine.data.model.response.GithubRepoResponse
import self.tranluunghia.mvicoroutine.domain.model.GithubRepo
import javax.inject.Inject

class GithubRepoResponseMapper @Inject constructor() : Mapper<GithubRepoResponse, GithubRepo> {
    override fun map(from: GithubRepoResponse): GithubRepo {
        return GithubRepo(
            allowForking = from.allowForking,
            archiveUrl = from.archiveUrl,
            archived = from.archived,
            assigneesUrl = from.assigneesUrl,
            blobsUrl = from.blobsUrl,
            branchesUrl = from.branchesUrl,
            cloneUrl = from.cloneUrl,
            collaboratorsUrl = from.collaboratorsUrl,
            commentsUrl = from.commentsUrl,
            commitsUrl = from.commitsUrl,
            compareUrl = from.compareUrl,
            contentsUrl = from.contentsUrl,
            contributorsUrl = from.contributorsUrl,
            createdAt = from.createdAt,
            defaultBranch = from.defaultBranch,
            deploymentsUrl = from.deploymentsUrl,
            description = from.description,
            disabled = from.disabled,
            downloadsUrl = from.downloadsUrl,
            eventsUrl = from.eventsUrl,
            fork = from.fork,
            forks = from.forks,
            forksCount = from.forksCount,
            forksUrl = from.forksUrl,
            fullName = from.fullName,
            gitCommitsUrl = from.gitCommitsUrl,
            gitRefsUrl = from.gitRefsUrl,
            gitTagsUrl = from.gitTagsUrl,
            gitUrl = from.gitUrl,
            hasDownloads = from.hasDownloads,
            hasIssues = from.hasIssues,
            hasPages = from.hasPages,
            hasProjects = from.hasProjects,
            hasWiki = from.hasWiki,
            homepage = from.homepage,
            hooksUrl = from.hooksUrl,
            htmlUrl = from.htmlUrl,
            id = from.id,
            issueCommentUrl = from.issueCommentUrl,
            issueEventsUrl = from.issueEventsUrl,
            issuesUrl = from.issuesUrl,
            keysUrl = from.keysUrl,
            labelsUrl = from.labelsUrl,
            language = from.language,
            languagesUrl = from.languagesUrl,
            mergesUrl = from.mergesUrl,
            milestonesUrl = from.milestonesUrl,
            mirrorUrl = from.mirrorUrl,
            name = from.name,
            nodeId = from.nodeId,
            notificationsUrl = from.notificationsUrl,
            openIssues = from.openIssues,
            openIssuesCount = from.openIssuesCount, 
            owner = GithubRepo.Owner(
                avatarUrl = from.owner?.avatarUrl,
                eventsUrl = from.owner?.eventsUrl,
                followersUrl = from.owner?.followersUrl,
                followingUrl = from.owner?.followingUrl,
                gistsUrl = from.owner?.gistsUrl,
                gravatarId = from.owner?.gravatarId,
                htmlUrl = from.owner?.htmlUrl,
                id = from.owner?.id,
                login = from.owner?.login,
                nodeId = from.owner?.nodeId,
                organizationsUrl = from.owner?.organizationsUrl,
                receivedEventsUrl = from.owner?.receivedEventsUrl,
                reposUrl = from.owner?.reposUrl,
                siteAdmin = from.owner?.siteAdmin,
                starredUrl = from.owner?.starredUrl,
                subscriptionsUrl = from.owner?.subscriptionsUrl,
                type = from.owner?.type,
                url = from.owner?.url
            ),
        )
    }
}