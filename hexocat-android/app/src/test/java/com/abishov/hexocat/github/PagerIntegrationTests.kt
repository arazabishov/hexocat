package com.abishov.hexocat.github

import assertk.assert
import assertk.assertions.isEqualTo
import com.abishov.hexocat.Inject
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import org.junit.Test

class PagerIntegrationTests {

  @Test
  fun `payload must map to model correctly`() {
    val adapter: JsonAdapter<Pager<String>> = Inject.moshi()
      .adapter(Types.newParameterizedType(Pager::class.java, String::class.java))

    val pager = adapter.fromJson(
      """
        {
          "total_count": 7044,
          "incomplete_results": false,
          "items": [
               "test_item_one",
               "test_item_two",
               "test_item_three"
           ]
        }
        """
    )!!

    assert(pager.items.size).isEqualTo(3)
    assert(pager.items[0]).isEqualTo("test_item_one")
    assert(pager.items[1]).isEqualTo("test_item_two")
    assert(pager.items[2]).isEqualTo("test_item_three")
  }
}
