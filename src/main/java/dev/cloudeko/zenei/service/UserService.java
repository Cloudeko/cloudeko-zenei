package dev.cloudeko.zenei.service;

import com.cloudeko.api.auth.password.UserPasswordEntity;
import com.cloudeko.api.auth.password.UserPasswordRepository;
import com.cloudeko.api.auth.session.SessionEntity;
import com.cloudeko.api.auth.session.SessionRepository;
import dev.cloudeko.model.app.User;
import dev.cloudeko.model.app.UserRole;
import dev.cloudeko.zenei.mapping.UserMapper;
import dev.cloudeko.zenei.models.UserEntity;
import dev.cloudeko.zenei.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {

    @Inject
    UserMapper userMapper;

    @Inject
    UserRepository userRepository;

    @Inject
    SessionRepository sessionRepository;

    @Inject
    UserPasswordRepository userPasswordRepository;

    @WithTransaction
    public Uni<User> createUser(String name, String email, String password, UserRole role) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);

        UserPasswordEntity userPassword = new UserPasswordEntity();
        userPassword.setPasswordHash(BcryptUtil.bcryptHash(password));

        return userRepository.persist(user)
                .onItem().ifNotNull().invoke(userPassword::setUser)
                .onItem().ifNotNull().transformToUni(u -> userPasswordRepository.persist(userPassword))
                .onItem().ifNotNull().transform(passwordEntity -> userMapper.toDomain(passwordEntity.getUser()));
    }

    @WithTransaction
    public Uni<User> verifyByToken(String token) {
        return sessionRepository.findByValidSessionToken(token)
                .onItem().ifNotNull().transform(SessionEntity::getUser)
                .onItem().ifNotNull().transform(userMapper::toDomain);
    }

    @WithSession
    public Uni<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email).onItem().ifNotNull().transform(userMapper::toDomain);
    }

    @WithSession
    public Uni<User> getUserByName(String name) {
        return userRepository.findByName(name).onItem().ifNotNull().transform(userMapper::toDomain);
    }
}
