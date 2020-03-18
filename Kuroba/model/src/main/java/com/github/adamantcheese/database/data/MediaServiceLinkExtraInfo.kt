package com.github.adamantcheese.database.data

import org.joda.time.Period


data class MediaServiceLinkExtraInfo(
        val videoTitle: String?,
        val videoDuration: Period?
) {
    companion object {
        fun empty(): MediaServiceLinkExtraInfo {
            return MediaServiceLinkExtraInfo(null, null)
        }
    }
}