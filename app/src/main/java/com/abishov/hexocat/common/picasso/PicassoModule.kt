package com.abishov.hexocat.common.picasso

import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Downloader
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestHandler
import com.squareup.picasso.Transformation
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@Singleton
class PicassoModule {

  @Provides
  @Singleton
  internal fun circularTransformation(impl: CircleTransformation): Transformation = impl

  @Provides
  @Singleton
  internal fun okHttpPicasso(okClient: OkHttpClient): Downloader {
    return OkHttp3Downloader(okClient)
  }

  @Provides
  @Singleton
  internal fun picasso(
    context: Context, downloader: Downloader, requestHandler: RequestHandler?
  ): Picasso {
    val builder = Picasso.Builder(context)

    if (requestHandler != null) {
      builder.addRequestHandler(requestHandler)
    }

    return builder.downloader(downloader).build()
  }
}
