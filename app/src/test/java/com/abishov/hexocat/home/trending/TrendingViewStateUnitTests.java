package com.abishov.hexocat.home.trending;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

@RunWith(JUnit4.class)
public class TrendingViewStateUnitTests {

    @Test
    public void idleMustPropagateCorrectProperties() {
        TrendingViewState viewState = TrendingViewState.idle();
        assertThat(viewState.isIdle()).isTrue();
        assertThat(viewState.isInProgress()).isFalse();
        assertThat(viewState.isSuccess()).isFalse();
        assertThat(viewState.isFailure()).isFalse();
        assertThat(viewState.error()).isEmpty();
        assertThat(viewState.items()).isEmpty();

        // items collection must be immutable
        try {
            viewState.items().clear();
            fail("UnsupportedOperationException was expected, but nothing was thrown.");
        } catch (UnsupportedOperationException exception) {
            // noop
        }
    }

    @Test
    public void inProgressMustPropagateCorrectProperties() {
        TrendingViewState viewState = TrendingViewState.progress();
        assertThat(viewState.isIdle()).isFalse();
        assertThat(viewState.isInProgress()).isTrue();
        assertThat(viewState.isSuccess()).isFalse();
        assertThat(viewState.isFailure()).isFalse();
        assertThat(viewState.error()).isEmpty();
        assertThat(viewState.items()).isEmpty();

        // items collection must be immutable
        try {
            viewState.items().clear();
            fail("UnsupportedOperationException was expected, but nothing was thrown.");
        } catch (UnsupportedOperationException exception) {
            // noop
        }
    }

    @Test
    public void successMustPropagateCorrectProperties() {
        RepositoryViewModel viewModelOne = RepositoryViewModel.create("test_name_one",
                "test_description_one", "5", "10", "test_avatar_one", "test_login_one");
        RepositoryViewModel viewModelTwo = RepositoryViewModel.create("test_name_two",
                "test_description_two", "3", "4", "test_avatar_two", "test_login_two");

        List<RepositoryViewModel> items = new ArrayList<>();
        items.add(viewModelOne);

        TrendingViewState viewState = TrendingViewState.success(items);

        assertThat(viewState.isIdle()).isFalse();
        assertThat(viewState.isInProgress()).isFalse();
        assertThat(viewState.isSuccess()).isTrue();
        assertThat(viewState.isFailure()).isFalse();
        assertThat(viewState.error()).isEmpty();
        assertThat(viewState.items().size()).isEqualTo(1);
        assertThat(viewState.items().get(0)).isEqualTo(viewModelOne);

        // create must make deep copy of items
        items.add(viewModelTwo);
        assertThat(viewState.items().size()).isEqualTo(1);
        assertThat(viewState.items().get(0)).isEqualTo(viewModelOne);

        // items collection must be immutable
        try {
            viewState.items().clear();
            fail("UnsupportedOperationException was expected, but nothing was thrown.");
        } catch (UnsupportedOperationException exception) {
            // noop
        }
    }

    @Test
    public void errorMustPropagateCorrectProperties() {
        TrendingViewState viewState = TrendingViewState
                .failure(new RuntimeException("oops"));

        assertThat(viewState.isIdle()).isFalse();
        assertThat(viewState.isInProgress()).isFalse();
        assertThat(viewState.isSuccess()).isFalse();
        assertThat(viewState.isFailure()).isTrue();
        assertThat(viewState.error()).isEqualTo("oops");
        assertThat(viewState.items()).isEmpty();

        // items collection must be immutable
        try {
            viewState.items().clear();
            fail("UnsupportedOperationException was expected, but nothing was thrown.");
        } catch (UnsupportedOperationException exception) {
            // noop
        }
    }
}
