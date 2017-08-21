package com.abishov.hexocat.commons.views;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

@RunWith(JUnit4.class)
public class ViewStateUnitTests {

    @Test
    public void equalsAndHashcodeMethodsMustConformToContract() {
        EqualsVerifier.forClass(ViewState.idle().getClass())
                .suppress(Warning.NULL_FIELDS)
                .verify();
    }

    @Test
    public void idleMustPropagateCorrectProperties() {
        ViewState viewState = ViewState.idle();
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
        ViewState viewState = ViewState.progress();
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
        List<String> items = new ArrayList<>();
        items.add("test_state");

        ViewState<String> viewState = ViewState.success(items);

        assertThat(viewState.isIdle()).isFalse();
        assertThat(viewState.isInProgress()).isFalse();
        assertThat(viewState.isSuccess()).isTrue();
        assertThat(viewState.isFailure()).isFalse();
        assertThat(viewState.error()).isEmpty();
        assertThat(viewState.items().size()).isEqualTo(1);
        assertThat(viewState.items().get(0)).isEqualTo("test_state");

        // create must make deep copy of items
        items.add("test_state_two");
        assertThat(viewState.items().size()).isEqualTo(1);
        assertThat(viewState.items().get(0)).isEqualTo("test_state");

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
        ViewState<String> viewState = ViewState
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
