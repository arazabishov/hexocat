package com.abishov.hexocat.commons.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

@RunWith(JUnit4.class)
public class PagerUnitTests {

    @Test
    public void createMustThrowOnNullItems() {
        try {
            Pager.create(null);
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void equalsAndHashcodeMethodsMustConformToContract() {
        List<String> sampleItems = Arrays.asList(
                "test_item_one", "test_item_two");
        Pager pagerOne = Pager.create(sampleItems);
        Pager pagerTwo = Pager.create(sampleItems);

        assertThat(pagerOne).isEqualTo(pagerTwo);
        assertThat(pagerTwo).isEqualTo(pagerOne);
    }

    @Test
    public void createMustPropagatePropertiesCorrectly() {
        List<String> sampleItems = Arrays.asList(
                "test_item_one", "test_item_two");
        Pager pager = Pager.create(sampleItems);

        assertThat(pager.items().get(0)).isEqualTo("test_item_one");
        assertThat(pager.items().get(1)).isEqualTo("test_item_two");
    }

    @Test
    public void itemsMustBeImmutable() {
        List<String> sampleItems = new ArrayList<>();
        sampleItems.add("test_item_one");
        sampleItems.add("test_item_two");

        Pager pager = Pager.create(sampleItems);

        try {
            pager.items().clear();
            fail("UnsupportedOperationException was expected, but nothing was thrown.");
        } catch (UnsupportedOperationException unsupportedOperationException) {
            // noop
        }

        sampleItems.add("test_item_three");

        assertThat(pager.items().size()).isEqualTo(2);
        assertThat(pager.items().get(0)).isEqualTo("test_item_one");
        assertThat(pager.items().get(1)).isEqualTo("test_item_two");
    }
}
