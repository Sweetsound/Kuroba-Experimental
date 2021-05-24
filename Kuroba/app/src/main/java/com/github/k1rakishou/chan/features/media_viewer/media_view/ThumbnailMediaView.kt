package com.github.k1rakishou.chan.features.media_viewer.media_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import coil.request.Disposable
import com.github.k1rakishou.chan.R
import com.github.k1rakishou.chan.core.image.ImageLoaderV2
import com.github.k1rakishou.chan.features.media_viewer.MediaLocation
import com.github.k1rakishou.chan.ui.theme.widget.TouchBlockingFrameLayoutNoBackground
import com.github.k1rakishou.chan.ui.view.ThumbnailImageView
import com.github.k1rakishou.chan.utils.AppModuleAndroidUtils
import com.github.k1rakishou.model.data.post.ChanPostImageType
import okhttp3.HttpUrl
import java.util.*
import javax.inject.Inject

@SuppressLint("ViewConstructor")
class ThumbnailMediaView @JvmOverloads constructor(
  context: Context,
  attributeSet: AttributeSet? = null
) : TouchBlockingFrameLayoutNoBackground(context, attributeSet, 0) {
  private val thumbnailView: ThumbnailImageView

  private var requestDisposable: Disposable? = null

  @Inject
  lateinit var imageLoaderV2: ImageLoaderV2

  init {
    AppModuleAndroidUtils.extractActivityComponent(context)
      .inject(this)

    inflate(context, R.layout.media_view_thumbnail, this)

    thumbnailView = findViewById(R.id.thumbnail_image_view)
  }

  fun thumbnailBitmap(): Bitmap? {
    val bitmap = (thumbnailView.drawable as? BitmapDrawable)?.bitmap
    if (bitmap == null || bitmap.isRecycled) {
      return null
    }

    return bitmap
  }

  fun bind(parameters: ThumbnailMediaViewParameters) {
    if (requestDisposable != null) {
      return
    }

    requestDisposable = when (val location = parameters.thumbnailLocation) {
      is MediaLocation.Local -> TODO()
      is MediaLocation.Remote -> loadRemoteMedia(location.url, parameters)
    }
  }

  fun unbind() {
    if (requestDisposable != null) {
      requestDisposable!!.dispose()
      requestDisposable = null
    }

    thumbnailView.setImageDrawable(null)
  }

  private fun loadRemoteMedia(url: HttpUrl, parameters: ThumbnailMediaViewParameters): Disposable {
    return imageLoaderV2.loadFromNetwork(
      context = context,
      requestUrl = url.toString(),
      imageSize = ImageLoaderV2.ImageSize.MeasurableImageSize.create(this),
      transformations = emptyList(),
      listener = object : ImageLoaderV2.FailureAwareImageListener {
        override fun onResponse(drawable: BitmapDrawable, isImmediate: Boolean) {
          requestDisposable = null

          val postImageType = if (parameters.isOriginalMediaVideo) {
            ChanPostImageType.MOVIE
          } else {
            ChanPostImageType.STATIC
          }

          thumbnailView.setType(postImageType)
          thumbnailView.setImageDrawable(drawable)
        }

        override fun onNotFound() {
          requestDisposable = null
          onNotFoundError()
        }

        override fun onResponseError(error: Throwable) {
          requestDisposable = null
          onError(error)
        }
      })
  }

  private fun onNotFoundError() {
//    cancellableToast.showToast(context, R.string.image_not_found)

    // TODO(KurobaEx):
//    callback?.hideProgress(this@MultiImageView)
  }

  private fun onError(exception: Throwable) {
    val message = String.format(
      Locale.ENGLISH,
      "%s: %s",
      AppModuleAndroidUtils.getString(R.string.image_preview_failed),
      exception.message
    )

//    cancellableToast.showToast(context, message)

    // TODO(KurobaEx):
//    callback?.hideProgress(this@MultiImageView)
  }

  data class ThumbnailMediaViewParameters(
    val isOriginalMediaVideo: Boolean,
    val thumbnailLocation: MediaLocation
  )

  companion object {
    private const val TAG = "ThumbnailMediaView"
  }

}