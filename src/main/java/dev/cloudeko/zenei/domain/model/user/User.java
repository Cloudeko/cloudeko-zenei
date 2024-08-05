package dev.cloudeko.zenei.domain.model.user;

import dev.cloudeko.zenei.domain.model.email.EmailAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;
    private String firstName;
    private String lastName;

    private String primaryEmailAddress;
    private List<EmailAddress> emailAddresses;

    private String image;
    private boolean admin;
    private boolean passwordEnabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EmailAddress getPrimaryEmailAddress() {
        return emailAddresses.stream()
                .filter(e -> e.getEmail().equals(primaryEmailAddress))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User has no primary email address"));
    }
}
