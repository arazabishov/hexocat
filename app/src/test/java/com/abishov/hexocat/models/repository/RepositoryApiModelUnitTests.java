package com.abishov.hexocat.models.repository;

import com.abishov.hexocat.models.organization.OrganizationApiModel;
import com.abishov.hexocat.models.repository.RepositoryApiModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class RepositoryApiModelUnitTests {

    @Test
    public void createMustThrowOnNullName() {
        try {
            RepositoryApiModel.create(null, "test_html_url",
                    "test_description", mock(OrganizationApiModel.class));
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullHtmlUrl() {
        try {
            RepositoryApiModel.create("test_name", null,
                    "test_description", mock(OrganizationApiModel.class));
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullOwner() {
        try {
            RepositoryApiModel.create("test_name", "test_html_url",
                    "test_description", null);
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void equalsAndHashcodeMustConformToTheContract() {
        OrganizationApiModel owner = OrganizationApiModel.create(
                "test_login", "test_html_url", "test_avatar_url");
        RepositoryApiModel repositoryApiModelOne = RepositoryApiModel.create("test_name",
                "test_html_url", "test_description", owner);
        RepositoryApiModel repositoryApiModelTwo = RepositoryApiModel.create("test_name",
                "test_html_url", "test_description", owner);

        assertThat(repositoryApiModelOne).isEqualTo(repositoryApiModelTwo);
        assertThat(repositoryApiModelTwo).isEqualTo(repositoryApiModelOne);
    }

    @Test
    public void propertiesMustBePropagatedCorrectly() {
        OrganizationApiModel owner = OrganizationApiModel.create(
                "test_login", "test_html_url", "test_avatar_url");
        RepositoryApiModel repositoryApiModel = RepositoryApiModel.create("test_name",
                "test_html_url", "test_description", owner);

        assertThat(repositoryApiModel.name()).isEqualTo("test_name");
        assertThat(repositoryApiModel.htmlUrl()).isEqualTo("test_html_url");
        assertThat(repositoryApiModel.description()).isEqualTo("test_description");
        assertThat(repositoryApiModel.owner()).isEqualTo(owner);
    }
}
