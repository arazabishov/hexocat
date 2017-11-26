package com.abishov.hexocat.common.picasso;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;
import java.io.IOException;

public final class MockRequestHandler extends RequestHandler {

  private static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024;

  private final AssetManager assetManager;

  // Emulate the disk cache by storing the URLs in an LRU using its size as the value.
  private final LruCache<String, Long> emulatedDiskCache =
      new LruCache<String, Long>(((int) Math.min(MIN_DISK_CACHE_SIZE, Integer.MAX_VALUE))) {
        @Override
        protected int sizeOf(String key, Long value) {
          return (int) Math.min(value.longValue(), Integer.MAX_VALUE);
        }
      };

  public MockRequestHandler(AssetManager assetManager) {
    this.assetManager = assetManager;
  }

  @Override
  public boolean canHandleRequest(Request data) {
    return "mock".equals(data.uri.getScheme());
  }

  @Override
  public Result load(Request request, int networkPolicy) throws IOException {
    // Grab only the path sans leading slash.
    String imagePath = request.uri.getPath().substring(1);

    // Check the disk cache for the image. A non-null return value indicates a hit.
    boolean cacheHit = emulatedDiskCache.get(imagePath) != null;

    // If there's a hit, grab the image stream and return it.
    if (cacheHit) {
      return new Result(loadBitmap(imagePath), Picasso.LoadedFrom.DISK);
    }

    // If we are not allowed to hit the network and the cache missed return a big fat nothing.
    if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
      return null;
    }

    // Since we cache missed put it in the LRU.
    AssetFileDescriptor fileDescriptor = assetManager.openFd(imagePath);
    long size = fileDescriptor.getLength();
    fileDescriptor.close();

    emulatedDiskCache.put(imagePath, size);

    // Grab the image stream and return it.
    return new Result(loadBitmap(imagePath), Picasso.LoadedFrom.NETWORK);
  }

  private Bitmap loadBitmap(String imagePath) throws IOException {
    return BitmapFactory.decodeStream(assetManager.open(imagePath));
  }
}
