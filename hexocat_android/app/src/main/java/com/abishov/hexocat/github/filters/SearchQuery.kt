package com.abishov.hexocat.github.filters

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class SearchQuery(
  private val createdSince: LocalDate,
  private val sort: Sort = Sort.STARS,
  private val order: Order = Order.DESC
) {

  override fun toString(): String {
    val dateString = DateTimeFormatter.ISO_LOCAL_DATE.format(createdSince)
    return "created:>=$dateString sort:$sort-$order"
  }
}
