package dev.cloudeko.zenei.extension.external.web.external.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubUser {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("login")
    private String login;

    @JsonProperty("name")
    private String name;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("email")
    private String email;
}
