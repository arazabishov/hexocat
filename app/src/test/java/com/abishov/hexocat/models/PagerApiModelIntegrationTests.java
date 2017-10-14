package com.abishov.hexocat.models;

import com.abishov.hexocat.Inject;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Type;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(JUnit4.class)
public class PagerApiModelIntegrationTests {

    @Test
    public void payloadMustMapToModelCorrectly() {
        Type stringTypeToken = new TypeToken<PagerApiModel<String>>() {}.getType();
        PagerApiModel<String> pagerApiModel = Inject.gson().fromJson("{\n" +
                "  \"total_count\": 7044,\n" +
                "  \"incomplete_results\": false,\n" +
                "  \"items\": [" +
                "       \"test_item_one\"," +
                "       \"test_item_two\"," +
                "       \"test_item_three\"" +
                "   ]" +
                "}", stringTypeToken);

        assertThat(pagerApiModel.items().size()).isEqualTo(3);
        assertThat(pagerApiModel.items().get(0)).isEqualTo("test_item_one");
        assertThat(pagerApiModel.items().get(1)).isEqualTo("test_item_two");
        assertThat(pagerApiModel.items().get(2)).isEqualTo("test_item_three");
    }
}
