package com.github.adamantcheese.model.data.bookmark

import com.github.adamantcheese.model.data.descriptor.ChanDescriptor
import com.github.adamantcheese.model.data.descriptor.PostDescriptor
import okhttp3.HttpUrl
import java.util.*
import kotlin.collections.HashMap

class ThreadBookmark private constructor(
  val threadDescriptor: ChanDescriptor.ThreadDescriptor,
  var seenPostsCount: Int = 0,
  var totalPostsCount: Int = 0,
  var lastViewedPostNo: Long = 0,
  val threadBookmarkReplies: MutableMap<PostDescriptor, ThreadBookmarkReply> = mutableMapOf(),
  var title: String? = null,
  var thumbnailUrl: HttpUrl? = null,
  var state: BitSet,
  var stickyThread: StickyThread = StickyThread.NotSticky
) {

  fun isActive(): Boolean = state.get(BOOKMARK_STATE_WATCHING)

  fun copy(): ThreadBookmark {
    return ThreadBookmark(
      threadDescriptor = threadDescriptor,
      seenPostsCount = seenPostsCount,
      totalPostsCount = totalPostsCount,
      lastViewedPostNo = lastViewedPostNo,
      threadBookmarkReplies = HashMap(threadBookmarkReplies),
      title = title,
      thumbnailUrl = thumbnailUrl,
      stickyThread = stickyThread,
      state = BitSet.valueOf(state.toLongArray())
    )
  }

  fun clearFirstFetchFlag() {
    if (state.get(BOOKMARK_STATE_FIRST_FETCH)) {
      state.clear(BOOKMARK_STATE_FIRST_FETCH)
    }
  }

  fun hasUnseenReplies(): Boolean {
    return threadBookmarkReplies.values.any { threadBookmarkReply -> !threadBookmarkReply.alreadySeen }
  }

  fun unseenPostsCount(): Int {
    return Math.max(0, totalPostsCount - seenPostsCount)
  }

  fun updateLastViewedPostNo(newLastViewedPostNo: Long) {
    lastViewedPostNo = Math.max(lastViewedPostNo, newLastViewedPostNo)
  }

  fun updateSeenPostCount(newSeenPostsCount: Int) {
    seenPostsCount = Math.max(seenPostsCount, newSeenPostsCount)
  }

  fun updateTotalPostsCount(newPostsCount: Int) {
    totalPostsCount = Math.max(totalPostsCount, newPostsCount)
  }

  fun updateSeenPostCountInRollingSticky(newPostsInRollingStickyThreadCount: Int) {
    seenPostsCount = Math.max(0, seenPostsCount - newPostsInRollingStickyThreadCount)
  }

  fun updateSeenReplies(lastSeenPostNo: Long) {
    // Mark all quotes to me as seen which postNo is less than lastSeenPostNo
    threadBookmarkReplies.values
      .filter { threadBookmarkReply ->
        threadBookmarkReply.postDescriptor.postNo <= lastSeenPostNo && !threadBookmarkReply.alreadySeen
      }
      .forEach { threadBookmarkReply -> threadBookmarkReply.alreadySeen = true }
  }

  fun setBumpLimit(bumpLimit: Boolean) {
    if (bumpLimit) {
      state.set(BOOKMARK_STATE_THREAD_BUMP_LIMIT)
    } else {
      state.clear(BOOKMARK_STATE_THREAD_BUMP_LIMIT)
    }
  }

  fun setImageLimit(imageLimit: Boolean) {
    if (imageLimit) {
      state.set(BOOKMARK_STATE_THREAD_IMAGE_LIMIT)
    } else {
      state.clear(BOOKMARK_STATE_THREAD_IMAGE_LIMIT)
    }
  }

  fun markAsSeen() {
    seenPostsCount = totalPostsCount

    threadBookmarkReplies.values.forEach { threadBookmarkReply ->
      threadBookmarkReply.alreadySeen = true
      threadBookmarkReply.alreadyNotified = true
    }
  }

  fun updateState(
    error: Boolean? = null,
    deleted: Boolean? = null,
    archived: Boolean? = null,
    closed: Boolean? = null
  ) {
    val oldStateHasTerminalFlags = state.get(BOOKMARK_STATE_THREAD_DELETED)
    if (oldStateHasTerminalFlags) {
      if (state.get(BOOKMARK_STATE_WATCHING)) {
        state.clear(BOOKMARK_STATE_WATCHING)
      }

      return
    }

    val newStateHasTerminalFlags = deleted == true || archived == true
    if (newStateHasTerminalFlags) {
      // If any of the above - we don't watch that thread anymore
      state.clear(BOOKMARK_STATE_WATCHING)
    }

    error?.let {
      if (it) {
        state.set(BOOKMARK_STATE_ERROR)
      } else {
        state.clear(BOOKMARK_STATE_ERROR)
      }
    }

    deleted?.let {
      if (it) {
        state.set(BOOKMARK_STATE_THREAD_DELETED)
      }
    }

    archived?.let {
      if (it) {
        state.set(BOOKMARK_STATE_THREAD_ARCHIVED)
      }
    }

    closed?.let {
      if (it) {
        state.set(BOOKMARK_STATE_THREAD_CLOSED)
      } else {
        state.clear(BOOKMARK_STATE_THREAD_CLOSED)
      }
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ThreadBookmark

    if (threadDescriptor != other.threadDescriptor) return false
    if (seenPostsCount != other.seenPostsCount) return false
    if (totalPostsCount != other.totalPostsCount) return false
    if (lastViewedPostNo != other.lastViewedPostNo) return false
    if (threadBookmarkReplies != other.threadBookmarkReplies) return false
    if (title != other.title) return false
    if (thumbnailUrl != other.thumbnailUrl) return false
    if (stickyThread != other.stickyThread) return false
    if (state != other.state) return false

    return true
  }

  override fun hashCode(): Int {
    var result = threadDescriptor.hashCode()
    result = 31 * result + seenPostsCount
    result = 31 * result + totalPostsCount
    result = 31 * result + lastViewedPostNo.hashCode()
    result = 31 * result + threadBookmarkReplies.hashCode()
    result = 31 * result + (title?.hashCode() ?: 0)
    result = 31 * result + (thumbnailUrl?.hashCode() ?: 0)
    result = 31 * result + stickyThread.hashCode()
    result = 31 * result + state.hashCode()
    return result
  }

  override fun toString(): String {
    return "ThreadBookmark(threadDescriptor=$threadDescriptor, seenPostsCount=$seenPostsCount, " +
      "totalPostsCount=$totalPostsCount, lastViewedPostNo=$lastViewedPostNo, " +
      "threadBookmarkReplies=$threadBookmarkReplies, title=${title?.take(20)}, thumbnailUrl=$thumbnailUrl, " +
      "stickyThread=$stickyThread, state=$state)"
  }

  companion object {
    /**
     * A flag for threads that are being watched (not paused). Default flag when bookmarking any
     * thread.
     * */
    const val BOOKMARK_STATE_WATCHING = 1 shl 0

    /**
     * A flag for threads that are probably got deleted (404ed) from the server
     * */
    const val BOOKMARK_STATE_THREAD_DELETED = 1 shl 1

    /**
     * A flag for threads that got archived by first-party archives (like the archive that 4chan has)
     * */
    const val BOOKMARK_STATE_THREAD_ARCHIVED = 1 shl 2

    /**
     * A flag for closed threads
     * */
    const val BOOKMARK_STATE_THREAD_CLOSED = 1 shl 3

    /**
     * A flag for threads that we failed to fetch bookmark info from for any reason (no internet,
     * server is down, etc.)
     * */
    const val BOOKMARK_STATE_ERROR = 1 shl 4

    /**
     * Thread has reached bump limit
     * */
    const val BOOKMARK_STATE_THREAD_BUMP_LIMIT = 1 shl 5

    /**
     * Thread has reached image limit
     * */
    const val BOOKMARK_STATE_THREAD_IMAGE_LIMIT = 1 shl 6

    /**
     * Default bookmark state that is getting cleared once the very first fetch is completed with
     * any result (success/error). We need this flag to show the "Loading" label for bookmarks we
     * have no info yet (before their very first fetch).
     * */
    const val BOOKMARK_STATE_FIRST_FETCH = 1 shl 7

    fun create(threadDescriptor: ChanDescriptor.ThreadDescriptor): ThreadBookmark {
      val bookmarkInitialState = BitSet()
      bookmarkInitialState.set(BOOKMARK_STATE_WATCHING)
      bookmarkInitialState.set(BOOKMARK_STATE_FIRST_FETCH)

      return ThreadBookmark(
        threadDescriptor = threadDescriptor,
        state = bookmarkInitialState
      )
    }
  }

}