/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.abishov.hexocat.common.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import androidx.recyclerview.widget.RecyclerView

private val ATTRS = intArrayOf(android.R.attr.listDivider)

class DividerItemDecoration(
  context: Context,
  private val orientation: Int,
  private val paddingStart: Float,
  private val rightToLeft: Boolean
) : RecyclerView.ItemDecoration() {

  private val bounds = Rect()
  private var divider: Drawable? = null

  init {
    context.withStyledAttributes(attrs = ATTRS) {
      divider = getDrawable(0)
    }
  }

  override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    parent.layoutManager?.let {
      if (orientation == VERTICAL) {
        drawVertical(canvas, parent)
      } else {
        drawHorizontal(canvas, parent)
      }
    }
  }

  private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
    canvas.save()

    val left = parent.paddingLeft + if (rightToLeft) 0 else paddingStart.toInt()
    val right = parent.width - parent.paddingRight + if (rightToLeft) paddingStart.toInt() else 0
    val childCount = parent.childCount

    for (i in 0 until childCount) {
      val child = parent.getChildAt(i)
      parent.getDecoratedBoundsWithMargins(child, bounds)

      val bottom = bounds.bottom + Math.round(child.translationY)
      val top = bottom - (divider?.intrinsicHeight ?: 0)

      divider?.setBounds(left, top, right, bottom)
      divider?.draw(canvas)
    }

    canvas.restore()
  }

  private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
    canvas.save()

    val top = parent.paddingTop
    val bottom = parent.height - parent.paddingBottom
    val childCount = parent.childCount

    for (i in 0 until childCount) {
      val child = parent.getChildAt(i)
      parent.layoutManager?.getDecoratedBoundsWithMargins(child, bounds)

      val right = bounds.right + Math.round(child.translationX)
      val left = right - (divider?.intrinsicWidth ?: 0)

      divider?.setBounds(left, top, right, bottom)
      divider?.draw(canvas)
    }

    canvas.restore()
  }

  override fun getItemOffsets(
          outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
  ) {
    if (orientation == VERTICAL) {
      outRect.set(0, 0, 0, divider?.intrinsicHeight ?: 0)
    } else {
      outRect.set(0, 0, divider?.intrinsicWidth ?: 0, 0)
    }
  }

  companion object {
    const val HORIZONTAL = LinearLayout.HORIZONTAL
    const val VERTICAL = LinearLayout.VERTICAL
  }
}
