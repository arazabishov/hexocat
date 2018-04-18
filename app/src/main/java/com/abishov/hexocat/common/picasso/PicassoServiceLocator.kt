package com.abishov.hexocat.common.picasso

import android.content.Context
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

private const val SERVICE_PICASSO = "service:picasso"
private const val SERVICE_PICASSO_TRANSFORMATION = "service:picassoTransformation"

object PicassoServiceLocator {

  fun matchesService(name: String) = SERVICE_PICASSO == name

  fun matchesTransformationService(name: String) = SERVICE_PICASSO_TRANSFORMATION == name

  @SuppressWarnings("ResourceType", "WrongConstant")
  fun obtain(context: Context) = context.getSystemService(SERVICE_PICASSO) as Picasso

  @SuppressWarnings("ResourceType", "WrongConstant")
  fun obtainTransformation(context: Context): Transformation {
    return context.getSystemService(SERVICE_PICASSO_TRANSFORMATION) as Transformation
  }
}
