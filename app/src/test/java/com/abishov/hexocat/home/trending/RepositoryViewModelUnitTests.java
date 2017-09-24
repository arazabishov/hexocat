package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.home.repository.RepositoryViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

@RunWith(JUnit4.class)
public class RepositoryViewModelUnitTests {

    @Test
    public void createMustThrowOnNullName() {
        try {
            RepositoryViewModel.create(null, "test_description",
                    "5", "10", "test_avatar_url", "test_login");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullDescription() {
        try {
            RepositoryViewModel.create("test_name", null,
                    "5", "10", "test_avatar_url", "test_login");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnForksName() {
        try {
            RepositoryViewModel.create("test_name", "test_description",
                    null, "10", "test_avatar_url", "test_login");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnStarsName() {
        try {
            RepositoryViewModel.create("test_name", "test_description",
                    "5", null, "test_avatar_url", "test_login");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullAvatarUrl() {
        try {
            RepositoryViewModel.create("test_name", "test_description",
                    "5", "10", null, "test_login");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullLogin() {
        try {
            RepositoryViewModel.create("test_name", "test_description",
                    "5", "10", "test_avatar", null);
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void equalsAndHashcodeMustConformToTheContract() {
        RepositoryViewModel repositoryOne = RepositoryViewModel.create("test_name", "test_description",
                "5", "10", "test_avatar_url", "test_login");
        RepositoryViewModel repositoryTwo = RepositoryViewModel.create("test_name", "test_description",
                "5", "10", "test_avatar_url", "test_login");

        assertThat(repositoryOne).isEqualTo(repositoryTwo);
        assertThat(repositoryTwo).isEqualTo(repositoryOne);
    }

    @Test
    public void propertiesMustBePropagatedCorrectly() {
        RepositoryViewModel repository = RepositoryViewModel.create("test_name",
                "test_description", "5", "10", "test_avatar_url", "test_login");
        assertThat(repository.name()).isEqualTo("test_name");
        assertThat(repository.description()).isEqualTo("test_description");
        assertThat(repository.forks()).isEqualTo("5");
        assertThat(repository.stars()).isEqualTo("10");
        assertThat(repository.avatarUrl()).isEqualTo("test_avatar_url");
        assertThat(repository.login()).isEqualTo("test_login");
    }
}
