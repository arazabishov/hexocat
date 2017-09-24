package com.abishov.hexocat.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

@RunWith(JUnit4.class)
public class PagerApiModelUnitTests {

    @Test
    public void createMustThrowOnNullItems() {
        try {
            PagerApiModel.create(null);
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void equalsAndHashcodeMethodsMustConformToContract() {
        List<String> sampleItems = Arrays.asList(
                "test_item_one", "test_item_two");
        PagerApiModel pagerApiModelOne = PagerApiModel.create(sampleItems);
        PagerApiModel pagerApiModelTwo = PagerApiModel.create(sampleItems);

        assertThat(pagerApiModelOne).isEqualTo(pagerApiModelTwo);
        assertThat(pagerApiModelTwo).isEqualTo(pagerApiModelOne);
    }

    @Test
    public void createMustPropagatePropertiesCorrectly() {
        List<String> sampleItems = Arrays.asList(
                "test_item_one", "test_item_two");
        PagerApiModel pagerApiModel = PagerApiModel.create(sampleItems);

        assertThat(pagerApiModel.items().get(0)).isEqualTo("test_item_one");
        assertThat(pagerApiModel.items().get(1)).isEqualTo("test_item_two");
    }

    @Test
    public void itemsMustBeImmutable() {
        List<String> sampleItems = new ArrayList<>();
        sampleItems.add("test_item_one");
        sampleItems.add("test_item_two");

        PagerApiModel pagerApiModel = PagerApiModel.create(sampleItems);

        try {
            pagerApiModel.items().clear();
            fail("UnsupportedOperationException was expected, but nothing was thrown.");
        } catch (UnsupportedOperationException unsupportedOperationException) {
            // noop
        }

        sampleItems.add("test_item_three");

        assertThat(pagerApiModel.items().size()).isEqualTo(2);
        assertThat(pagerApiModel.items().get(0)).isEqualTo("test_item_one");
        assertThat(pagerApiModel.items().get(1)).isEqualTo("test_item_two");
    }
}
