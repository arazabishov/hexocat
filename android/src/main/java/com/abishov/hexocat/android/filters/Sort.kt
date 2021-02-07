package com.abishov.hexocat.github.filters

enum class Sort(private val value: String) {
  STARS("stars"), FORKS("forks");

  override fun toString() = value
}
