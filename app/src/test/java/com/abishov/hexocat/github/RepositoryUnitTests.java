package com.abishov.hexocat.github;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RepositoryUnitTests {

  @Test
  public void createMustThrowOnNullName() {
    try {
      Repository.create(null, "test_html_url", 5, 10,
          "test_description", mock(User.class));
      fail("NullPointerException was expected, but nothing was thrown.");
    } catch (NullPointerException nullPointerException) {
      // noop
    }
  }

  @Test
  public void createMustThrowOnNullHtmlUrl() {
    try {
      Repository.create("test_name", null, 5, 10,
          "test_description", mock(User.class));
      fail("NullPointerException was expected, but nothing was thrown.");
    } catch (NullPointerException nullPointerException) {
      // noop
    }
  }

  @Test
  public void createMustThrowOnNullForks() {
    try {
      Repository.create("test_name", null, null, 10,
          "test_description", mock(User.class));
      fail("NullPointerException was expected, but nothing was thrown.");
    } catch (NullPointerException nullPointerException) {
      // noop
    }
  }

  @Test
  public void createMustThrowOnNullStars() {
    try {
      Repository.create("test_name", null, 5, null,
          "test_description", mock(User.class));
      fail("NullPointerException was expected, but nothing was thrown.");
    } catch (NullPointerException nullPointerException) {
      // noop
    }
  }

  @Test
  public void createMustThrowOnNullOwner() {
    try {
      Repository.create("test_name", "test_html_url", 5, 10,
          "test_description", null);
      fail("NullPointerException was expected, but nothing was thrown.");
    } catch (NullPointerException nullPointerException) {
      // noop
    }
  }

  @Test
  public void equalsAndHashcodeMustConformToTheContract() {
    User owner = User.create(
        "test_login", "test_html_url", "test_avatar_url");
    Repository repositoryOne = Repository.create("test_name",
        "test_html_url", 5, 10, "test_description", owner);
    Repository repositoryTwo = Repository.create("test_name",
        "test_html_url", 5, 10, "test_description", owner);

    assertThat(repositoryOne).isEqualTo(repositoryTwo);
    assertThat(repositoryTwo).isEqualTo(repositoryOne);
  }

  @Test
  public void propertiesMustBePropagatedCorrectly() {
    User owner = User.create(
        "test_login", "test_html_url", "test_avatar_url");
    Repository repository = Repository.create("test_name",
        "test_html_url", 5, 10, "test_description", owner);

    assertThat(repository.name()).isEqualTo("test_name");
    assertThat(repository.htmlUrl()).isEqualTo("test_html_url");
    assertThat(repository.forks()).isEqualTo(5);
    assertThat(repository.stars()).isEqualTo(10);
    assertThat(repository.description()).isEqualTo("test_description");
    assertThat(repository.owner()).isEqualTo(owner);
  }
}
