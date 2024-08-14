package dev.cloudeko.zenei.domain.model.email;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyMagicLinkInput {
    private String token;
}
