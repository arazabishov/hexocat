package com.abishov.hexocat.shared.filters

enum class Order(val value: String) {
  ASC("asc"), DESC("desc");

  override fun toString() = value
}
