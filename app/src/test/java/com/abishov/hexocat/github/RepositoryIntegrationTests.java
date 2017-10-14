package com.abishov.hexocat.github;

import com.abishov.hexocat.Inject;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(JUnit4.class)
public class RepositoryIntegrationTests {

    @Test
    public void payloadMustMapToModelCorrectly() {
        Gson gson = Inject.gson();
        Repository repository = gson.fromJson("{\n" +
                "      \"id\": 892275,\n" +
                "      \"name\": \"retrofit\",\n" +
                "      \"forks\": 222," +
                "      \"stargazers_count\": 777," +
                "      \"html_url\": \"https://github.com/square/retrofit\",\n" +
                "      \"description\": \"Type-safe HTTP client for Android and Java by Square, Inc.\"," +
                "      \"owner\": {\n" +
                "        \"login\": \"square\",\n" +
                "        \"id\": 82592,\n" +
                "        \"avatar_url\": \"https://avatars0.githubusercontent.com/u/82592?v=4\",\n" +
                "        \"gravatar_id\": \"\",\n" +
                "        \"url\": \"https://api.github.com/users/square\",\n" +
                "        \"html_url\": \"https://github.com/square\",\n" +
                "        \"followers_url\": \"https://api.github.com/users/square/followers\",\n" +
                "        \"following_url\": \"https://api.github.com/users/square/following{/other_user}\",\n" +
                "        \"gists_url\": \"https://api.github.com/users/square/gists{/gist_id}\",\n" +
                "        \"starred_url\": \"https://api.github.com/users/square/starred{/owner}{/repo}\",\n" +
                "        \"subscriptions_url\": \"https://api.github.com/users/square/subscriptions\",\n" +
                "        \"organizations_url\": \"https://api.github.com/users/square/orgs\",\n" +
                "        \"repos_url\": \"https://api.github.com/users/square/repos\",\n" +
                "        \"events_url\": \"https://api.github.com/users/square/events{/privacy}\",\n" +
                "        \"received_events_url\": \"https://api.github.com/users/square/received_events\",\n" +
                "        \"type\": \"User\",\n" +
                "        \"site_admin\": false\n" +
                "      }" +
                "}", Repository.class);

        assertThat(repository.name()).isEqualTo("retrofit");
        assertThat(repository.forks()).isEqualTo(222);
        assertThat(repository.stars()).isEqualTo(777);
        assertThat(repository.htmlUrl()).isEqualTo("https://github.com/square/retrofit");
        assertThat(repository.description()).isEqualTo("Type-safe HTTP client for Android and Java by Square, Inc.");
        assertThat(repository.owner().login()).isEqualTo("square");
        assertThat(repository.owner().htmlUrl()).isEqualTo("https://github.com/square");
    }
}
