package dev.cloudeko.zenei.domain.model.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OAuthUserInput {
    private String code;
    private String state;
}
