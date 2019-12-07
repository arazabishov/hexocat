package com.abishov.hexocat.common.picasso

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import com.squareup.picasso.RequestHandler
import java.io.IOException

private const val MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024

class MockRequestHandler(private val assetManager: AssetManager) : RequestHandler() {

  private val emulatedDiskCache =
    object : LruCache<String, Long>(Math.min(MIN_DISK_CACHE_SIZE, Integer.MAX_VALUE)) {
      override fun sizeOf(key: String, value: Long): Int {
        return Math.min(value, Integer.MAX_VALUE.toLong()).toInt()
      }
    }

  override fun canHandleRequest(data: Request): Boolean {
    return "mock" == data.uri.scheme
  }

  @Throws(IOException::class)
  override fun load(request: Request, networkPolicy: Int): RequestHandler.Result? {
    val imagePath = request.uri.path.substring(1)
    val cacheHit = emulatedDiskCache.get(imagePath) != null

    if (cacheHit) {
      return RequestHandler.Result(loadBitmap(imagePath), Picasso.LoadedFrom.DISK)
    }

    if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
      return null
    }

    val fileDescriptor = assetManager.openFd(imagePath)
    val size = fileDescriptor.length
    fileDescriptor.close()

    emulatedDiskCache.put(imagePath, size)
    return RequestHandler.Result(loadBitmap(imagePath), Picasso.LoadedFrom.NETWORK)
  }

  @Throws(IOException::class)
  private fun loadBitmap(imagePath: String): Bitmap {
    return BitmapFactory.decodeStream(assetManager.open(imagePath))
  }
}
