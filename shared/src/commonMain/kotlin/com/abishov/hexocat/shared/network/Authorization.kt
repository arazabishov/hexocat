package com.abishov.hexocat.shared.network

import com.abishov.hexocat.shared.BuildKonfig

object Authorization {
    val headers: Map<String, String>
        get() = mapOf("Authorization" to "Bearer ${BuildKonfig.GITHUB_PAT}")
}
