package com.abishov.hexocat.home.trending;

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
            RepositoryViewModel.create(null, "test_description", "test_avatar_url");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullDescription() {
        try {
            RepositoryViewModel.create("test_name", null, "test_avatar_url");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullAvatarUrl() {
        try {
            RepositoryViewModel.create("test_name", "test_description", null);
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void equalsAndHashcodeMustConformToTheContract() {
        RepositoryViewModel repositoryOne = RepositoryViewModel
                .create("test_name", "test_description", "test_avatar_url");
        RepositoryViewModel repositoryTwo = RepositoryViewModel
                .create("test_name", "test_description", "test_avatar_url");

        assertThat(repositoryOne).isEqualTo(repositoryTwo);
        assertThat(repositoryTwo).isEqualTo(repositoryOne);
    }

    @Test
    public void propertiesMustBePropagatedCorrectly() {
        RepositoryViewModel repository = RepositoryViewModel
                .create("test_name", "test_description", "test_avatar_url");
        assertThat(repository.name()).isEqualTo("test_name");
        assertThat(repository.description()).isEqualTo("test_description");
        assertThat(repository.avatarUrl()).isEqualTo("test_avatar_url");
    }
}
