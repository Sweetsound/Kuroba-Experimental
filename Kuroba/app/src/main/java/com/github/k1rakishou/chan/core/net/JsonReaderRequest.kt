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
package com.github.k1rakishou.chan.core.net

import com.github.k1rakishou.chan.core.base.okhttp.ProxiedOkHttpClient
import com.github.k1rakishou.chan.utils.Logger
import com.github.k1rakishou.common.ModularResult.Companion.Try
import com.github.k1rakishou.common.suspendCall
import com.google.gson.stream.JsonReader
import okhttp3.Request
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

abstract class JsonReaderRequest<T>(
  protected val jsonRequestType: JsonRequestType,
  protected val request: Request,
  private val proxiedOkHttpClient: ProxiedOkHttpClient
) {

  @OptIn(ExperimentalTime::class)
  open suspend fun execute(): JsonReaderResponse<T> {
    val response = Try {
      val timedValue = measureTimedValue {
        proxiedOkHttpClient.proxiedClient.suspendCall(request)
      }

      Logger.d(TAG, "Request \"${jsonRequestType.requestTag}\" to \"${request.url}\" " +
        "took ${timedValue.duration.inMilliseconds}ms")

      return@Try timedValue.value
    }.safeUnwrap { error ->
      Logger.e(TAG, "Network request error", error)
      return JsonReaderResponse.UnknownServerError(error)
    }

    if (!response.isSuccessful) {
      return JsonReaderResponse.ServerError(response.code)
    }

    if (response.body == null) {
      return JsonReaderResponse.UnknownServerError(IOException("Response has no body"))
    }

    try {
      return response.body!!.use { body ->
        return@use body.byteStream().use { inputStream ->
          return@use JsonReader(InputStreamReader(inputStream, StandardCharsets.UTF_8)).use { jsonReader ->
            return@use JsonReaderResponse.Success(readJson(jsonReader))
          }
        }
      }
    } catch (error: Throwable) {
      return JsonReaderResponse.ParsingError(error)
    }
  }

  protected abstract suspend fun readJson(reader: JsonReader): T

  sealed class JsonReaderResponse<out T> {
    class Success<out T>(val result: T) : JsonReaderResponse<T>()
    class ServerError(val statusCode: Int) : JsonReaderResponse<Nothing>()
    class UnknownServerError(val error: Throwable) : JsonReaderResponse<Nothing>()
    class ParsingError(val error: Throwable) : JsonReaderResponse<Nothing>()
  }

  enum class JsonRequestType(val requestTag: String) {
    Chan420BoardsJsonRequest("Chan420Boards"),
    Chan4BoardsJsonRequest("Chan4Boards"),
    Kun8BoardsJsonRequest("Kun8Boards"),
    Chan4PagesJsonRequest("Chan4Pages"),
    BetaUpdateApiJsonRequest("BetaUpdateApi"),
    DvachBoardsJsonRequest("DvachBoards"),
    ReleaseUpdateApiJsonRequest("ReleaseUpdateApi")
  }

  companion object {
    private const val TAG = "JsonReaderRequest"
  }

}