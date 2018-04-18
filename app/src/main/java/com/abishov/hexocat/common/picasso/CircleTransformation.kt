package com.abishov.hexocat.common.picasso

import android.graphics.*
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Shader.TileMode.CLAMP
import com.squareup.picasso.Transformation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CircleTransformation @Inject constructor() : Transformation {

  override fun transform(bitmap: Bitmap): Bitmap {
    val size = bitmap.width
    val rounded = Bitmap.createBitmap(size, size, ARGB_8888)
    val canvas = Canvas(rounded)

    val shader = BitmapShader(bitmap, CLAMP, CLAMP)
    val shaderPaint = Paint(ANTI_ALIAS_FLAG)
    shaderPaint.shader = shader

    val rect = RectF(0f, 0f, size.toFloat(), size.toFloat())
    val radius = size / 2f
    canvas.drawRoundRect(rect, radius, radius, shaderPaint)

    bitmap.recycle()
    return rounded
  }

  override fun key() = "circle"
}