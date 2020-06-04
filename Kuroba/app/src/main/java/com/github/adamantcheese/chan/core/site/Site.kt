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
package com.github.adamantcheese.chan.core.site

import com.github.adamantcheese.chan.core.model.json.site.SiteConfig
import com.github.adamantcheese.chan.core.model.orm.Board
import com.github.adamantcheese.chan.core.settings.json.JsonSettings
import com.github.adamantcheese.chan.core.site.parser.ChanReader
import com.github.adamantcheese.model.data.descriptor.SiteDescriptor

interface Site {
  enum class SiteFeature {
    /**
     * This site supports posting. (Or rather, we've implemented support for it.)
     *
     * @see SiteActions.post
     * @see SiteEndpoints.reply
     */
    POSTING,

    /**
     * This site supports deleting posts.
     *
     * @see SiteActions.delete
     * @see SiteEndpoints.delete
     */
    POST_DELETE,

    /**
     * This site supports reporting posts.
     *
     * @see SiteEndpoints.report
     */
    POST_REPORT,

    /**
     * This site supports some sort of authentication (like 4pass).
     *
     * @see SiteActions.login
     * @see SiteEndpoints.login
     */
    LOGIN,

    /**
     * This site reports image hashes.
     */
    IMAGE_FILE_HASH,


    /**
     * This site can retrieve deleted posts from third-party archives (for now it's only 4chan)
     */
    THIRD_PARTY_ARCHIVES
  }

  /**
   * Features available to check when [SiteFeature.POSTING] is `true`.
   */
  enum class BoardFeature {
    /**
     * This board supports posting with images.
     */
    // TODO(multisite) use this
    POSTING_IMAGE,

    /**
     * This board supports posting with a checkbox to mark the posted image as a spoiler.
     */
    // TODO(multisite) use this
    POSTING_SPOILER,

    /**
     * This board support loading the archive, a list of threads that are locked after expiring.
     */
    ARCHIVE
  }

  /**
   * How the boards are organized for this site.
   */
  enum class BoardsType(
    /**
     * Can the boards be listed, in other words, can
     * [SiteActions.boards] be used, and is
     * [.board] available.
     */
    val canList: Boolean
  ) {
    /**
     * The site's boards are static, there is no extra info for a board in the api.
     */
    STATIC(true),

    /**
     * The site's boards are dynamic, a boards.json like endpoint is available to get the available boards.
     */
    DYNAMIC(true),

    /**
     * The site's boards are dynamic and infinite, existence of boards should be checked per board.
     */
    INFINITE(false);

  }

  /**
   * Initialize the site with the given id, config, and userSettings.
   *
   * **Note: do not use any managers at this point, because they rely on the sites being initialized.
   * Instead, use [.postInitialize]**
   *
   * @param id           the site id
   * @param siteConfig   the site config
   * @param userSettings the site user settings
   */
  fun initialize(id: Int, siteConfig: SiteConfig, userSettings: JsonSettings)
  fun postInitialize()

  /**
   * Global positive (>0) integer that uniquely identifies this site.<br></br>
   * Use the id received from [.initialize].
   *
   * @return a positive (>0) integer that uniquely identifies this site.
   */
  @Deprecated("Use SiteDescriptor instead")
  fun id(): Int

  /**
   * Name of the site. Must be unique. This will be used to find a site among other sites. Usually
   * you should just use the domain name (like 4chan). It usually shouldn't matter if a site has
   * multiple domains.
   */
  fun name(): String
  fun siteDescriptor(): SiteDescriptor
  fun icon(): SiteIcon
  fun boardsType(): BoardsType
  fun resolvable(): SiteUrlHandler
  fun siteFeature(siteFeature: SiteFeature): Boolean
  fun boardFeature(boardFeature: BoardFeature, board: Board): Boolean
  fun settings(): List<SiteSetting>
  fun endpoints(): SiteEndpoints
  fun requestModifier(): SiteRequestModifier
  fun chanReader(): ChanReader?
  fun actions(): SiteActions

  /**
   * Return the board for this site with the given `code`.
   *
   * This does not need to create the board if it doesn't exist. This is important for
   * sites that have the board type INFINITE. Returning from the database is
   * enough.
   *
   * @param code the board code
   * @return a board with the board code, or `null`.
   */
  fun board(code: String): Board?

  /**
   * Create a new board with the specified `code` and `name`.
   *
   * This is only applicable to sites with a board type INFINITE.
   *
   * @return the created board.
   */
  fun createBoard(boardName: String, boardCode: String): Board
  fun getChunkDownloaderSiteProperties(): ChunkDownloaderSiteProperties
}