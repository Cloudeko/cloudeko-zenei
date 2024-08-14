package dev.cloudeko.zenei.extension.external.web.external.discord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscordUser {
    @JsonProperty("id")
    private String id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("global_name")
    private String globalName;

    @JsonProperty("discriminator")
    private String discriminator;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("email")
    private String email;

    @JsonProperty("verified")
    private boolean verified;
}
