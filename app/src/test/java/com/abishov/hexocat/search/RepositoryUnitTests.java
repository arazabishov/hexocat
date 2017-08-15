package com.abishov.hexocat.search;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class RepositoryUnitTests {

    @Test
    public void createMustThrowOnNullName() {
        try {
            Repository.create(null, "test_html_url",
                    "test_description", mock(Organization.class));
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullHtmlUrl() {
        try {
            Repository.create("test_name", null,
                    "test_description", mock(Organization.class));
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullDescription() {
        try {
            Repository.create("test_name", "test_html_url",
                    null, mock(Organization.class));
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullOwner() {
        try {
            Repository.create("test_name", "test_html_url",
                    "test_description", null);
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void equalsAndHashcodeMustConformToTheContract() {
        Organization owner = Organization.create(
                "test_login", "test_html_url");
        Repository repositoryOne = Repository.create("test_name",
                "test_html_url", "test_description", owner);
        Repository repositoryTwo = Repository.create("test_name",
                "test_html_url", "test_description", owner);

        assertThat(repositoryOne).isEqualTo(repositoryTwo);
        assertThat(repositoryTwo).isEqualTo(repositoryOne);
    }

    @Test
    public void propertiesMustBePropagatedCorrectly() {
        Organization owner = Organization.create(
                "test_login", "test_html_url");
        Repository repository = Repository.create("test_name",
                "test_html_url", "test_description", owner);

        assertThat(repository.name()).isEqualTo("test_name");
        assertThat(repository.htmlUrl()).isEqualTo("test_html_url");
        assertThat(repository.description()).isEqualTo("test_description");
        assertThat(repository.owner()).isEqualTo(owner);
    }
}
