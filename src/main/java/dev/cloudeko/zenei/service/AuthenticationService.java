package dev.cloudeko.zenei.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@ApplicationScoped
public class AuthenticationService {

    /*@Inject
    UserService userService;

    @Inject
    TokenService tokenService;

    @Inject
    RefreshTokenService refreshTokenService;

    @Inject
    RefreshTokenRepository refreshTokenRepository;

    @Inject
    UserPasswordRepository userPasswordRepository;

    @WithTransaction
    public Uni<User> signupAttempt(String name, String email, String password) {
        return userService.getUserByEmail(email)
                .onItem().ifNotNull().failWith(() -> new BadRequestException("User already exists"))
                .onItem().ifNull().switchTo(() -> userService.createUser(name, email, password, null));
    }

    @WithTransaction
    public Uni<Token> login(String email, String password) {
        return userPasswordRepository.findByValidPassword(email, BcryptUtil.bcryptHash(password))
                .onItem().ifNotNull().transformToUni(userPassword -> tokenService.createToken(userPassword.getUser()));
    }

    @WithTransaction
    public Uni<Token> refreshToken(String refreshToken) {
        return tokenService.refreshToken(refreshToken);
    }*/
}
