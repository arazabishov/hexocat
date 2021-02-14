package com.abishov.hexocat.shared.filters

enum class Sort(private val value: String) {
    STARS("stars"), FORKS("forks");

    override fun toString() = value
}
