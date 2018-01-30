package com.abishov.hexocat.github;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.abishov.hexocat.Inject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PagerIntegrationTests {

  @Test
  public void payloadMustMapToModelCorrectly() {
    Type stringTypeToken = new TypeToken<Pager<String>>() {
    }.getType();
    Pager<String> pager = Inject.gson().fromJson("{\n" +
        "  \"total_count\": 7044,\n" +
        "  \"incomplete_results\": false,\n" +
        "  \"items\": [" +
        "       \"test_item_one\"," +
        "       \"test_item_two\"," +
        "       \"test_item_three\"" +
        "   ]" +
        "}", stringTypeToken);

    assertThat(pager.items().size()).isEqualTo(3);
    assertThat(pager.items().get(0)).isEqualTo("test_item_one");
    assertThat(pager.items().get(1)).isEqualTo("test_item_two");
    assertThat(pager.items().get(2)).isEqualTo("test_item_three");
  }
}
