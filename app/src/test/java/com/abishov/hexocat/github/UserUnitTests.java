package com.abishov.hexocat.github;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

@RunWith(JUnit4.class)
public class UserUnitTests {

    @Test
    public void createMustThrowOnNullLogin() {
        try {
            User.create(null, "test_html_url", "test_avatar_url");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullHtmlUrl() {
        try {
            User.create("test_login", null, "test_avatar_url");
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void createMustThrowOnNullAvatarUrl() {
        try {
            User.create("test_login", "test_html_url", null);
            fail("NullPointerException was expected, but nothing was thrown.");
        } catch (NullPointerException nullPointerException) {
            // noop
        }
    }

    @Test
    public void equalsAndHashcodeMustConformToContract() {
        User userOne = User.create(
                "test_login", "test_html_url", "test_avatar_url");
        User userTwo = User.create(
                "test_login", "test_html_url", "test_avatar_url");

        assertThat(userOne).isEqualTo(userTwo);
        assertThat(userTwo).isEqualTo(userOne);
    }

    @Test
    public void propertiesMustBePropagatedCorrectly() {
        User user = User.create(
                "test_login", "test_html_url", "test_avatar_url");

        assertThat(user.login()).isEqualTo("test_login");
        assertThat(user.htmlUrl()).isEqualTo("test_html_url");
        assertThat(user.avatarUrl()).isEqualTo("test_avatar_url");
    }
}
