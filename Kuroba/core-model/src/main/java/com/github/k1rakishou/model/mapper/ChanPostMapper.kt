package com.github.k1rakishou.model.mapper

import com.github.k1rakishou.model.data.descriptor.ChanDescriptor
import com.github.k1rakishou.model.data.descriptor.PostDescriptor
import com.github.k1rakishou.model.data.post.ChanPost
import com.github.k1rakishou.model.data.serializable.spans.SerializableSpannableString
import com.github.k1rakishou.model.entity.chan.post.ChanPostEntity
import com.github.k1rakishou.model.entity.chan.post.ChanPostIdEntity
import com.github.k1rakishou.model.entity.chan.post.ChanTextSpanEntity
import com.github.k1rakishou.model.entity.chan.thread.ChanThreadEntity
import com.google.gson.Gson

object ChanPostMapper {

  fun toEntity(
    chanPostId: Long,
    chanPost: ChanPost
  ): ChanPostEntity {
    return ChanPostEntity(
      chanPostId = chanPostId,
      deleted = chanPost.deleted,
      timestamp = chanPost.timestamp,
      name = chanPost.name,
      posterId = chanPost.posterId,
      moderatorCapcode = chanPost.moderatorCapcode,
      isOp = chanPost.isOp,
      isSavedReply = chanPost.isSavedReply
    )
  }

  fun fromEntity(
    gson: Gson,
    chanDescriptor: ChanDescriptor,
    chanThreadEntity: ChanThreadEntity,
    chanPostIdEntity: ChanPostIdEntity,
    chanPostEntity: ChanPostEntity?,
    chanTextSpanEntityList: List<ChanTextSpanEntity>?
  ): ChanPost? {
    if (chanPostEntity == null) {
      return null
    }

    val postComment = TextSpanMapper.fromEntity(
      gson,
      chanTextSpanEntityList,
      ChanTextSpanEntity.TextType.PostComment
    ) ?: SerializableSpannableString()

    val subject = TextSpanMapper.fromEntity(
      gson,
      chanTextSpanEntityList,
      ChanTextSpanEntity.TextType.Subject
    ) ?: SerializableSpannableString()

    val tripcode = TextSpanMapper.fromEntity(
      gson,
      chanTextSpanEntityList,
      ChanTextSpanEntity.TextType.Tripcode
    ) ?: SerializableSpannableString()

    val postDescriptor = when (chanDescriptor) {
      is ChanDescriptor.ThreadDescriptor -> PostDescriptor.create(
        chanDescriptor.siteName(),
        chanDescriptor.boardCode(),
        chanDescriptor.threadNo,
        chanPostIdEntity.postNo
      )
      is ChanDescriptor.CatalogDescriptor -> PostDescriptor.create(
        chanDescriptor.siteName(),
        chanDescriptor.boardCode(),
        chanPostIdEntity.postNo
      )
    }

    if (chanPostEntity.isOp) {
      return ChanPost(
        chanPostId = chanPostEntity.chanPostId,
        postDescriptor = postDescriptor,
        postImages = mutableListOf(),
        postIcons = mutableListOf(),
        replies = chanThreadEntity.replies,
        threadImagesCount = chanThreadEntity.threadImagesCount,
        uniqueIps = chanThreadEntity.uniqueIps,
        lastModified = chanThreadEntity.lastModified,
        sticky = chanThreadEntity.sticky,
        closed = chanThreadEntity.closed,
        archived = chanThreadEntity.archived,
        deleted = chanPostEntity.deleted,
        archiveId = chanPostIdEntity.ownerArchiveId,
        timestamp = chanPostEntity.timestamp,
        name = chanPostEntity.name,
        postComment = postComment,
        subject = subject,
        tripcode = tripcode,
        posterId = chanPostEntity.posterId,
        moderatorCapcode = chanPostEntity.moderatorCapcode,
        isOp = chanPostEntity.isOp,
        isSavedReply = chanPostEntity.isSavedReply
      )
    } else {
      return ChanPost(
        chanPostId = chanPostEntity.chanPostId,
        postDescriptor = postDescriptor,
        postImages = mutableListOf(),
        postIcons = mutableListOf(),
        timestamp = chanPostEntity.timestamp,
        name = chanPostEntity.name,
        deleted = chanPostEntity.deleted,
        archiveId = chanPostIdEntity.ownerArchiveId,
        postComment = postComment,
        subject = subject,
        tripcode = tripcode,
        posterId = chanPostEntity.posterId,
        moderatorCapcode = chanPostEntity.moderatorCapcode,
        isOp = chanPostEntity.isOp,
        isSavedReply = chanPostEntity.isSavedReply
      )
    }
  }

}