package com.abishov.hexocat.commons.models;

import com.abishov.hexocat.Inject;
import com.abishov.hexocat.commons.models.Organization;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(JUnit4.class)
public class OrganizationIntegrationTests {

    @Test
    public void payloadMustMapToModelCorrectly() {
        Gson gson = Inject.gson();
        Organization organization = gson.fromJson("{\n" +
                "    \"login\": \"CaMnter\",\n" +
                "    \"id\": 10336931,\n" +
                "    \"avatar_url\": \"https://avatars0.githubusercontent.com/u/10336931?v=3\",\n" +
                "    \"gravatar_id\": \"\",\n" +
                "    \"url\": \"https://api.github.com/users/CaMnter\",\n" +
                "    \"html_url\": \"https://github.com/CaMnter\",\n" +
                "    \"followers_url\": \"https://api.github.com/users/CaMnter/followers\",\n" +
                "    \"following_url\": \"https://api.github.com/users/CaMnter/following{/other_user}\",\n" +
                "    \"gists_url\": \"https://api.github.com/users/CaMnter/gists{/gist_id}\",\n" +
                "    \"starred_url\": \"https://api.github.com/users/CaMnter/starred{/owner}{/repo}\",\n" +
                "    \"subscriptions_url\": \"https://api.github.com/users/CaMnter/subscriptions\",\n" +
                "    \"organizations_url\": \"https://api.github.com/users/CaMnter/orgs\",\n" +
                "    \"repos_url\": \"https://api.github.com/users/CaMnter/repos\",\n" +
                "    \"events_url\": \"https://api.github.com/users/CaMnter/events{/privacy}\",\n" +
                "    \"received_events_url\": \"https://api.github.com/users/CaMnter/received_events\",\n" +
                "    \"type\": \"User\",\n" +
                "    \"site_admin\": false\n" +
                "      }", Organization.class);

        assertThat(organization.login()).isEqualTo("CaMnter");
        assertThat(organization.htmlUrl()).isEqualTo("https://github.com/CaMnter");
        assertThat(organization.avatarUrl()).isEqualTo("https://avatars0.githubusercontent.com/u/10336931?v=3");
    }
}
