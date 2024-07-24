package dev.cloudeko.zenei.service;

import dev.cloudeko.zenei.dto.Token;
import dev.cloudeko.zenei.models.RefreshTokenEntity;
import dev.cloudeko.zenei.models.UserEntity;
import dev.cloudeko.zenei.repository.RefreshTokenRepository;
import dev.cloudeko.zenei.repository.UserPasswordRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.jbosslog.JBossLog;

import java.time.LocalDateTime;
import java.util.UUID;

@JBossLog
@ApplicationScoped
public class AuthenticationService {

    @Inject
    TokenService tokenService;

    @Inject
    RefreshTokenService refreshTokenService;

    @Inject
    RefreshTokenRepository refreshTokenRepository;

    @Inject
    UserPasswordRepository userPasswordRepository;

    @WithTransaction
    public Uni<Token> login(String email, String password) {
        return userPasswordRepository.findByValidPassword(email, BcryptUtil.bcryptHash(password))
                .onItem().ifNotNull().transformToUni(userPassword -> tokenService.createToken(userPassword.getUser()));
    }
}
