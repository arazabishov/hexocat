package com.abishov.hexocat.android.common.network

import android.net.Uri
import com.apollographql.apollo.api.CustomTypeAdapter
import com.apollographql.apollo.api.CustomTypeValue

class UriTypeAdapter: CustomTypeAdapter<Uri> {
  override fun decode(value: CustomTypeValue<*>): Uri {
    return Uri.parse(value.value.toString())
  }

  override fun encode(value: Uri): CustomTypeValue<*> {
    return CustomTypeValue.GraphQLString(value.toString())
  }
}
