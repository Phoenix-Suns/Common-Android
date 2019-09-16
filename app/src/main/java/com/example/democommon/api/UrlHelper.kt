package com.example.democommon.api

import com.example.democommon.BuildConfig

object UrlHelper {
    const val BASE_URL = BuildConfig.BASE_URL

    const val RADIO_600x600 = "600x600/" //radio
    const val RADIO_160x160 = "160x160/" //radio
    const val NEWS_240x150 = "240x150/" //post
    const val VIDEO_160x100 = "160x100/" //video
    const val VIDEO_600x374 = "600x375/" //video


    const val ENDPOINT_LIVE_INFO = "radio/liveinfo"
    const val ENDPOINT_CONTENT_LASTED = "elastic/content/lasted"
    /* const val ENDPOINT_CONTENT_RADIO = "elastic/content/feature?type=radio"
     const val ENDPOINT_CONTENT_VIDEO = "elastic/content/feature?type=video"
     const val ENDPOINT_CONTENT_POST = "elastic/content/feature?type=post"*/
    const val ENDPOINT_CONTENT_RADIO = "radio/radio"
    const val ENDPOINT_CONTENT_VIDEO = "video/video"
    const val ENDPOINT_CONTENT_POST = "post/post"

    const val ENDPOINT_SEARCH_POST = "elastic/content/search?type=post"
    const val ENDPOINT_SEARCH_RADIO = "elastic/content/search?type=radio"
    const val ENDPOINT_SEARCH_VIDEO = "elastic/content/search?type=video"
    const val ENDPOINT_SEARCH = "elastic/content/search"

    const val ENDPOINT_APP_INFO = "setting/setting/info"
    const val ENDPOINT_REFRESH_TOKEN = "auth/refresh-token"
    const val ENDPOINT_LOGIN = "auth/login"
    const val ENDPOINT_LOGIN_FACEBOOK = "auth/facebook"
    const val ENDPOINT_LOGIN_GOOGLE = "auth/google"
    const val ENDPOINT_REGISTER = "auth/register"
    const val ENDPOINT_UPDATE_INFO = "users/{userId}"
    const val ENDPOINT_UPLOAD_AVATAR = "extra/upload/avatar"

    const val ENDPOINT_RADIO = "radio/radio"
    const val ENDPOINT_RADIO_CATEGORY = "radio/category"
    const val ENDPOINT_RADIO_FAVORITE = "radio/wishlist"
    const val ENDPOINT_RADIO_DETAIL = "radio/radio/{radioUID}"
    const val ENDPOINT_RADIO_COMMENT = "radio/comment"
    const val ENDPOINT_RADIO_COMMENT_LIKE = "radio/comment-like"
    const val ENDPOINT_RADIO_FEATURE = "radio/radio/?is_feature=true"

    const val ENDPOINT_VIDEO_DETAIL = "video/video/{videoId}"

    const val ENDPOINT_RADIO_PLAYLIST = "radio/playlist"
    const val ENDPOINT_RADIO_PLAYLIST_ITEM = "radio/playlist-item"

    const val CODE_SUCCESS = 200
    const val PERPAGE_GET_ALL = -1

    const val ENDPOINT_NEWS = "post/post"
    const val ENDPOINT_NEWS_CATEGORY = "post/category"
    const val ENDPOINT_NEWS_BOOKMARK = "post/bookmark"
    const val ENDPOINT_NEWS_HIGHLIGHT = "elastic/content/feature?type=post"
    const val ENDPOINT_NEWS_COMMENT = "post/comment"
    const val ENDPOINT_NEWS_COMMENT_LIKE = "post/comment-like"

    const val ENDPOINT_VIDEO = "video/video"
    const val ENDPOINT_VIDEO_FAVORITE = "video/wishlist"
    const val ENDPOINT_VIDEO_CATEGORY = "video/category"
    const val ENDPOINT_VIDEO_HIGHLIGHT = "elastic/content/feature?type=video"
    const val ENDPOINT_VIDEO_COMMENT = "video/comment"
    const val ENDPOINT_VIDEO_COMMENT_LIKE = "video/comment-like"

    const val ENDPOINT_BIBLE = "bible/bible"
    const val ENDPOINT_BIBLE_PARAGRAPH = "bible/bible-chapter"
    const val ENDPOINT_BIBLE_SENTENCE = "bible/bible-verse"
    const val ENDPOINT_BIBLE_BOOKMARK = "bible/bible-bookmark"

    const val ENDPOINT_CHANT = "hymn/hymn"
    const val ENDPOINT_CHANT_CATEGORY = "hymn/category"

    const val ENDPOINT_NOTIFICATION = "notification/notification"

    const val ENDPOINT_USER = "users"
    const val ENDPOINT_USER_TIMELINE = "user/timeline"

    const val ENDPOINT_FOLLOWER_FOLLOWING = "user/follow/follower-following"

    const val ENDPOINT_FOLLOW = "user/follow"
    const val ENDPOINT_FOLLOW_CANCEL = "user/follow/cancel"

    const val ENDPOINT_REPORT = "report/report"

}