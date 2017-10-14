package com.abishov.hexocat.models;

import com.abishov.hexocat.models.OrganizationApiModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

@RunWith(JUnit4.class)
public class OrganizationApiModelUnitTests {

    @Test
    public void createMustThrowOnNullLogin() {
        try {
            OrganizationApiModel.create(null, "test_html_url", "test_avatar_url");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullHtmlUrl() {
        try {
            OrganizationApiModel.create("test_login", null, "test_avatar_url");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullAvatarUrl() {
        try {
            OrganizationApiModel.create("test_login", "test_html_url", null);
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void equalsAndHashcodeMustConformToContract() {
        OrganizationApiModel organizationApiModelOne = OrganizationApiModel.create(
                "test_login", "test_html_url", "test_avatar_url");
        OrganizationApiModel organizationApiModelTwo = OrganizationApiModel.create(
                "test_login", "test_html_url", "test_avatar_url");

        assertThat(organizationApiModelOne).isEqualTo(organizationApiModelTwo);
        assertThat(organizationApiModelTwo).isEqualTo(organizationApiModelOne);
    }

    @Test
    public void propertiesMustBePropagatedCorrectly() {
        OrganizationApiModel organizationApiModel = OrganizationApiModel.create(
                "test_login", "test_html_url", "test_avatar_url");

        assertThat(organizationApiModel.login()).isEqualTo("test_login");
        assertThat(organizationApiModel.htmlUrl()).isEqualTo("test_html_url");
        assertThat(organizationApiModel.avatarUrl()).isEqualTo("test_avatar_url");
    }
}
