package com.abishov.hexocat.common.views

import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE
import java.util.*

fun truss(func: Truss.() -> Unit) = Truss().apply(func)

class Truss {
  private val builder: SpannableStringBuilder = SpannableStringBuilder()
  private val stack: Deque<Span> = ArrayDeque()

  fun append(string: String) {
    builder.append(string)
  }

  fun span(span: Any, func: Truss.() -> Unit) {
    pushSpan(span)

    try {
      apply(func)
    } finally {
      popSpan()
    }
  }

  private fun pushSpan(span: Any) {
    stack.addLast(Span(builder.length, span))
  }

  fun build(): CharSequence {
    while (!stack.isEmpty()) {
      popSpan()
    }

    return builder
  }

  private fun popSpan() {
    stack.removeLast().apply {
      builder.setSpan(
        span, start, builder.length, SPAN_INCLUSIVE_EXCLUSIVE
      )
    }
  }

  private class Span internal constructor(internal val start: Int, internal val span: Any)
}
