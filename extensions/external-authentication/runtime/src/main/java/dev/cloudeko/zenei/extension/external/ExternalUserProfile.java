package dev.cloudeko.zenei.extension.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalUserProfile {

    private String id;

    private String username;
    private String avatarUrl;

    private List<ExternalUserEmail> emails;

    public record ExternalUserEmail(String email, boolean primary, boolean verified) {
    }
}
