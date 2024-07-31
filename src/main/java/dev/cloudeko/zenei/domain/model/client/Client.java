package dev.cloudeko.zenei.domain.model.client;

import dev.cloudeko.zenei.domain.model.auth.SigninAttempt;
import dev.cloudeko.zenei.domain.model.auth.SignupAttempt;
import dev.cloudeko.zenei.domain.model.session.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private long id;

    private SignupAttempt signupAttempt;
    private SigninAttempt signinAttempt;
    private List<Session> sessions;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
