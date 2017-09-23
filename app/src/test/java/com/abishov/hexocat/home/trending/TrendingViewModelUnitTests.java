package com.abishov.hexocat.home.trending;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

@RunWith(JUnit4.class)
public class TrendingViewModelUnitTests {

    @Test
    public void createMustThrowOnNullName() {
        try {
            TrendingViewModel.create(null, "test_description",
                    "5", "10", "test_avatar_url", "test_login");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullDescription() {
        try {
            TrendingViewModel.create("test_name", null,
                    "5", "10", "test_avatar_url", "test_login");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnForksName() {
        try {
            TrendingViewModel.create("test_name", "test_description",
                    null, "10", "test_avatar_url", "test_login");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnStarsName() {
        try {
            TrendingViewModel.create("test_name", "test_description",
                    "5", null, "test_avatar_url", "test_login");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullAvatarUrl() {
        try {
            TrendingViewModel.create("test_name", "test_description",
                    "5", "10", null, "test_login");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullLogin() {
        try {
            TrendingViewModel.create("test_name", "test_description",
                    "5", "10", "test_avatar", null);
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void equalsAndHashcodeMustConformToTheContract() {
        TrendingViewModel repositoryOne = TrendingViewModel.create("test_name", "test_description",
                "5", "10", "test_avatar_url", "test_login");
        TrendingViewModel repositoryTwo = TrendingViewModel.create("test_name", "test_description",
                "5", "10", "test_avatar_url", "test_login");

        assertThat(repositoryOne).isEqualTo(repositoryTwo);
        assertThat(repositoryTwo).isEqualTo(repositoryOne);
    }

    @Test
    public void propertiesMustBePropagatedCorrectly() {
        TrendingViewModel repository = TrendingViewModel.create("test_name",
                "test_description", "5", "10", "test_avatar_url", "test_login");
        assertThat(repository.name()).isEqualTo("test_name");
        assertThat(repository.description()).isEqualTo("test_description");
        assertThat(repository.forks()).isEqualTo("5");
        assertThat(repository.stars()).isEqualTo("10");
        assertThat(repository.avatarUrl()).isEqualTo("test_avatar_url");
        assertThat(repository.login()).isEqualTo("test_login");
    }
}
