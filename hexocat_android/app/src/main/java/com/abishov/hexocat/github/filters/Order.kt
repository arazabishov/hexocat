package com.abishov.hexocat.github.filters

enum class Order(val value: String) {
  ASC("asc"), DESC("desc");

  override fun toString() = value
}
