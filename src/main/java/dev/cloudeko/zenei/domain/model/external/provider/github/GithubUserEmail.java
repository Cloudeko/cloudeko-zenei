package dev.cloudeko.zenei.domain.model.external.provider.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubUserEmail {
    @JsonProperty("email")
    private String email;

    @JsonProperty("primary")
    private boolean primary;

    @JsonProperty("verified")
    private boolean verified;
}
