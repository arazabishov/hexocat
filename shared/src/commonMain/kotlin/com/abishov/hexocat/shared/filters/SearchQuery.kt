package com.abishov.hexocat.shared.filters

class SearchQuery(
    private val dateString: String,
    private val sort: Sort = Sort.STARS,
    private val order: Order = Order.DESC,
) {

    override fun toString(): String {
        return "created:>=$dateString sort:$sort-$order"
    }
}
