/*
 * KurobaEx - *chan browser https://github.com/K1rakishou/Kuroba-Experimental/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.k1rakishou.chan.ui.cell

import com.github.k1rakishou.chan.core.manager.PostPreloadedInfoHolder
import com.github.k1rakishou.chan.core.model.Post
import com.github.k1rakishou.chan.core.model.PostImage
import com.github.k1rakishou.chan.core.settings.ChanSettings.PostViewMode
import com.github.k1rakishou.chan.core.site.sites.chan4.Chan4PagesRequest.BoardPage
import com.github.k1rakishou.chan.ui.text.span.PostLinkable
import com.github.k1rakishou.chan.ui.theme.Theme
import com.github.k1rakishou.chan.ui.view.ThumbnailView
import com.github.k1rakishou.chan.ui.view.floating_menu.FloatingListMenuItem
import com.github.k1rakishou.model.data.descriptor.ChanDescriptor

interface PostCellInterface {
  fun setPost(
    chanDescriptor: ChanDescriptor,
    post: Post,
    currentPostIndex: Int,
    realPostIndex: Int,
    callback: PostCellCallback,
    postPreloadedInfoHolder: PostPreloadedInfoHolder,
    inPopup: Boolean,
    highlighted: Boolean,
    selected: Boolean,
    markedNo: Long,
    showDivider: Boolean,
    postViewMode: PostViewMode,
    compact: Boolean,
    theme: Theme
  )

  /**
   * @param isActuallyRecycling is only true when the view holder that is getting passed into the
   * RecyclerView's onViewRecycled is being recycled because it's
   * offscreen and not because we called notifyItemChanged.
   */
  fun onPostRecycled(isActuallyRecycling: Boolean)
  fun getPost(): Post?
  fun getThumbnailView(postImage: PostImage): ThumbnailView?

  interface PostCellCallback {
    fun getChanDescriptor(): ChanDescriptor?

    // Only used in PostCell and CardPostCell
    fun onPostBind(post: Post)

    // Only used in PostCell and CardPostCell
    fun onPostUnbind(post: Post, isActuallyRecycling: Boolean)

    fun onPostClicked(post: Post)
    fun onPostDoubleClicked(post: Post)
    fun onThumbnailClicked(postImage: PostImage, thumbnail: ThumbnailView)
    fun onThumbnailLongClicked(postImage: PostImage, thumbnail: ThumbnailView)
    fun onShowPostReplies(post: Post)
    fun onPopulatePostOptions(post: Post, menu: MutableList<FloatingListMenuItem>)
    fun onPostOptionClicked(post: Post, id: Any, inPopup: Boolean)
    fun onPostLinkableClicked(post: Post, linkable: PostLinkable)
    fun onPostNoClicked(post: Post)
    fun onPostSelectionQuoted(post: Post, quoted: CharSequence)
    fun getPage(op: Post): BoardPage?
    fun hasAlreadySeenPost(post: Post): Boolean
    fun showPostOptions(post: Post, inPopup: Boolean, items: List<FloatingListMenuItem>)
  }
}